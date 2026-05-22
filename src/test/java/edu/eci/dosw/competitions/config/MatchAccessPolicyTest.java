package edu.eci.dosw.competitions.config;

import edu.eci.dosw.competitions.entity.Match;
import edu.eci.dosw.competitions.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchAccessPolicyTest {

    private MatchRepository matchRepository;
    private MatchAccessPolicy policy;

    @BeforeEach
    void setUp() {
        matchRepository = mock(MatchRepository.class);
        policy = new MatchAccessPolicy(matchRepository);
    }

    private Authentication auth(String userId) {
        return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
    }

    @Test
    void canManageAssignedMatch_assignedReferee_returnsTrue() {
        Match match = new Match();
        match.setRefereeId("ref-1");
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));

        assertTrue(policy.canManageAssignedMatch("match-1", auth("ref-1")));
    }

    @Test
    void canManageAssignedMatch_notAssignedReferee_returnsFalse() {
        Match match = new Match();
        match.setRefereeId("ref-2");
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));

        assertFalse(policy.canManageAssignedMatch("match-1", auth("ref-1")));
    }

    @Test
    void canManageAssignedMatch_matchNotFound_returnsFalse() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.empty());

        assertFalse(policy.canManageAssignedMatch("match-1", auth("ref-1")));
    }

    @Test
    void canManageAssignedMatch_nullAuthentication_returnsFalse() {
        assertFalse(policy.canManageAssignedMatch("match-1", null));
    }

    @Test
    void canManageAssignedMatch_nullMatchId_returnsFalse() {
        assertFalse(policy.canManageAssignedMatch(null, auth("ref-1")));
    }
}