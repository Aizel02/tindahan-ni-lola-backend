package com.Tindahan.ni.Lola.Sari_sari.Store.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class SariSariStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SariSariStoreApplication.class, args);
    }

    // ✅ Automatically create /uploads folder if not exists
    @PostConstruct
    public void initUploadsFolder() {
        try {
            Path uploadPath = Paths.get(System.getProperty("user.dir") + "/uploads/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("✅ Created uploads directory at: " + uploadPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
