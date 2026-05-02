package edu.eci.dosw.competitions.controller;

import edu.eci.dosw.competitions.dtos.CreateLineupDTO;
import edu.eci.dosw.competitions.dtos.LineupResponseDTO;
import edu.eci.dosw.competitions.dtos.UpdateLineupDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import edu.eci.dosw.competitions.mapper.LineupMapper;
import edu.eci.dosw.competitions.service.LineupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lineups")
public class LineupController {

    private final LineupService lineupService;
    private final LineupMapper lineupMapper;

    public LineupController(LineupService lineupService, LineupMapper lineupMapper) {
        this.lineupService = lineupService;
        this.lineupMapper = lineupMapper;
    }

    @PostMapping
    public ResponseEntity<LineupResponseDTO> createLineup(@RequestBody CreateLineupDTO dto) {
        Lineup lineup = lineupService.createLineup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lineupMapper.toResponseDTO(lineup));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineupResponseDTO> updateLineup(@PathVariable String id,
                                                          @RequestBody UpdateLineupDTO dto) {
        Lineup lineup = lineupService.updateLineup(id, dto);
        return ResponseEntity.ok(lineupMapper.toResponseDTO(lineup));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<LineupResponseDTO> confirmLineup(@PathVariable String id) {
        Lineup lineup = lineupService.confirmLineup(id);
        return ResponseEntity.ok(lineupMapper.toResponseDTO(lineup));
    }

    @GetMapping("/match/{matchId}/team/{teamId}")
    public ResponseEntity<LineupResponseDTO> getLineup(@PathVariable String matchId,
                                                       @PathVariable String teamId) {
        Lineup lineup = lineupService.getLineup(matchId, teamId);
        return ResponseEntity.ok(lineupMapper.toResponseDTO(lineup));
    }
}
