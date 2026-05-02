package edu.eci.dosw.competitions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dosw.competitions.dtos.CreateLineupDTO;
import edu.eci.dosw.competitions.dtos.LineupResponseDTO;
import edu.eci.dosw.competitions.dtos.UpdateLineupDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import edu.eci.dosw.competitions.mapper.LineupMapper;
import edu.eci.dosw.competitions.service.LineupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LineupController.class)
@AutoConfigureMockMvc(addFilters = false)
class LineupControllerTest {

    private static final List<String> STARTER_IDS = List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11");
    private static final List<String> SUBSTITUTE_IDS = List.of("p12","p13","p14");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LineupService lineupService;

    @MockitoBean
    private LineupMapper lineupMapper;

    private ObjectMapper objectMapper;
    private Lineup lineup;
    private LineupResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        lineup = new Lineup();
        lineup.setId("lineup-1");
        lineup.setMatchId("match-1");
        lineup.setTeamId("team-1");
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(STARTER_IDS);
        lineup.setSubstituteIds(SUBSTITUTE_IDS);
        lineup.setConfirmed(false);

        responseDTO = new LineupResponseDTO();
        responseDTO.setId("lineup-1");
        responseDTO.setMatchId("match-1");
        responseDTO.setTeamId("team-1");
        responseDTO.setFormation("4-3-3");
        responseDTO.setStarterIds(STARTER_IDS);
        responseDTO.setSubstituteIds(SUBSTITUTE_IDS);
        responseDTO.setConfirmed(false);
    }

    private void stubMapperResponse() {
        when(lineupMapper.toResponseDTO(any(Lineup.class))).thenReturn(responseDTO);
    }

    private CreateLineupDTO buildCreateDTO() {
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("team-1");
        dto.setFormation("4-3-3");
        dto.setStarterIds(STARTER_IDS);
        dto.setSubstituteIds(SUBSTITUTE_IDS);
        return dto;
    }

    private ResultActions performPost(String url, Object body) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)));
    }

    private ResultActions performPut(String url, Object body) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)));
    }

    // ===== UNIT TESTS =====

    @Test
    void testCreateLineup_returns201() throws Exception {
        when(lineupService.createLineup(any(CreateLineupDTO.class))).thenReturn(lineup);
        stubMapperResponse();

        performPost("/api/lineups", buildCreateDTO())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("lineup-1"))
                .andExpect(jsonPath("$.formation").value("4-3-3"))
                .andExpect(jsonPath("$.confirmed").value(false));

        verify(lineupService).createLineup(any(CreateLineupDTO.class));
    }

    @Test
    void testUpdateLineup_returns200() throws Exception {
        UpdateLineupDTO dto = new UpdateLineupDTO();
        dto.setFormation("4-4-2");
        responseDTO.setFormation("4-4-2");

        when(lineupService.updateLineup(eq("lineup-1"), any(UpdateLineupDTO.class))).thenReturn(lineup);
        stubMapperResponse();

        performPut("/api/lineups/lineup-1", dto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formation").value("4-4-2"));

        verify(lineupService).updateLineup(eq("lineup-1"), any(UpdateLineupDTO.class));
    }

    @Test
    void testConfirmLineup_returns200() throws Exception {
        responseDTO.setConfirmed(true);
        when(lineupService.confirmLineup("lineup-1")).thenReturn(lineup);
        stubMapperResponse();

        mockMvc.perform(put("/api/lineups/lineup-1/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true));

        verify(lineupService).confirmLineup("lineup-1");
    }

    @Test
    void testGetLineup_returns200() throws Exception {
        when(lineupService.getLineup("match-1", "team-1")).thenReturn(lineup);
        stubMapperResponse();

        mockMvc.perform(get("/api/lineups/match-1/team-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("lineup-1"))
                .andExpect(jsonPath("$.matchId").value("match-1"))
                .andExpect(jsonPath("$.teamId").value("team-1"));
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFullLineupLifecycle_createUpdateConfirm() throws Exception {
        when(lineupService.createLineup(any(CreateLineupDTO.class))).thenReturn(lineup);
        stubMapperResponse();

        performPost("/api/lineups", buildCreateDTO())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.confirmed").value(false));

        UpdateLineupDTO updateDTO = new UpdateLineupDTO();
        updateDTO.setFormation("4-4-2");
        responseDTO.setFormation("4-4-2");
        when(lineupService.updateLineup(eq("lineup-1"), any(UpdateLineupDTO.class))).thenReturn(lineup);

        performPut("/api/lineups/lineup-1", updateDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formation").value("4-4-2"));

        responseDTO.setConfirmed(true);
        when(lineupService.confirmLineup("lineup-1")).thenReturn(lineup);
        mockMvc.perform(put("/api/lineups/lineup-1/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true));
    }
}
