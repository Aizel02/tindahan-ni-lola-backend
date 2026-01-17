package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Liability;
import com.Tindahan.ni.Lola.Sari_sari.Store.website.repository.LiabilityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liabilities")
@CrossOrigin(origins = "${FRONTEND_URL:*}")
public class LiabilityController {

    private final LiabilityRepository repo;

    public LiabilityController(LiabilityRepository repo) {
        this.repo = repo;
    }

    // ✅ Get all liabilities
    @GetMapping
    public List<Liability> getAll() {
        return repo.findAll();
    }

    // ✅ Add liability
    @PostMapping
    public Liability add(@RequestBody Liability l) {
        l.setStatus("Pending");
        return repo.save(l);
    }

    // ✅ Mark as paid
    @PutMapping("/{id}/pay")
    public Liability markAsPaid(@PathVariable Long id) {
        Liability l = repo.findById(id).orElseThrow();
        l.setStatus("Paid");
        return repo.save(l);
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
