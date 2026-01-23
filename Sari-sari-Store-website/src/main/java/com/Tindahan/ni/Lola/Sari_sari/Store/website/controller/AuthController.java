package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;
import com.Tindahan.ni.Lola.Sari.sari.Store.website.model.Store;
import com.Tindahan.ni.Lola.Sari.sari.Store.website.repository.StoreRepository;
import com.Tindahan.ni.Lola.Sari.sari.Store.website.security.JwtUtil;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final StoreRepository storeRepository;

    public AuthController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @PostMapping("/register")
    public Store register(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body
    ) {
        UUID userId = JwtUtil.extractUserId(token);

        Store store = new Store();
        store.setUserId(userId);
        store.setStoreName(body.get("storeName"));

        return storeRepository.save(store);
    }

    @GetMapping("/me")
    public Store getMyStore(
            @RequestHeader("Authorization") String token
    ) {
        UUID userId = JwtUtil.extractUserId(token);
        return storeRepository.findByUserId(userId);
    }
}
