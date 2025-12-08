package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Product;
import com.Tindahan.ni.Lola.Sari_sari.Store.website.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "${FRONTEND_URL:*}") // set FRONTEND_URL in Render to your Vercel domain (or leave * for dev)
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Set this env var in Render if you want a different storage path.
    // Default "uploads" (relative path inside container)
    private final String UPLOAD_DIR = Optional.ofNullable(System.getenv("UPLOAD_DIR")).orElse("uploads");

    // If set, API will return absolute image URLs: BACKEND_URL=https://your-backend.render.com
    private final String BACKEND_URL = Optional.ofNullable(System.getenv("BACKEND_URL")).orElse("").trim();

    private String fullImageUrl(String imageUrl) {
        if (imageUrl == null) return null;
        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) return imageUrl;
        if (!BACKEND_URL.isBlank()) {
            String base = BACKEND_URL.replaceAll("/+$", "");
            return base + (imageUrl.startsWith("/") ? "" : "/") + imageUrl;
        }
        return imageUrl;
    }

    // Helper: return a response map (avoids mutating JPA entities)
    private Map<String, Object> toResponse(Product p) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", p.getId());
        m.put("name", p.getName());
        m.put("category", p.getCategory());
        m.put("price", p.getPrice());
        m.put("description", p.getDescription());
        m.put("imageUrl", fullImageUrl(p.getImageUrl()));
        return m;
    }

    // GET all products
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<Map<String,Object>> resp = new ArrayList<>();
        for (Product p : products) resp.add(toResponse(p));
        return ResponseEntity.ok(resp);
    }

    // GET products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Map<String, Object>>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body(List.of());
        }
        List<Map<String,Object>> resp = new ArrayList<>();
        for (Product p : products) resp.add(toResponse(p));
        return ResponseEntity.ok(resp);
    }

    // GET single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> opt = productRepository.findById(id);
        return opt.<ResponseEntity<?>>map(p -> ResponseEntity.ok(toResponse(p)))
                .orElseGet(() -> ResponseEntity.status(404).body("❌ Product not found with id: " + id));
    }

    // POST new product (supports image upload)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("price") double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            String imageUrl = null;

            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                String original = Path.of(Objects.requireNonNull(imageFile.getOriginalFilename())).getFileName().toString();
                String safeFileName = System.currentTimeMillis() + "_" + original.replaceAll("\\s+", "_");
                Path filePath = uploadPath.resolve(safeFileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // store relative path used by resource handler
                imageUrl = "/uploads/" + safeFileName;
            }

            Product product = new Product(name, price, description, imageUrl, category);
            Product savedProduct = productRepository.save(product);

            return ResponseEntity.status(201).body(toResponse(savedProduct));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error uploading image: " + e.getMessage());
        }
    }

    // PUT update product (supports optional image)
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("price") double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(404).body("❌ Product not found with ID: " + id);
        }

        Product product = optionalProduct.get();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setDescription(description);

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                String original = Path.of(Objects.requireNonNull(imageFile.getOriginalFilename())).getFileName().toString();
                String safeFileName = System.currentTimeMillis() + "_" + original.replaceAll("\\s+", "_");
                Path filePath = uploadPath.resolve(safeFileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // delete old file if stored under /uploads/
                String old = product.getImageUrl();
                if (old != null && old.startsWith("/uploads/")) {
                    try {
                        Path oldPath = Paths.get(UPLOAD_DIR).resolve(old.substring("/uploads/".length()));
                        Files.deleteIfExists(oldPath);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                product.setImageUrl("/uploads/" + safeFileName);
            }

            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(toResponse(updatedProduct));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error updating product: " + e.getMessage());
        }
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("❌ Product not found with id: " + id);
        }

        Product product = opt.get();

        String img = product.getImageUrl();
        if (img != null && img.startsWith("/uploads/")) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Path filePath = uploadPath.resolve(img.substring("/uploads/".length()));
                Files.deleteIfExists(filePath);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok("✅ Product deleted successfully!");
    }
}
