package edu.eci.dosw.competitions.controller;

import edu.eci.dosw.competitions.dtos.*;
import edu.eci.dosw.competitions.entity.*;
import edu.eci.dosw.competitions.mapper.MatchMapper;
import edu.eci.dosw.competitions.service.MatchService;
import edu.eci.dosw.competitions.repository.StandingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchMapper matchMapper;
    private final StandingsRepository standingsRepository;

    public MatchController(MatchService matchService,
                           MatchMapper matchMapper,
                           StandingsRepository standingsRepository) {
        this.matchService = matchService;
        this.matchMapper = matchMapper;
        this.standingsRepository = standingsRepository;
    }

    @PostMapping
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody CreateMatchDTO dto) {
        Match match = matchService.createMatch(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(matchMapper.toResponseDTO(match));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> updateMatch(@PathVariable String id,
                                                        @RequestBody UpdateMatchDTO dto) {
        Match match = matchService.updateMatch(id, dto);
        return ResponseEntity.ok(matchMapper.toResponseDTO(match));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<MatchResponseDTO> startMatch(@PathVariable String id) {
        Match match = matchService.startMatch(id);
        return ResponseEntity.ok(matchMapper.toResponseDTO(match));
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<MatchResponseDTO> finishMatch(@PathVariable String id) {
        Match match = matchService.finishMatch(id);
        return ResponseEntity.ok(matchMapper.toResponseDTO(match));
    }

    @PostMapping("/goals")
    public ResponseEntity<MatchEventResponseDTO> registerGoal(@RequestBody RegisterGoalDTO dto) {
        Goal goal = matchService.registerGoal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(matchMapper.toEventResponseDTO(goal));
    }

    @PostMapping("/cards")
    public ResponseEntity<MatchEventResponseDTO> registerCard(@RequestBody RegisterCardDTO dto) {
        Card card = matchService.registerCard(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(matchMapper.toEventResponseDTO(card));
    }

    @GetMapping("/{matchId}/events")
    public ResponseEntity<List<MatchEventResponseDTO>> getEvents(@PathVariable String matchId) {
        List<MatchEvent> events = matchService.getEventsByMatch(matchId);
        List<MatchEventResponseDTO> dtos = events.stream()
                .map(matchMapper::toEventResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/standings/{tournamentId}")
    public ResponseEntity<List<StandingsResponseDTO>> getStandings(@PathVariable String tournamentId) {
        List<Standings> standings = standingsRepository.findByTournamentIdOrderByPointsDescGoalDifferenceDesc(tournamentId);
        List<StandingsResponseDTO> dtos = standings.stream()
                .map(matchMapper::toStandingsResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
