package edu.eci.dosw.competitions.service;

import edu.eci.dosw.competitions.dtos.CreateLineupDTO;
import edu.eci.dosw.competitions.dtos.UpdateLineupDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import edu.eci.dosw.competitions.model.LineupModel;
import edu.eci.dosw.competitions.repository.LineupRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LineupService {

    private final LineupRepository lineupRepository;

    public LineupService(LineupRepository lineupRepository) {
        this.lineupRepository = lineupRepository;
    }

    public Lineup createLineup(CreateLineupDTO dto) {
        Lineup lineup = new Lineup();
        lineup.setId(UUID.randomUUID().toString());
        lineup.setMatchId(dto.getMatchId());
        lineup.setTeamId(dto.getTeamId());
        lineup.setFormation(dto.getFormation());
        lineup.setStarterIds(dto.getStarterIds());
        lineup.setSubstituteIds(dto.getSubstituteIds());
        lineup.setConfirmed(false);

        return lineupRepository.save(lineup);
    }

    public Lineup updateLineup(String id, UpdateLineupDTO dto) {
        Lineup lineup = lineupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lineup not found: " + id));

        LineupModel model = new LineupModel(lineup);
        model.update();

        if (dto.getFormation() != null) lineup.setFormation(dto.getFormation());
        if (dto.getStarterIds() != null) lineup.setStarterIds(dto.getStarterIds());
        if (dto.getSubstituteIds() != null) lineup.setSubstituteIds(dto.getSubstituteIds());

        return lineupRepository.save(lineup);
    }

    public Lineup confirmLineup(String id) {
        Lineup lineup = lineupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lineup not found: " + id));

        if (!lineup.validateStarters()) {
            throw new RuntimeException("Lineup must have exactly 11 starters");
        }
        if (!lineup.validateGoalkeeper()) {
            throw new RuntimeException("Lineup must have at least one goalkeeper");
        }

        LineupModel model = new LineupModel(lineup);
        model.confirm();

        return lineupRepository.save(lineup);
    }

    public Lineup getLineup(String matchId, String teamId) {
        return lineupRepository.findByMatchIdAndTeamId(matchId, teamId)
                .orElseThrow(() -> new RuntimeException("Lineup not found for match " + matchId + " and team " + teamId));
    }
}