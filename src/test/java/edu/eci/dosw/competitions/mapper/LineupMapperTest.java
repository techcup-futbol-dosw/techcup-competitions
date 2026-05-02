package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.LineupResponseDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineupMapperTest {

    private LineupMapper lineupMapper;
    private Lineup baseLineup;

    @BeforeEach
    void setUp() {
        lineupMapper = Mappers.getMapper(LineupMapper.class);

        baseLineup = new Lineup();
        baseLineup.setId("lineup-1");
        baseLineup.setMatchId("match-1");
        baseLineup.setTeamId("team-1");
        baseLineup.setFormation("4-3-3");
        baseLineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        baseLineup.setSubstituteIds(List.of("p12","p13","p14"));
        baseLineup.setConfirmed(false);
    }

    @Test
    void testToResponseDTO_mapsAllFields() {
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);
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
        baseLineup.setConfirmed(true);
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);
        assertTrue(dto.isConfirmed());
    }

    @Test
    void testToResponseDTO_preservesPlayerOrder() {
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);
        assertEquals("p1", dto.getStarterIds().get(0));
        assertEquals("p11", dto.getStarterIds().get(10));
        assertEquals("p12", dto.getSubstituteIds().get(0));
    }

    @Test
    void testToResponseDTO_nullOptionalFields() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-null");
        lineup.setMatchId("match-2");
        lineup.setTeamId("team-2");

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);
        assertEquals("lineup-null", dto.getId());
        assertNull(dto.getFormation());
        assertNull(dto.getStarterIds());
        assertNull(dto.getSubstituteIds());
    }

    @Test
    void testToResponseDTO_differentFormation() {
        baseLineup.setFormation("4-4-2");
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);
        assertEquals("4-4-2", dto.getFormation());
    }

    @Test
    void testToResponseDTO_emptySubstitutes() {
        baseLineup.setSubstituteIds(List.of());
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);
        assertTrue(dto.getSubstituteIds().isEmpty());
        assertEquals(11, dto.getStarterIds().size());
    }

    // ===== INTEGRACIÓN =====

    @Test
    void integracion_mapeoCamposIdentidad() {
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);
        assertEquals(baseLineup.getId(), dto.getId());
        assertEquals(baseLineup.getMatchId(), dto.getMatchId());
        assertEquals(baseLineup.getTeamId(), dto.getTeamId());
        assertEquals(baseLineup.getFormation(), dto.getFormation());
    }

    @Test
    void integracion_formacion442_confirmado() {
        baseLineup.setFormation("4-4-2");
        baseLineup.setConfirmed(true);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);

        assertEquals("4-4-2", dto.getFormation());
        assertTrue(dto.isConfirmed());
        assertEquals(11, dto.getStarterIds().size());
        assertEquals(3, dto.getSubstituteIds().size());
    }

    @Test
    void integracion_suplentesExtendidos() {
        baseLineup.setSubstituteIds(List.of("s1","s2","s3","s4","s5","s6","s7"));
        LineupResponseDTO dto = lineupMapper.toResponseDTO(baseLineup);

        assertEquals(7, dto.getSubstituteIds().size());
        assertEquals("s1", dto.getSubstituteIds().get(0));
        assertEquals("s7", dto.getSubstituteIds().get(6));
    }

    @Test
    void integracion_lineupDiferenteEquipo() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-b");
        lineup.setMatchId("match-final");
        lineup.setTeamId("team-b");
        lineup.setFormation("3-5-2");
        lineup.setStarterIds(List.of("a1","a2","a3","a4","a5","a6","a7","a8","a9","a10","a11"));
        lineup.setSubstituteIds(List.of("a12","a13","a14","a15","a16"));
        lineup.setConfirmed(true);

        LineupResponseDTO dto = lineupMapper.toResponseDTO(lineup);

        assertEquals("lineup-b", dto.getId());
        assertEquals("match-final", dto.getMatchId());
        assertEquals("team-b", dto.getTeamId());
        assertEquals("3-5-2", dto.getFormation());
        assertEquals(11, dto.getStarterIds().size());
        assertEquals(5, dto.getSubstituteIds().size());
        assertTrue(dto.isConfirmed());
    }
}

