package com.Tindahan.ni.Lola.Sari_sari.Store.website.repository;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Liability;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LiabilityRepository extends JpaRepository<Liability, Long> {
    List<Liability> findByDebtorName(String debtorName);
}
