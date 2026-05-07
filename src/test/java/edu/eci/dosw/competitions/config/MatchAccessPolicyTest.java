package edu.eci.dosw.competitions.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MatchAccessPolicyTest {

    private MatchAccessPolicy policy;

    @BeforeEach
    void setUp() {
        policy = new MatchAccessPolicy();
    }

    private Authentication auth(String userId) {
        return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
    }

    @Test
    void canAccessOwnMatch_sameId_returnsTrue() {
        assertTrue(policy.canAccessOwnMatch("match-1", auth("match-1")));
    }

    @Test
    void canAccessOwnMatch_differentId_returnsFalse() {
        assertFalse(policy.canAccessOwnMatch("match-1", auth("match-99")));
    }

    @Test
    void canAccessOwnMatch_nullAuthentication_returnsFalse() {
        assertFalse(policy.canAccessOwnMatch("match-1", null));
    }
}