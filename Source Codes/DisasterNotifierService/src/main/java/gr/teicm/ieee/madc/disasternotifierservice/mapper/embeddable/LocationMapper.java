package gr.teicm.ieee.madc.disasternotifierservice.mapper.embeddable;

import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.dto.embeddable.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    List<LocationDTO> toDTO(List<Location> locations);

    LocationDTO toDTO(Location location);

    List<Location> fromDTO(List<LocationDTO> locationDTOS);

    Location fromDTO(LocationDTO locationDTO);

}
