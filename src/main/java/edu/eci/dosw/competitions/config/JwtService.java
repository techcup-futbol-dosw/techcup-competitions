package edu.eci.dosw.competitions.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JwtService {

    private static final String ROLES_CLAIM       = "roles";
    private static final String PERMISSIONS_CLAIM = "permissions";
    private static final String TOKEN_TYPE_CLAIM  = "tokenType";
    private static final String ACCESS_TOKEN_TYPE = "ACCESS";

    private final SecretKey secretKey;

    public JwtService(@Value("${security.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /** Valida firma, expiración y tipo ACCESS */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return ACCESS_TOKEN_TYPE.equals(claims.get(TOKEN_TYPE_CLAIM, String.class));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    /** Extrae la lista de roles del claim "roles" */
    public List<String> extractRoles(String token) {
        Object rolesObj = extractAllClaims(token).get(ROLES_CLAIM);
        if (rolesObj instanceof List<?> rawList) {
            List<String> roles = new ArrayList<>();
            for (Object item : rawList) {
                roles.add(String.valueOf(item));
            }
            return roles;
        }
        return Collections.emptyList();
    }

    /** Extrae la lista de permisos del claim "permissions" */
    public List<String> extractPermissions(String token) {
        Object permObj = extractAllClaims(token).get(PERMISSIONS_CLAIM);
        if (permObj instanceof List<?> rawList) {
            List<String> permissions = new ArrayList<>();
            for (Object item : rawList) {
                permissions.add(String.valueOf(item));
            }
            return permissions;
        }
        return Collections.emptyList();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
