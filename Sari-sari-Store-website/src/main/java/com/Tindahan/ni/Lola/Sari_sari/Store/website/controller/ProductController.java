package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Product;
import com.Tindahan.ni.Lola.Sari_sari.Store.website.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "${FRONTEND_URL:*}") // Spring will substitute env or fallback "*"
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Use environment variable UPLOAD_DIR if set; otherwise default to "uploads/"
    private final String UPLOAD_DIR = System.getenv().getOrDefault("UPLOAD_DIR", "uploads/");

    // Optional BACKEND_URL env var so responses can include absolute image URLs in prod
    private final String BACKEND_URL = System.getenv().getOrDefault("BACKEND_URL", "").trim();

    // Helper: convert stored relative image path (/uploads/xxx.jpg) to absolute URL if BACKEND_URL is provided
    private String fullImageUrl(String imageUrl) {
        if (imageUrl == null) return null;
        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) return imageUrl;
        if (!BACKEND_URL.isEmpty()) {
            String base = BACKEND_URL.replaceAll("/+$", "");
            return base + (imageUrl.startsWith("/") ? "" : "/") + imageUrl;
        }
        return imageUrl;
    }

    // GET all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        // if BACKEND_URL provided, update imageUrl to full path for each product (non-destructive)
        if (!BACKEND_URL.isBlank()) {
            products.forEach(p -> p.setImageUrl(fullImageUrl(p.getImageUrl())));
        }
        return ResponseEntity.ok(products);
    }

    // GET products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        if (!BACKEND_URL.isBlank()) {
            products.forEach(p -> p.setImageUrl(fullImageUrl(p.getImageUrl())));
        }
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body(List.of());
        }
        return ResponseEntity.ok(products);
    }

    // GET single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            Product p = opt.get();
            if (!BACKEND_URL.isBlank()) p.setImageUrl(fullImageUrl(p.getImageUrl()));
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.status(404).body("❌ Product not found with id: " + id);
        }
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
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String safeFileName = System.currentTimeMillis() + "_" + Path.of(imageFile.getOriginalFilename()).getFileName().toString();
                Path filePath = uploadPath.resolve(safeFileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Store relative path used by resource handler
                imageUrl = "/uploads/" + safeFileName;
            }

            Product product = new Product(name, price, description, imageUrl, category);
            Product savedProduct = productRepository.save(product);

            // if BACKEND_URL set, return full image url
            if (!BACKEND_URL.isBlank()) savedProduct.setImageUrl(fullImageUrl(savedProduct.getImageUrl()));

            return ResponseEntity.status(201).body(savedProduct);

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
            // Upload new image if provided (and optionally delete old file)
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                String safeFileName = System.currentTimeMillis() + "_" + Path.of(imageFile.getOriginalFilename()).getFileName().toString();
                Path filePath = uploadPath.resolve(safeFileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // delete old file if exists and it is stored under /uploads/
                String old = product.getImageUrl();
                if (old != null && old.startsWith("/uploads/")) {
                    try {
                        Path oldPath = Paths.get(UPLOAD_DIR).resolve(old.substring("/uploads/".length()));
                        Files.deleteIfExists(oldPath);
                    } catch (Exception ex) {
                        // log but don't fail update
                        ex.printStackTrace();
                    }
                }

                product.setImageUrl("/uploads/" + safeFileName);
            }

            Product updatedProduct = productRepository.save(product);
            if (!BACKEND_URL.isBlank()) updatedProduct.setImageUrl(fullImageUrl(updatedProduct.getImageUrl()));
            return ResponseEntity.ok(updatedProduct);

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

        // Delete image file if exists (stored in uploads folder)
        String img = product.getImageUrl();
        if (img != null && img.startsWith("/uploads/")) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Path filePath = uploadPath.resolve(img.substring("/uploads/".length()));
                Files.deleteIfExists(filePath);
            } catch (Exception ex) {
                ex.printStackTrace(); // don't block deletion because of file error
            }
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok("✅ Product deleted successfully!");
    }
}
