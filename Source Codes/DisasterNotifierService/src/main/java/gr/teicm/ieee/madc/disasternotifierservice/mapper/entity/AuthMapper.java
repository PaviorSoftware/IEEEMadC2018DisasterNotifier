package gr.teicm.ieee.madc.disasternotifierservice.mapper.entity;


import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.AuthDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        uses = UserMapper.class
)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    List<AuthDTO> toDTO(List<Auth> auths);

    AuthDTO toDTO(Auth auth);

    List<Auth> fromDTO(List<AuthDTO> authDTOS);

    Auth fromDTO(AuthDTO authDTO);
}
