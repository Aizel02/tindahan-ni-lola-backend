package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Product;
import com.Tindahan.ni.Lola.Sari_sari.Store.website.repository.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "${FRONTEND_URL:*}")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Cloudinary cloudinary;

    /* ===================== HELPERS ===================== */

    private Map<String, Object> toResponse(Product p) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", p.getId());
        m.put("name", p.getName());
        m.put("category", p.getCategory());
        m.put("price", p.getPrice());
        m.put("description", p.getDescription());
        m.put("imageUrl", p.getImageUrl()); // already Cloudinary URL
        return m;
    }

    /* ===================== GET ===================== */

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<Map<String, Object>> resp = new ArrayList<>();
        for (Product p : products) resp.add(toResponse(p));
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(p -> ResponseEntity.ok(toResponse(p)))
                .orElse(ResponseEntity.status(404).body(
                        Map.of("message", "❌ Product not found")
                ));
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Map<String, Object>>> getByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        List<Map<String, Object>> resp = new ArrayList<>();
        for (Product p : products) resp.add(toResponse(p));
        return ResponseEntity.ok(resp);
    }

    /* ===================== CREATE ===================== */

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            String imageUrl = null;

            if (image != null && !image.isEmpty()) {
                Map upload = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.emptyMap()
                );
                imageUrl = upload.get("secure_url").toString();
            }

            Product product = new Product(
                    name,
                    price,
                    description,
                    imageUrl,
                    category
            );

            Product saved = productRepository.save(product);
            return ResponseEntity.status(201).body(toResponse(saved));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Failed to upload image");
        }
    }

    /* ===================== UPDATE ===================== */

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile image
    ) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("❌ Product not found");
        }

        try {
            Product product = opt.get();
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);
            product.setDescription(description);

            if (image != null && !image.isEmpty()) {
                Map upload = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.emptyMap()
                );
                product.setImageUrl(upload.get("secure_url").toString());
            }

            Product updated = productRepository.save(product);
            return ResponseEntity.ok(toResponse(updated));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Failed to update product");
        }
    }

    /* ===================== DELETE ===================== */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(404).body("❌ Product not found");
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("✅ Product deleted");
    }
}
