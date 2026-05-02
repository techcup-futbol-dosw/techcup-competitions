package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.StandingsResponseDTO;
import edu.eci.dosw.competitions.entity.Standings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StandingsMapper {

    StandingsResponseDTO toResponseDTO(Standings standings);
}
