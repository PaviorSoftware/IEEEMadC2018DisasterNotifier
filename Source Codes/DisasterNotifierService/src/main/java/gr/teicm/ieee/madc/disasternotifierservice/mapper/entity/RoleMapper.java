package gr.teicm.ieee.madc.disasternotifierservice.mapper.entity;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    List<RoleDTO> toDTO(List<Role> disasters);

    RoleDTO toDTO(Role disaster);

    List<Role> fromDTO(List<RoleDTO> disasterDTOS);

    Role fromDTO(RoleDTO disasterDTO);

}
