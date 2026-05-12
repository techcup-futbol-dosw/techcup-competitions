package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Lineup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LineupRepository extends JpaRepository<Lineup, String> {

    Optional<Lineup> findByMatchIdAndTeamId(String matchId, String teamId);
}