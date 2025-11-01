package com.Tindahan.ni.Lola.Sari_sari.Store.website.repository;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIgnoreCase(String category);
}
