package edu.eci.dosw.competitions.controller;

import edu.eci.dosw.competitions.dtos.*;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lineupMapper.toResponseDTO(lineupService.createLineup(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineupResponseDTO> updateLineup(@PathVariable String id,
                                                          @RequestBody UpdateLineupDTO dto) {
        return ResponseEntity.ok(lineupMapper.toResponseDTO(lineupService.updateLineup(id, dto)));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<LineupResponseDTO> confirmLineup(@PathVariable String id) {
        return ResponseEntity.ok(lineupMapper.toResponseDTO(lineupService.confirmLineup(id)));
    }

    @GetMapping("/match/{matchId}/team/{teamId}")
    public ResponseEntity<LineupResponseDTO> getLineup(@PathVariable String matchId,
                                                       @PathVariable String teamId) {
        return ResponseEntity.ok(lineupMapper.toResponseDTO(lineupService.getLineup(matchId, teamId)));
    }
}