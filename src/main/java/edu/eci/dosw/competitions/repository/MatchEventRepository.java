package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Card;
import edu.eci.dosw.competitions.entity.Goal;
import edu.eci.dosw.competitions.entity.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, String> {

    List<MatchEvent> findByMatchId(String matchId);

    @Query("SELECT g FROM Goal g WHERE g.matchId = :matchId")
    List<Goal> findGoalsByMatchId(@Param("matchId") String matchId);

    @Query("SELECT c FROM Card c WHERE c.matchId = :matchId")
    List<Card> findCardsByMatchId(@Param("matchId") String matchId);

    @Query("SELECT e.matchId FROM MatchEvent e WHERE e.id = :eventId")
    Optional<String> findMatchIdByEventId(@Param("eventId") String eventId);
}