package edu.eci.dosw.competitions.controller;

import edu.eci.dosw.competitions.dtos.*;
import edu.eci.dosw.competitions.entity.Standings;
import edu.eci.dosw.competitions.mapper.MatchEventMapper;
import edu.eci.dosw.competitions.mapper.MatchMapper;
import edu.eci.dosw.competitions.mapper.StandingsMapper;
import edu.eci.dosw.competitions.repository.StandingsRepository;
import edu.eci.dosw.competitions.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchMapper matchMapper;
    private final MatchEventMapper matchEventMapper;
    private final StandingsMapper standingsMapper;
    private final StandingsRepository standingsRepository;

    public MatchController(MatchService matchService, MatchMapper matchMapper,
                           MatchEventMapper matchEventMapper, StandingsMapper standingsMapper,
                           StandingsRepository standingsRepository) {
        this.matchService = matchService;
        this.matchMapper = matchMapper;
        this.matchEventMapper = matchEventMapper;
        this.standingsMapper = standingsMapper;
        this.standingsRepository = standingsRepository;
    }

    @PostMapping
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody CreateMatchDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchMapper.toResponseDTO(matchService.createMatch(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> updateMatch(@PathVariable String id,
                                                        @RequestBody UpdateMatchDTO dto) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.updateMatch(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<MatchResponseDTO> startMatch(@PathVariable String id) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.startMatch(id)));
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<MatchResponseDTO> finishMatch(@PathVariable String id) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.finishMatch(id)));
    }

    @PostMapping("/goals")
    public ResponseEntity<MatchEventResponseDTO> registerGoal(@RequestBody RegisterGoalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchEventMapper.toResponseDTO(matchService.registerGoal(dto)));
    }

    @PostMapping("/cards")
    public ResponseEntity<MatchEventResponseDTO> registerCard(@RequestBody RegisterCardDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchEventMapper.toResponseDTO(matchService.registerCard(dto)));
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<MatchEventResponseDTO>> getEvents(@PathVariable String id) {
        return ResponseEntity.ok(
                matchService.getEventsByMatch(id).stream()
                        .map(matchEventMapper::toResponseDTO)
                        .toList()
        );
    }

    @GetMapping("/standings/{tournamentId}")
    public ResponseEntity<List<StandingsResponseDTO>> getStandings(@PathVariable String tournamentId) {
        List<Standings> standings = standingsRepository
                .findByTournamentIdOrderByPointsDescGoalDifferenceDesc(tournamentId);
        return ResponseEntity.ok(standings.stream().map(standingsMapper::toResponseDTO).toList());
    }
}