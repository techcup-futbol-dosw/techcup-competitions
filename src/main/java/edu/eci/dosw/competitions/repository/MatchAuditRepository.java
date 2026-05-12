package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.MatchAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchAuditRepository extends JpaRepository<MatchAudit, String> {

    List<MatchAudit> findByMatchId(String matchId);
}