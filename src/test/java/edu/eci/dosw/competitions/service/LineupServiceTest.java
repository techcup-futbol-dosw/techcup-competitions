package edu.eci.dosw.competitions.service;

import edu.eci.dosw.competitions.dtos.CreateLineupDTO;
import edu.eci.dosw.competitions.dtos.UpdateLineupDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import edu.eci.dosw.competitions.repository.LineupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LineupServiceTest {

    @Mock private LineupRepository lineupRepository;

    @InjectMocks
    private LineupService lineupService;

    private Lineup lineup;

    @BeforeEach
    void setUp() {
        lineup = new Lineup();
        lineup.setId("lineup-1");
        lineup.setMatchId("match-1");
        lineup.setTeamId("team-1");
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineup.setSubstituteIds(List.of("p12","p13","p14"));
        lineup.setConfirmed(false);
    }

    // ===== UNIT TESTS =====

    @Test
    void testCreateLineup_savedWithConfirmedFalse() {
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("team-1");
        dto.setFormation("4-3-3");
        dto.setStarterIds(lineup.getStarterIds());
        dto.setSubstituteIds(lineup.getSubstituteIds());

        when(lineupRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Lineup result = lineupService.createLineup(dto);

        assertFalse(result.isConfirmed());
        verify(lineupRepository, times(1)).save(any(Lineup.class));
    }

    @Test
    void testUpdateLineup_throwsWhenConfirmed() {
        lineup.setConfirmed(true);
        when(lineupRepository.findById("lineup-1")).thenReturn(Optional.of(lineup));

        UpdateLineupDTO dto = new UpdateLineupDTO();
        dto.setFormation("4-4-2");

        assertThrows(RuntimeException.class, () -> lineupService.updateLineup("lineup-1", dto));
    }

    @Test
    void testConfirmLineup_throwsWhenNotEnoughStarters() {
        lineup.setStarterIds(List.of("p1","p2","p3"));
        when(lineupRepository.findById("lineup-1")).thenReturn(Optional.of(lineup));

        assertThrows(RuntimeException.class, () -> lineupService.confirmLineup("lineup-1"));
    }

    @Test
    void testGetLineup_throwsWhenNotFound() {
        when(lineupRepository.findByMatchIdAndTeamId("match-x", "team-x"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> lineupService.getLineup("match-x", "team-x"));
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testCreateLineup_setsCorrectMatchAndTeam() {
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId("match-int");
        dto.setTeamId("team-int");
        dto.setFormation("4-3-3");
        dto.setStarterIds(lineup.getStarterIds());
        dto.setSubstituteIds(lineup.getSubstituteIds());

        when(lineupRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Lineup result = lineupService.createLineup(dto);

        assertEquals("match-int", result.getMatchId());
        assertEquals("team-int", result.getTeamId());
        assertNotNull(result.getId());
    }

    @Test
    void testFullLineupLifecycle_createUpdateConfirm() {
        // crear
        when(lineupRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        CreateLineupDTO dto = new CreateLineupDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("team-1");
        dto.setFormation("4-3-3");
        dto.setStarterIds(lineup.getStarterIds());
        dto.setSubstituteIds(lineup.getSubstituteIds());
        Lineup created = lineupService.createLineup(dto);
        assertFalse(created.isConfirmed());

        // actualizar
        when(lineupRepository.findById(any())).thenReturn(Optional.of(created));
        UpdateLineupDTO update = new UpdateLineupDTO();
        update.setFormation("4-4-2");
        Lineup updated = lineupService.updateLineup(created.getId(), update);
        assertEquals("4-4-2", updated.getFormation());

        // confirmar
        Lineup confirmed = lineupService.confirmLineup(created.getId());
        assertTrue(confirmed.isConfirmed());
    }

    @Test
    void testUpdateLineup_afterConfirm_throwsException() {
        lineup.setConfirmed(true);
        when(lineupRepository.findById("lineup-1")).thenReturn(Optional.of(lineup));

        UpdateLineupDTO update = new UpdateLineupDTO();
        update.setFormation("3-5-2");

        assertThrows(RuntimeException.class,
                () -> lineupService.updateLineup("lineup-1", update));
    }
}