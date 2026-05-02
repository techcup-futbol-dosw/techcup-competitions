package edu.eci.dosw.competitions.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dosw.competitions.dtos.CreateLineupDTO;
import edu.eci.dosw.competitions.dtos.UpdateLineupDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class LineupControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<String> ELEVEN =
            List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11");
    private static final List<String> SUBS = List.of("p12","p13","p14");

    // ── Helpers ──────────────────────────────────────────────────────────────

    private CreateLineupDTO buildDTO(String matchId, String teamId, String formation) {
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId(matchId);
        dto.setTeamId(teamId);
        dto.setFormation(formation);
        dto.setStarterIds(ELEVEN);
        dto.setSubstituteIds(SUBS);
        return dto;
    }

    private String createLineupAndGetId(String matchId, String teamId, String formation) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildDTO(matchId, teamId, formation))))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("id").asText();
    }

    // ── Creación ─────────────────────────────────────────────────────────────

    @Test
    void integration_createLineup_returns201WithUnconfirmedStatus() throws Exception {
        mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildDTO("m-1", "t-1", "4-3-3"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.confirmed").value(false))
                .andExpect(jsonPath("$.formation").value("4-3-3"))
                .andExpect(jsonPath("$.matchId").value("m-1"))
                .andExpect(jsonPath("$.teamId").value("t-1"));
    }

    @Test
    void integration_createLineup_starterIdsPersistedCorrectly() throws Exception {
        mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildDTO("m-2", "t-2", "4-4-2"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.starterIds.length()").value(11))
                .andExpect(jsonPath("$.substituteIds.length()").value(3));
    }

    @Test
    void integration_createTwoLineups_sameMatch_differentTeams() throws Exception {
        mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildDTO("m-shared", "team-A", "4-3-3"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildDTO("m-shared", "team-B", "3-5-2"))))
                .andExpect(status().isCreated());
    }

    // ── Actualización ────────────────────────────────────────────────────────

    @Test
    void integration_updateLineup_changesFormation() throws Exception {
        String id = createLineupAndGetId("m-upd", "t-upd", "4-3-3");

        UpdateLineupDTO upd = new UpdateLineupDTO();
        upd.setFormation("3-5-2");

        mockMvc.perform(put("/api/lineups/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formation").value("3-5-2"));
    }

    @Test
    void integration_updateLineup_changesStarterIds() throws Exception {
        String id = createLineupAndGetId("m-upd2", "t-upd2", "4-3-3");

        List<String> newStarters =
                List.of("x1","x2","x3","x4","x5","x6","x7","x8","x9","x10","x11");
        UpdateLineupDTO upd = new UpdateLineupDTO();
        upd.setStarterIds(newStarters);

        mockMvc.perform(put("/api/lineups/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.starterIds[0]").value("x1"));
    }

    @Test
    void integration_updateConfirmedLineup_returns500() throws Exception {
        String id = createLineupAndGetId("m-conf", "t-conf", "4-3-3");
        mockMvc.perform(put("/api/lineups/" + id + "/confirm"));

        UpdateLineupDTO upd = new UpdateLineupDTO();
        upd.setFormation("4-4-2");

        mockMvc.perform(put("/api/lineups/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().is5xxServerError());
    }

    // ── Confirmación ─────────────────────────────────────────────────────────

    @Test
    void integration_confirmLineup_returnsConfirmedTrue() throws Exception {
        String id = createLineupAndGetId("m-ok", "t-ok", "4-4-2");

        mockMvc.perform(put("/api/lineups/" + id + "/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true));
    }

    @Test
    void integration_confirmLineupWithLessThan11Starters_returns500() throws Exception {
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId("m-bad");
        dto.setTeamId("t-bad");
        dto.setFormation("4-3-3");
        dto.setStarterIds(List.of("p1", "p2", "p3"));
        dto.setSubstituteIds(SUBS);

        MvcResult result = mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        String id = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("id").asText();

        mockMvc.perform(put("/api/lineups/" + id + "/confirm"))
                .andExpect(status().is5xxServerError());
    }

    // ── Consulta ─────────────────────────────────────────────────────────────

    @Test
    void integration_getLineup_returnsCorrectData() throws Exception {
        createLineupAndGetId("m-get", "team-get", "3-4-3");

        mockMvc.perform(get("/api/lineups/match/m-get/team/team-get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchId").value("m-get"))
                .andExpect(jsonPath("$.teamId").value("team-get"))
                .andExpect(jsonPath("$.formation").value("3-4-3"));
    }

    @Test
    void integration_getLineup_notFound_returns500() throws Exception {
        mockMvc.perform(get("/api/lineups/match/no-match/team/no-team"))
                .andExpect(status().is5xxServerError());
    }

    // ── Ciclo de vida completo ────────────────────────────────────────────────

    @Test
    void integration_fullLineupLifecycle_createUpdateConfirmGet() throws Exception {
        // 1. Crear
        String id = createLineupAndGetId("m-life", "t-life", "4-3-3");

        // 2. Actualizar
        UpdateLineupDTO upd = new UpdateLineupDTO();
        upd.setFormation("4-4-2");
        mockMvc.perform(put("/api/lineups/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formation").value("4-4-2"));

        // 3. Confirmar
        mockMvc.perform(put("/api/lineups/" + id + "/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true));

        // 4. Consultar
        mockMvc.perform(get("/api/lineups/match/m-life/team/t-life"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true))
                .andExpect(jsonPath("$.formation").value("4-4-2"));

        // 5. Intentar modificar post-confirmación
        UpdateLineupDTO upd2 = new UpdateLineupDTO();
        upd2.setFormation("3-5-2");
        mockMvc.perform(put("/api/lineups/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upd2)))
                .andExpect(status().is5xxServerError());
    }
}