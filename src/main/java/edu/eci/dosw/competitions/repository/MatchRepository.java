package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {

    Optional<Match> findById(String id);

    List<Match> findByTournamentId(String tournamentId);

    void deleteById(String id);
}