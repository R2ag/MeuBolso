package com.meubolso.api.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-seconds}")
    private long expirationSeconds;

    private Mac hmac;

    @PostConstruct
    public void init() {
        try {
            byte[] signingKey = secret.getBytes(StandardCharsets.UTF_8);
            hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(signingKey, "HmacSHA256"));
        } catch (Exception ex) {
            throw new IllegalStateException("Não foi possível inicializar o gerador de JWT", ex);
        }
    }

    public String createToken(String userId, String email) {
        long issuedAt = Instant.now().getEpochSecond();
        long expiration = issuedAt + expirationSeconds;
        String header = base64UrlEncode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = base64UrlEncode(String.format(
                "{\"sub\":\"%s\",\"email\":\"%s\",\"iat\":%d,\"exp\":%d}",
                escape(userId),
                escape(email),
                issuedAt,
                expiration
        ));
        String signature = base64UrlEncode(hmacSha256(header + "." + payload));
        return String.join(".", header, payload, signature);
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }
            String expected = base64UrlEncode(hmacSha256(parts[0] + "." + parts[1]));
            if (!expected.equals(parts[2])) {
                return false;
            }
            String payload = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
            long expiration = extractLongClaim(payload, "exp");
            return Instant.now().getEpochSecond() <= expiration;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUserId(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            String payload = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
            return extractStringClaim(payload, "sub");
        } catch (Exception ex) {
            return null;
        }
    }

    private byte[] hmacSha256(String value) {
        try {
            return hmac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private String base64UrlEncode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private byte[] base64UrlDecode(String payload) {
        return Base64.getUrlDecoder().decode(payload);
    }

    private String extractStringClaim(String json, String name) {
        String pattern = String.format("\"%s\":\"", name);
        int index = json.indexOf(pattern);
        if (index < 0) {
            return null;
        }
        int start = index + pattern.length();
        int end = json.indexOf('"', start);
        if (end < 0) {
            return null;
        }
        return json.substring(start, end);
    }

    private long extractLongClaim(String json, String name) {
        String pattern = String.format("\"%s\":", name);
        int index = json.indexOf(pattern);
        if (index < 0) {
            return 0;
        }
        int start = index + pattern.length();
        int end = json.indexOf(',', start);
        if (end < 0) {
            end = json.indexOf('}', start);
        }
        if (end < 0) {
            return 0;
        }
        return Long.parseLong(json.substring(start, end).trim());
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
