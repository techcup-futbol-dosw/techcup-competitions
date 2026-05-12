package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.MatchEventResponseDTO;
import edu.eci.dosw.competitions.entity.MatchEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchEventMapper {

    @Mapping(target = "description", expression = "java(event.getDescription())")
    MatchEventResponseDTO toResponseDTO(MatchEvent event);
}
