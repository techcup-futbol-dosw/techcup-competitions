package edu.eci.dosw.competitions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.eci.dosw.competitions.dtos.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@WithMockUser(authorities = {
        "match:create:any",
        "match:update:any",
        "match:delete:any",
        "match:read:any",
        "goal:register:any",
        "card:register:any"
})
class MatchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    // ── Helpers ──────────────────────────────────────────────────────────────

    private CreateMatchDTO buildCreateDTO(String tournamentId, String home, String away, String phase) {
        CreateMatchDTO dto = new CreateMatchDTO();
        dto.setTournamentId(tournamentId);
        dto.setHomeTeamId(home);
        dto.setAwayTeamId(away);
        dto.setRefereeId("ref-1");
        dto.setFieldId("field-1");
        dto.setScheduledAt(LocalDateTime.of(2026, 6, 1, 15, 0));
        dto.setPhase(phase);
        return dto;
    }

    private String createMatchAndGetId(String tournamentId, String home, String away) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                buildCreateDTO(tournamentId, home, away, "GROUP_STAGE"))))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("id").asText();
    }

    // ── Creación ─────────────────────────────────────────────────────────────

    @Test
    void integration_createMatch_returns201WithScheduledStatus() throws Exception {
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                buildCreateDTO("t-1", "home-1", "away-1", "GROUP_STAGE"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("SCHEDULED"))
                .andExpect(jsonPath("$.tournamentId").value("t-1"))
                .andExpect(jsonPath("$.homeTeamId").value("home-1"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void integration_createMatch_differentPhases_persistCorrectly() throws Exception {
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                buildCreateDTO("t-phase", "h", "a", "FINAL"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.phase").value("FINAL"));
    }

    // ── Actualización ────────────────────────────────────────────────────────

    @Test
    void integration_updateMatch_changesRefereeAndField() throws Exception {
        String id = createMatchAndGetId("t-upd", "h", "a");

        UpdateMatchDTO upd = new UpdateMatchDTO();
        upd.setRefereeId("ref-updated");
        upd.setFieldId("field-updated");

        mockMvc.perform(put("/api/matches/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void integration_updateFinishedMatch_returns500() throws Exception {
        String id = createMatchAndGetId("t-upd2", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start")).andReturn();
        mockMvc.perform(put("/api/matches/" + id + "/finish")).andReturn();

        UpdateMatchDTO upd = new UpdateMatchDTO();
        upd.setRefereeId("should-fail");

        mockMvc.perform(put("/api/matches/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().is5xxServerError());
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────

    @Test
    void integration_startMatch_returnsInProgress() throws Exception {
        String id = createMatchAndGetId("t-start", "h", "a");

        mockMvc.perform(put("/api/matches/" + id + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void integration_finishMatch_returnsFinished() throws Exception {
        String id = createMatchAndGetId("t-finish", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        mockMvc.perform(put("/api/matches/" + id + "/finish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FINISHED"));
    }

    @Test
    void integration_fullLifecycle_createStartGoalsFinishStandings() throws Exception {
        // 1. Crear
        String id = createMatchAndGetId("t-full", "home-full", "away-full");

        // 2. Iniciar
        mockMvc.perform(put("/api/matches/" + id + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        // 3. Gol del local
        RegisterGoalDTO goal = new RegisterGoalDTO();
        goal.setMatchId(id);
        goal.setTeamId("home-full");
        goal.setPlayerId("player-1");
        goal.setOwnGoal(false);
        goal.setMinute(30);

        mockMvc.perform(post("/api/matches/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.minute").value(30));

        // 4. Finalizar
        mockMvc.perform(put("/api/matches/" + id + "/finish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FINISHED"))
                .andExpect(jsonPath("$.homeScore").value(1))
                .andExpect(jsonPath("$.awayScore").value(0));

        // 5. Standings
        mockMvc.perform(get("/api/matches/standings/t-full"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ── Goles ────────────────────────────────────────────────────────────────

    @Test
    void integration_registerGoal_returns201WithDescription() throws Exception {
        String id = createMatchAndGetId("t-goal", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId(id);
        dto.setTeamId("h");
        dto.setPlayerId("player-10");
        dto.setOwnGoal(false);
        dto.setMinute(55);

        mockMvc.perform(post("/api/matches/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerId").value("player-10"))
                .andExpect(jsonPath("$.minute").value(55))
                .andExpect(jsonPath("$.description").isNotEmpty());
    }

    @Test
    void integration_registerOwnGoal_descriptionContainsOwnGoal() throws Exception {
        String id = createMatchAndGetId("t-own", "home-own", "away-own");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId(id);
        dto.setTeamId("home-own");
        dto.setPlayerId("player-5");
        dto.setOwnGoal(true);
        dto.setMinute(70);

        mockMvc.perform(post("/api/matches/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Own goal at minute 70"));
    }

    @Test
    void integration_registerGoal_nonExistentMatch_returns500() throws Exception {
        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId("match-ghost");
        dto.setTeamId("h");
        dto.setPlayerId("p");
        dto.setMinute(10);

        mockMvc.perform(post("/api/matches/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is5xxServerError());
    }

    // ── Tarjetas ─────────────────────────────────────────────────────────────

    @Test
    void integration_registerYellowCard_returns201() throws Exception {
        String id = createMatchAndGetId("t-card", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        RegisterCardDTO dto = new RegisterCardDTO();
        dto.setMatchId(id);
        dto.setTeamId("h");
        dto.setPlayerId("player-7");
        dto.setCardType("YELLOW");
        dto.setMinute(40);

        mockMvc.perform(post("/api/matches/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.minute").value(40))
                .andExpect(jsonPath("$.description").value("YELLOW card at minute 40"));
    }

    @Test
    void integration_registerRedCard_returns201() throws Exception {
        String id = createMatchAndGetId("t-red", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        RegisterCardDTO dto = new RegisterCardDTO();
        dto.setMatchId(id);
        dto.setTeamId("a");
        dto.setPlayerId("player-9");
        dto.setCardType("RED");
        dto.setMinute(88);

        mockMvc.perform(post("/api/matches/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("RED card at minute 88"));
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    @Test
    void integration_getEvents_returnsAllEventsForMatch() throws Exception {
        String id = createMatchAndGetId("t-events", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        RegisterGoalDTO goal = new RegisterGoalDTO();
        goal.setMatchId(id);
        goal.setTeamId("h");
        goal.setPlayerId("p1");
        goal.setOwnGoal(false);
        goal.setMinute(10);
        mockMvc.perform(post("/api/matches/goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(goal)));

        RegisterCardDTO card = new RegisterCardDTO();
        card.setMatchId(id);
        card.setTeamId("a");
        card.setPlayerId("p2");
        card.setCardType("YELLOW");
        card.setMinute(20);
        mockMvc.perform(post("/api/matches/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)));

        mockMvc.perform(get("/api/matches/" + id + "/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void integration_getEvents_emptyMatch_returnsEmptyList() throws Exception {
        String id = createMatchAndGetId("t-empty", "h", "a");

        mockMvc.perform(get("/api/matches/" + id + "/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ── Eliminación ──────────────────────────────────────────────────────────

    @Test
    void integration_deleteScheduledMatch_returns204() throws Exception {
        String id = createMatchAndGetId("t-del", "h", "a");

        mockMvc.perform(delete("/api/matches/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void integration_deleteInProgressMatch_returns500() throws Exception {
        String id = createMatchAndGetId("t-del2", "h", "a");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        mockMvc.perform(delete("/api/matches/" + id))
                .andExpect(status().is5xxServerError());
    }

    // ── Standings ─────────────────────────────────────────────────────────────

    @Test
    void integration_getStandings_afterFinishedMatch_returnsRanking() throws Exception {
        String id = createMatchAndGetId("t-standings", "team-A", "team-B");
        mockMvc.perform(put("/api/matches/" + id + "/start"));

        RegisterGoalDTO goal = new RegisterGoalDTO();
        goal.setMatchId(id);
        goal.setTeamId("team-A");
        goal.setPlayerId("p1");
        goal.setOwnGoal(false);
        goal.setMinute(15);
        mockMvc.perform(post("/api/matches/goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(goal)));

        mockMvc.perform(put("/api/matches/" + id + "/finish"));

        mockMvc.perform(get("/api/matches/standings/t-standings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].points").value(3));
    }

    @Test
    void integration_getStandings_noMatches_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/matches/standings/tournament-sin-partidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}