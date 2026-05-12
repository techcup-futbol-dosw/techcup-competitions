package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.MatchResponseDTO;
import edu.eci.dosw.competitions.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "status", expression = "java(match.getStatus().name())")
    @Mapping(target = "phase", expression = "java(match.getPhase().name())")
    MatchResponseDTO toResponseDTO(Match match);
}
