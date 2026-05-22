package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.RefereeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RefereeAssignmentRepository extends JpaRepository<RefereeAssignment, String> {

    boolean existsByMatchIdAndRefereeIdAndActiveTrue(String matchId, Long refereeId);

    List<RefereeAssignment> findByRefereeIdAndActiveTrue(Long refereeId);
}
