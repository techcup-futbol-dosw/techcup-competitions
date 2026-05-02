package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.LineupResponseDTO;
import edu.eci.dosw.competitions.entity.Lineup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LineupMapper {

    LineupResponseDTO toResponseDTO(Lineup lineup);
}
