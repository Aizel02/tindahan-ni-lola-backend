package com.Tindahan.ni.Lola.Sari_sari.Store.website.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "✅ Backend is running! Welcome to Sari-Sari Store API.";
    }
}
