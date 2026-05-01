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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LineupController.class)
@AutoConfigureMockMvc(addFilters = false)
class LineupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LineupService lineupService;

    @MockitoBean
    private LineupMapper lineupMapper;

    private ObjectMapper objectMapper;
    private Lineup lineup;
    private LineupResponseDTO lineupResponseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        lineup = new Lineup();
        lineup.setId("lineup-1");
        lineup.setMatchId("match-1");
        lineup.setTeamId("team-1");
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineup.setSubstituteIds(List.of("p12","p13","p14"));
        lineup.setConfirmed(false);

        lineupResponseDTO = new LineupResponseDTO();
        lineupResponseDTO.setId("lineup-1");
        lineupResponseDTO.setMatchId("match-1");
        lineupResponseDTO.setTeamId("team-1");
        lineupResponseDTO.setFormation("4-3-3");
        lineupResponseDTO.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineupResponseDTO.setSubstituteIds(List.of("p12","p13","p14"));
        lineupResponseDTO.setConfirmed(false);
    }

    // ===== UNIT TESTS =====

    @Test
    void testCreateLineup_returns201() throws Exception {
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("team-1");
        dto.setFormation("4-3-3");
        dto.setStarterIds(lineup.getStarterIds());
        dto.setSubstituteIds(lineup.getSubstituteIds());

        when(lineupService.createLineup(any(CreateLineupDTO.class))).thenReturn(lineup);
        when(lineupMapper.toResponseDTO(any(Lineup.class))).thenReturn(lineupResponseDTO);

        mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("lineup-1"))
                .andExpect(jsonPath("$.formation").value("4-3-3"))
                .andExpect(jsonPath("$.confirmed").value(false));

        verify(lineupService, times(1)).createLineup(any(CreateLineupDTO.class));
    }

    @Test
    void testUpdateLineup_returns200() throws Exception {
        UpdateLineupDTO dto = new UpdateLineupDTO();
        dto.setFormation("4-4-2");

        lineupResponseDTO.setFormation("4-4-2");

        when(lineupService.updateLineup(eq("lineup-1"), any(UpdateLineupDTO.class))).thenReturn(lineup);
        when(lineupMapper.toResponseDTO(any(Lineup.class))).thenReturn(lineupResponseDTO);

        mockMvc.perform(put("/api/lineups/lineup-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formation").value("4-4-2"));

        verify(lineupService, times(1)).updateLineup(eq("lineup-1"), any(UpdateLineupDTO.class));
    }

    @Test
    void testConfirmLineup_returns200() throws Exception {
        lineup.setConfirmed(true);
        lineupResponseDTO.setConfirmed(true);

        when(lineupService.confirmLineup("lineup-1")).thenReturn(lineup);
        when(lineupMapper.toResponseDTO(any(Lineup.class))).thenReturn(lineupResponseDTO);

        mockMvc.perform(put("/api/lineups/lineup-1/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true));

        verify(lineupService, times(1)).confirmLineup("lineup-1");
    }

    @Test
    void testGetLineup_returns200() throws Exception {
        when(lineupService.getLineup("match-1", "team-1")).thenReturn(lineup);
        when(lineupMapper.toResponseDTO(any(Lineup.class))).thenReturn(lineupResponseDTO);

        mockMvc.perform(get("/api/lineups/match-1/team-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("lineup-1"))
                .andExpect(jsonPath("$.matchId").value("match-1"))
                .andExpect(jsonPath("$.teamId").value("team-1"));
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFullLineupLifecycle_createUpdateConfirm() throws Exception {
        // Create
        CreateLineupDTO createDTO = new CreateLineupDTO();
        createDTO.setMatchId("match-1");
        createDTO.setTeamId("team-1");
        createDTO.setFormation("4-3-3");
        createDTO.setStarterIds(lineup.getStarterIds());
        createDTO.setSubstituteIds(lineup.getSubstituteIds());

        when(lineupService.createLineup(any(CreateLineupDTO.class))).thenReturn(lineup);
        when(lineupMapper.toResponseDTO(any(Lineup.class))).thenReturn(lineupResponseDTO);

        mockMvc.perform(post("/api/lineups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.confirmed").value(false));

        // Update
        UpdateLineupDTO updateDTO = new UpdateLineupDTO();
        updateDTO.setFormation("4-4-2");
        lineupResponseDTO.setFormation("4-4-2");

        when(lineupService.updateLineup(eq("lineup-1"), any(UpdateLineupDTO.class))).thenReturn(lineup);

        mockMvc.perform(put("/api/lineups/lineup-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formation").value("4-4-2"));

        // Confirm
        lineupResponseDTO.setConfirmed(true);
        when(lineupService.confirmLineup("lineup-1")).thenReturn(lineup);

        mockMvc.perform(put("/api/lineups/lineup-1/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmed").value(true));
    }
}
