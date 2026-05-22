package edu.eci.dosw.competitions.config;

import edu.eci.dosw.competitions.repository.MatchRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("matchAccessPolicy")
public class MatchAccessPolicy {

    private final MatchRepository matchRepository;

    public MatchAccessPolicy(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public boolean canManageAssignedMatch(String matchId, Authentication authentication) {
        if (matchId == null || matchId.isBlank()) {
            return false;
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String accountId = extractAccountId(authentication);

        if (accountId == null) {
            return false;
        }

        return matchRepository.findById(matchId)
                .map(match -> accountId.equals(match.getRefereeId()))
                .orElse(false);
    }

    private String extractAccountId(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal == null) {
            return null;
        }

        return principal.toString();
    }
}