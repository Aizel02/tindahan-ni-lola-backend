package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;

import com.Tindahan.ni.Lola.Sari_sari.Store.website.model.Liability;
import com.Tindahan.ni.Lola.Sari_sari.Store.website.repository.LiabilityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/liabilities")
@CrossOrigin(origins = "${FRONTEND_URL:*}")
public class LiabilityController {

    private final LiabilityRepository repo;

    public LiabilityController(LiabilityRepository repo) {
        this.repo = repo;
    }

    // ✅ GET ALL LIABILITIES
    @GetMapping
    public List<Liability> getAll() {
        return repo.findAll();
    }

    // ✅ ADD LIABILITY
    @PostMapping
    public Liability add(@RequestBody Liability l) {
        l.setStatus("Pending");       // default
        l.setPaidDate(null);          // not paid yet
        return repo.save(l);
    }

    // ✅ MARK AS PAID (with date)

    @PutMapping("/{id}/pay")
    public Liability markAsPaid(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        Liability l = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Liability not found"));

        l.setStatus("Paid");

        // ✅ Convert string → LocalDate
        if (body.get("paidDate") != null && !body.get("paidDate").isEmpty()) {
            l.setPaidDate(LocalDate.parse(body.get("paidDate")));
        }

        return repo.save(l);
    }


    // ✅ DELETE LIABILITY
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
