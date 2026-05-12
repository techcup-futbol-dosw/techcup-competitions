package edu.eci.dosw.competitions.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final String SECRET =
            "bWtZY2xhdmUtc3VwZXItc2VjcmV0YS1wYXJhLWp3dC0xMjM0NTY3ODkwMTIzNDU2";

    private JwtService jwtService;
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(SECRET);
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private String buildToken(String subject, List<String> roles,
                              List<String> permissions, String tokenType,
                              long expiresInMs) {
        return Jwts.builder()
                .subject(subject)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .claim("tokenType", tokenType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiresInMs))
                .signWith(secretKey)
                .compact();
    }

    private String validToken(String userId) {
        return buildToken(userId, List.of("ADMIN"), List.of("match:write:any"), "ACCESS", 60_000);
    }

    // ── isTokenValid ─────────────────────────────────────────────────────────

    @Test
    void isTokenValid_validAccessToken_returnsTrue() {
        assertTrue(jwtService.isTokenValid(validToken("user-1")));
    }

    @Test
    void isTokenValid_wrongTokenType_returnsFalse() {
        String token = buildToken("user-1", List.of(), List.of(), "REFRESH", 60_000);
        assertFalse(jwtService.isTokenValid(token));
    }

    @Test
    void isTokenValid_expiredToken_returnsFalse() {
        String token = buildToken("user-1", List.of(), List.of(), "ACCESS", -1000);
        assertFalse(jwtService.isTokenValid(token));
    }

    @Test
    void isTokenValid_malformedToken_returnsFalse() {
        assertFalse(jwtService.isTokenValid("not.a.token"));
    }

    @Test
    void isTokenValid_emptyString_returnsFalse() {
        assertFalse(jwtService.isTokenValid(""));
    }

    // ── extractUserId ────────────────────────────────────────────────────────

    @Test
    void extractUserId_validToken_returnsSubject() {
        String token = validToken("user-42");
        assertEquals("user-42", jwtService.extractUserId(token));
    }

    // ── extractRoles ─────────────────────────────────────────────────────────

    @Test
    void extractRoles_tokenWithRoles_returnsList() {
        String token = buildToken("u", List.of("ADMIN", "CAPITAN"), List.of(), "ACCESS", 60_000);
        List<String> roles = jwtService.extractRoles(token);
        assertEquals(2, roles.size());
        assertTrue(roles.contains("ADMIN"));
        assertTrue(roles.contains("CAPITAN"));
    }

    @Test
    void extractRoles_tokenWithoutRoles_returnsEmptyList() {
        String token = buildToken("u", List.of(), List.of(), "ACCESS", 60_000);
        assertTrue(jwtService.extractRoles(token).isEmpty());
    }

    // ── extractPermissions ───────────────────────────────────────────────────

    @Test
    void extractPermissions_tokenWithPermissions_returnsList() {
        String token = buildToken("u", List.of(), List.of("match:write:any", "match:read:any"), "ACCESS", 60_000);
        List<String> perms = jwtService.extractPermissions(token);
        assertEquals(2, perms.size());
        assertTrue(perms.contains("match:write:any"));
    }

    @Test
    void extractPermissions_tokenWithoutPermissions_returnsEmptyList() {
        String token = buildToken("u", List.of(), List.of(), "ACCESS", 60_000);
        assertTrue(jwtService.extractPermissions(token).isEmpty());
    }
}