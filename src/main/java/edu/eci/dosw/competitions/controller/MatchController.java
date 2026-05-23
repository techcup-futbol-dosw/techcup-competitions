package edu.eci.dosw.competitions.controller;

import edu.eci.dosw.competitions.dtos.*;
import edu.eci.dosw.competitions.entity.Match;
import edu.eci.dosw.competitions.entity.Standings;
import edu.eci.dosw.competitions.mapper.MatchEventMapper;
import edu.eci.dosw.competitions.mapper.MatchMapper;
import edu.eci.dosw.competitions.mapper.StandingsMapper;
import edu.eci.dosw.competitions.repository.StandingsRepository;
import edu.eci.dosw.competitions.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('match:create:any')")
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody CreateMatchDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchMapper.toResponseDTO(matchService.createMatch(dto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('match:update:any')")
    public ResponseEntity<MatchResponseDTO> updateMatch(@PathVariable String id,
                                                        @RequestBody UpdateMatchDTO dto) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.updateMatch(id, dto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('match:delete:any')")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/start")
    @PreAuthorize("hasAuthority('match:update:any')")
    public ResponseEntity<MatchResponseDTO> startMatch(@PathVariable String id) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.startMatch(id)));
    }

    @PutMapping("/{id}/finish")
    @PreAuthorize("hasAuthority('match:update:any')")
    public ResponseEntity<MatchResponseDTO> finishMatch(@PathVariable String id) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.finishMatch(id)));
    }

    @PostMapping("/goals")
    @PreAuthorize(
            "hasAuthority('goal:register:any') " +
            "or (hasAuthority('goal:register:assigned') " +
            "and @matchAccessPolicy.canManageAssignedMatch(#dto.matchId, authentication))"
    )
    public ResponseEntity<MatchEventResponseDTO> registerGoal(@RequestBody RegisterGoalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchEventMapper.toResponseDTO(matchService.registerGoal(dto)));
    }

    @PostMapping("/cards")
    @PreAuthorize(
            "hasAuthority('card:register:any') " +
            "or (hasAuthority('card:register:assigned') " +
            "and @matchAccessPolicy.canManageAssignedMatch(#dto.matchId, authentication))"
    )
    public ResponseEntity<MatchEventResponseDTO> registerCard(@RequestBody RegisterCardDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchEventMapper.toResponseDTO(matchService.registerCard(dto)));
    }

    @GetMapping("/{id}/events")
    @PreAuthorize(
            "hasAuthority('result:read:any') " +
            "or (hasAuthority('result:read:assigned') " +
            "and @matchAccessPolicy.canManageAssignedMatch(#id, authentication))"
    )
    public ResponseEntity<List<MatchEventResponseDTO>> getEvents(@PathVariable String id) {
        return ResponseEntity.ok(
                matchService.getEventsByMatch(id).stream()
                        .map(matchEventMapper::toResponseDTO)
                        .toList()
        );
    }

    @GetMapping("/standings/{tournamentId}")
    @PreAuthorize("hasAuthority('standings:read:any')")
    public ResponseEntity<List<StandingsResponseDTO>> getStandings(@PathVariable String tournamentId) {
        List<Standings> standings = standingsRepository
                .findByTournamentIdOrderByPointsDescGoalDifferenceDesc(tournamentId);
        return ResponseEntity.ok(standings.stream().map(standingsMapper::toResponseDTO).toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('referee-match:read:assigned') or hasAuthority('match:read:any')")
    public ResponseEntity<MatchResponseDTO> getMatchById(@PathVariable String id) {
        return ResponseEntity.ok(matchMapper.toResponseDTO(matchService.getMatchById(id)));
    }

    @GetMapping("/referee/{refereeId}")
    @PreAuthorize("hasAuthority('referee-match:read:assigned')")
    public ResponseEntity<List<MatchResponseDTO>> getMatchesByReferee(@PathVariable String refereeId) {
        return ResponseEntity.ok(
                matchService.getMatchesByReferee(refereeId).stream()
                        .map(matchMapper::toResponseDTO)
                        .toList()
        );
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<MatchResponseDTO>> getByTournament(@PathVariable String tournamentId) {
        List<Match> matches = matchService.getMatchesByTournament(tournamentId);
        return ResponseEntity.ok(matches.stream().map(matchMapper::toResponseDTO).toList());
    }
}