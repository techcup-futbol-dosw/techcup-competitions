package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.MatchEventResponseDTO;
import edu.eci.dosw.competitions.dtos.MatchResponseDTO;
import edu.eci.dosw.competitions.dtos.StandingsResponseDTO;
import edu.eci.dosw.competitions.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "status", expression = "java(match.getStatus().name())")
    @Mapping(target = "phase", expression = "java(match.getPhase().name())")
    MatchResponseDTO toResponseDTO(Match match);

    @Mapping(target = "description", expression = "java(event.getDescription())")
    MatchEventResponseDTO toEventResponseDTO(MatchEvent event);

    StandingsResponseDTO toStandingsResponseDTO(Standings standings);
}
