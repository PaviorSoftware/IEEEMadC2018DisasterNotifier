package gr.teicm.ieee.madc.disasternotifierservice.mapper.entity;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.UserDTO;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.ReferenceMapper;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.embeddable.LocationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        uses = {
                ReferenceMapper.class,
                LocationMapper.class
        }
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDTO> toDTO(List<User> users);

    UserDTO toDTO(User user);

    List<User> fromDTO(List<UserDTO> userDTOS);

    User fromDTO(UserDTO userDTO);
}
