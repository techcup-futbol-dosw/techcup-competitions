package edu.eci.dosw.competitions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.eci.dosw.competitions.dtos.*;
import edu.eci.dosw.competitions.entity.*;
import edu.eci.dosw.competitions.mapper.MatchMapper;
import edu.eci.dosw.competitions.service.MatchService;
import edu.eci.dosw.competitions.repository.StandingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
@AutoConfigureMockMvc(addFilters = false)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @MockitoBean
    private MatchMapper matchMapper;

    @MockitoBean
    private StandingsRepository standingsRepository;

    private ObjectMapper objectMapper;
    private Match match;
    private MatchResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        match = new Match();
        match.setId("match-1");
        match.setTournamentId("tournament-1");
        match.setHomeTeamId("home-1");
        match.setAwayTeamId("away-1");
        match.setScheduledAt(LocalDateTime.of(2026, 5, 1, 15, 0));
        match.setStatus(MatchStatus.SCHEDULED);
        match.setPhase(MatchPhase.GROUP_STAGE);

        responseDTO = new MatchResponseDTO();
        responseDTO.setId("match-1");
        responseDTO.setTournamentId("tournament-1");
        responseDTO.setHomeTeamId("home-1");
        responseDTO.setAwayTeamId("away-1");
        responseDTO.setScheduledAt(LocalDateTime.of(2026, 5, 1, 15, 0));
        responseDTO.setStatus("SCHEDULED");
        responseDTO.setPhase("GROUP_STAGE");
    }

    private void stubMapperResponse() {
        when(matchMapper.toResponseDTO(any(Match.class))).thenReturn(responseDTO);
    }

    private MatchEventResponseDTO buildEventDTO(String id, int minute, String description) {
        MatchEventResponseDTO dto = new MatchEventResponseDTO();
        dto.setId(id);
        dto.setMatchId("match-1");
        dto.setMinute(minute);
        dto.setDescription(description);
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
    void testCreateMatch_returns201() throws Exception {
        CreateMatchDTO dto = new CreateMatchDTO();
        dto.setTournamentId("tournament-1");
        dto.setHomeTeamId("home-1");
        dto.setAwayTeamId("away-1");
        dto.setScheduledAt(LocalDateTime.of(2026, 5, 1, 15, 0));
        dto.setPhase("GROUP_STAGE");

        when(matchService.createMatch(any(CreateMatchDTO.class))).thenReturn(match);
        stubMapperResponse();

        performPost("/api/matches", dto)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("match-1"))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));

        verify(matchService).createMatch(any(CreateMatchDTO.class));
    }

    @Test
    void testUpdateMatch_returns200() throws Exception {
        UpdateMatchDTO dto = new UpdateMatchDTO();
        dto.setRefereeId("ref-new");
        responseDTO.setRefereeId("ref-new");

        when(matchService.updateMatch(eq("match-1"), any(UpdateMatchDTO.class))).thenReturn(match);
        stubMapperResponse();

        performPut("/api/matches/match-1", dto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("match-1"));

        verify(matchService).updateMatch(eq("match-1"), any(UpdateMatchDTO.class));
    }

    @Test
    void testDeleteMatch_returns204() throws Exception {
        doNothing().when(matchService).deleteMatch("match-1");

        mockMvc.perform(delete("/api/matches/match-1"))
                .andExpect(status().isNoContent());

        verify(matchService).deleteMatch("match-1");
    }

    @Test
    void testStartMatch_returns200() throws Exception {
        responseDTO.setStatus("IN_PROGRESS");
        when(matchService.startMatch("match-1")).thenReturn(match);
        stubMapperResponse();

        mockMvc.perform(put("/api/matches/match-1/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        verify(matchService).startMatch("match-1");
    }

    @Test
    void testFinishMatch_returns200() throws Exception {
        responseDTO.setStatus("FINISHED");
        when(matchService.finishMatch("match-1")).thenReturn(match);
        stubMapperResponse();

        mockMvc.perform(put("/api/matches/match-1/finish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FINISHED"));

        verify(matchService).finishMatch("match-1");
    }

    @Test
    void testRegisterGoal_returns201() throws Exception {
        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("home-1");
        dto.setPlayerId("player-1");
        dto.setMinute(30);
        dto.setOwnGoal(false);

        Goal goal = new Goal();
        goal.setId("goal-1");
        when(matchService.registerGoal(any(RegisterGoalDTO.class))).thenReturn(goal);
        when(matchMapper.toEventResponseDTO(any(MatchEvent.class)))
                .thenReturn(buildEventDTO("goal-1", 30, "Goal at minute 30"));

        performPost("/api/matches/goals", dto)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("goal-1"))
                .andExpect(jsonPath("$.minute").value(30));

        verify(matchService).registerGoal(any(RegisterGoalDTO.class));
    }

    @Test
    void testRegisterCard_returns201() throws Exception {
        RegisterCardDTO dto = new RegisterCardDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("home-1");
        dto.setPlayerId("player-2");
        dto.setCardType("YELLOW");
        dto.setMinute(45);

        Card card = new Card();
        card.setId("card-1");
        when(matchService.registerCard(any(RegisterCardDTO.class))).thenReturn(card);
        when(matchMapper.toEventResponseDTO(any(MatchEvent.class)))
                .thenReturn(buildEventDTO("card-1", 45, "YELLOW card at minute 45"));

        performPost("/api/matches/cards", dto)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("card-1"))
                .andExpect(jsonPath("$.minute").value(45));

        verify(matchService).registerCard(any(RegisterCardDTO.class));
    }

    @Test
    void testGetEvents_returns200WithList() throws Exception {
        Goal goal = new Goal();
        goal.setId("event-1");

        when(matchService.getEventsByMatch("match-1")).thenReturn(List.of(goal));
        when(matchMapper.toEventResponseDTO(any(MatchEvent.class)))
                .thenReturn(buildEventDTO("event-1", 10, "Goal"));

        mockMvc.perform(get("/api/matches/match-1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("event-1"))
                .andExpect(jsonPath("$[0].minute").value(10));
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testGetEvents_emptyList_returns200() throws Exception {
        when(matchService.getEventsByMatch("match-empty")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/matches/match-empty/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetStandings_returns200WithList() throws Exception {
        Standings standings = new Standings();
        standings.setId("s-1");
        standings.setPoints(9);

        StandingsResponseDTO standingsDTO = new StandingsResponseDTO();
        standingsDTO.setId("s-1");
        standingsDTO.setPoints(9);

        when(standingsRepository.findByTournamentIdOrderByPointsDescGoalDifferenceDesc("tournament-1"))
                .thenReturn(List.of(standings));
        when(matchMapper.toStandingsResponseDTO(any(Standings.class))).thenReturn(standingsDTO);

        mockMvc.perform(get("/api/matches/standings/tournament-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("s-1"))
                .andExpect(jsonPath("$[0].points").value(9));
    }

    @Test
    void testGetStandings_emptyList_returns200() throws Exception {
        when(standingsRepository.findByTournamentIdOrderByPointsDescGoalDifferenceDesc("no-existe"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/matches/standings/no-existe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testCreateMatch_fullLifecycle_createStartFinish() throws Exception {
        CreateMatchDTO createDTO = new CreateMatchDTO();
        createDTO.setTournamentId("tournament-1");
        createDTO.setHomeTeamId("home-1");
        createDTO.setAwayTeamId("away-1");
        createDTO.setScheduledAt(LocalDateTime.of(2026, 5, 1, 15, 0));
        createDTO.setPhase("GROUP_STAGE");

        when(matchService.createMatch(any(CreateMatchDTO.class))).thenReturn(match);
        stubMapperResponse();

        performPost("/api/matches", createDTO)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("SCHEDULED"));

        responseDTO.setStatus("IN_PROGRESS");
        when(matchService.startMatch("match-1")).thenReturn(match);
        mockMvc.perform(put("/api/matches/match-1/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        responseDTO.setStatus("FINISHED");
        when(matchService.finishMatch("match-1")).thenReturn(match);
        mockMvc.perform(put("/api/matches/match-1/finish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FINISHED"));
    }
}
