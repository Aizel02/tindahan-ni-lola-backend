package com.Tindahan.ni.Lola.Sari_sari.Store.website.repository;
import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    Store findByUserId(UUID userId);
}
