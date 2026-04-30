package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Standings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StandingsRepository extends JpaRepository<Standings, String> {

    List<Standings> findByTournamentId(String tournamentId);

    Optional<Standings> findByTournamentIdAndTeamId(String tournamentId, String teamId);

    List<Standings> findByTournamentIdOrderByPointsDescGoalDifferenceDesc(String tournamentId);
}