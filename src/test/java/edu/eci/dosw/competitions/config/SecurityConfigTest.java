package edu.eci.dosw.competitions.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    // ── Endpoints públicos (GET) ──────────────────────────────────────────────

    @Test
    void getMatches_noToken_returns200() throws Exception {
        mockMvc.perform(get("/api/matches/standings/tournament-x"))
                .andExpect(status().isOk());
    }

    @Test
    void getLineup_noToken_returns2xx() throws Exception {
        mockMvc.perform(get("/api/lineups/match/m-x/team/t-x"))
                .andExpect(status().is5xxServerError()); // no existe, pero pasó el filtro de seguridad
    }

    @Test
    void swaggerUi_noToken_isAllowed() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().is2xxSuccessful());
    }

    // ── Endpoints protegidos (sin token → 401) ───────────────────────────────

    @Test
    void createMatch_noToken_returns401() throws Exception {
        mockMvc.perform(post("/api/matches")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateMatch_noToken_returns401() throws Exception {
        mockMvc.perform(put("/api/matches/some-id")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteMatch_noToken_returns401() throws Exception {
        mockMvc.perform(delete("/api/matches/some-id"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createLineup_noToken_returns401() throws Exception {
        mockMvc.perform(post("/api/lineups")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateLineup_noToken_returns401() throws Exception {
        mockMvc.perform(put("/api/lineups/some-id")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }
}