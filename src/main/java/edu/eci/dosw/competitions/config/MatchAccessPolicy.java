package edu.eci.dosw.competitions.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class MatchAccessPolicy {

    /**
     * Verifica si el usuario autenticado es el dueño/responsable del recurso.
     *
     * Ejemplo de uso en un controlador:
     *
     * @PreAuthorize("hasAuthority('match:update:any') or @matchAccessPolicy.canAccessOwnMatch(#matchId, authentication)")
     * @PutMapping("/{matchId}")
     * public ResponseEntity<MatchResponseDTO> updateMatch(@PathVariable String matchId, ...) { ... }
     */
    public boolean canAccessOwnMatch(String requestedMatchId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String currentUserId = authentication.getPrincipal().toString();
        return requestedMatchId.equals(currentUserId);
    }
}