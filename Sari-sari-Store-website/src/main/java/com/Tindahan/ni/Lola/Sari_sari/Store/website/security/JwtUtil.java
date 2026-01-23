package com.Tindahan.ni.Lola.Sari_sari.Store.website.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.UUID;

public class JwtUtil {

    private static final String SECRET = "YOUR_SUPABASE_JWT_SECRET";

    public static UUID extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        return UUID.fromString(claims.getSubject());
    }
}
