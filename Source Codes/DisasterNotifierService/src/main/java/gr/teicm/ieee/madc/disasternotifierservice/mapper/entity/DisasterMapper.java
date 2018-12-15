package gr.teicm.ieee.madc.disasternotifierservice.mapper.entity;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.DisasterDTO;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.embeddable.LocationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        uses = {
                LocationMapper.class,
                UserMapper.class
        }
)
public interface DisasterMapper {
    DisasterMapper INSTANCE = Mappers.getMapper(DisasterMapper.class);

    List<DisasterDTO> toDTO(List<Disaster> disasters);

    @Mapping(source = "disasterType", target = "disasterType")
    DisasterDTO toDTO(Disaster disaster);

    List<Disaster> fromDTO(List<DisasterDTO> disasterDTOS);

    Disaster fromDTO(DisasterDTO disasterDTO);
}
