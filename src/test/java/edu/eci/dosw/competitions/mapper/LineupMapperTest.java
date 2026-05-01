package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.LineupResponseDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineupMapperTest {

    private LineupMapper lineupMapper;

    @BeforeEach
    void setUp() {
        lineupMapper = Mappers.getMapper(LineupMapper.class);
    }

    // ===== UNIT TESTS =====

    @Test
    void testToResponseDTO_mapsAllFields() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-1");
        lineup.setMatchId("match-1");
        lineup.setTeamId("team-1");
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineup.setSubstituteIds(List.of("p12","p13","p14"));
        lineup.setConfirmed(false);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertEquals("lineup-1", dto.getId());
        assertEquals("match-1", dto.getMatchId());
        assertEquals("team-1", dto.getTeamId());
        assertEquals("4-3-3", dto.getFormation());
        assertEquals(11, dto.getStarterIds().size());
        assertEquals(3, dto.getSubstituteIds().size());
        assertFalse(dto.isConfirmed());
    }

    @Test
    void testToResponseDTO_confirmedLineup() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-2");
        lineup.setMatchId("match-2");
        lineup.setTeamId("team-2");
        lineup.setFormation("4-4-2");
        lineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineup.setSubstituteIds(List.of("p12","p13"));
        lineup.setConfirmed(true);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertTrue(dto.isConfirmed());
        assertEquals("4-4-2", dto.getFormation());
    }

    @Test
    void testToResponseDTO_emptySubstitutes() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-3");
        lineup.setMatchId("match-3");
        lineup.setTeamId("team-3");
        lineup.setFormation("3-5-2");
        lineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineup.setSubstituteIds(Collections.emptyList());
        lineup.setConfirmed(false);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertEquals("lineup-3", dto.getId());
        assertNotNull(dto.getSubstituteIds());
        assertTrue(dto.getSubstituteIds().isEmpty());
    }

    @Test
    void testToResponseDTO_nullOptionalFields() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-4");
        lineup.setMatchId("match-4");
        lineup.setTeamId("team-4");
        lineup.setConfirmed(false);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertEquals("lineup-4", dto.getId());
        assertEquals("match-4", dto.getMatchId());
        assertEquals("team-4", dto.getTeamId());
        assertNull(dto.getFormation());
        assertNull(dto.getStarterIds());
        assertNull(dto.getSubstituteIds());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testToResponseDTO_fullLineupWithAllFormation() {
        List<String> starters = Arrays.asList(
                "player-1", "player-2", "player-3", "player-4", "player-5",
                "player-6", "player-7", "player-8", "player-9", "player-10", "player-11"
        );
        List<String> substitutes = Arrays.asList(
                "sub-1", "sub-2", "sub-3", "sub-4", "sub-5"
        );

        Lineup lineup = new Lineup();
        lineup.setId("lineup-int");
        lineup.setMatchId("match-int");
        lineup.setTeamId("team-int");
        lineup.setFormation("1-4-3-3");
        lineup.setStarterIds(starters);
        lineup.setSubstituteIds(substitutes);
        lineup.setConfirmed(true);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertEquals("lineup-int", dto.getId());
        assertEquals("match-int", dto.getMatchId());
        assertEquals("team-int", dto.getTeamId());
        assertEquals("1-4-3-3", dto.getFormation());
        assertEquals(11, dto.getStarterIds().size());
        assertEquals(5, dto.getSubstituteIds().size());
        assertTrue(dto.isConfirmed());
        assertEquals("player-1", dto.getStarterIds().get(0));
        assertEquals("sub-1", dto.getSubstituteIds().get(0));
    }

    @Test
    void testToResponseDTO_preservesPlayerOrder() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-order");
        lineup.setMatchId("match-order");
        lineup.setTeamId("team-order");
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(List.of("gk-1","def-1","def-2","def-3","def-4","mid-1","mid-2","mid-3","fwd-1","fwd-2","fwd-3"));
        lineup.setSubstituteIds(List.of("sub-gk","sub-def","sub-mid"));
        lineup.setConfirmed(false);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertEquals("gk-1", dto.getStarterIds().get(0));
        assertEquals("fwd-3", dto.getStarterIds().get(10));
        assertEquals("sub-gk", dto.getSubstituteIds().get(0));
    }
}
