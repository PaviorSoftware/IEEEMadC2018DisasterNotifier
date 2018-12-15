package gr.teicm.ieee.madc.disasternotifierservice.api.rest.impl;

import gr.teicm.ieee.madc.disasternotifierservice.api.rest.UserController;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.converter.ResponseModelToResponseEntity;
import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.UserDTO;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.entity.UserMapper;
import gr.teicm.ieee.madc.disasternotifierservice.model.ListResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.SingleResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserControllerImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<?> get(String authorization) {

        ListResponseModel<UserDTO> usersListResponseEntity;

        List<User> users = userService.get(authorization);

        if (users.isEmpty()) {
            usersListResponseEntity = new ListResponseModel<>(
                    HttpStatus.NO_CONTENT,
                    null,
                    null
            );
        } else {

            for (User user : users) {
                cleanRecord(user);
            }

            usersListResponseEntity = new ListResponseModel<>(
                    HttpStatus.OK,
                    null,
                    userMapper.toDTO(users)
            );
        }

        return ResponseModelToResponseEntity.convert(
                usersListResponseEntity
        );
    }

    @Override
    public ResponseEntity<?> get(Long id, String authorization) {

        SingleResponseModel<UserDTO> userSingleResponseModel;

        try {
            User user = userService.get(id, authorization);

            cleanRecord(user);

            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    userMapper.toDTO(user)
            );
        } catch (EntityNotFoundException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity.convert(
                userSingleResponseModel
        );
    }

    @Override
    public ResponseEntity<?> post(UserDTO entity, String authorization) {
        SingleResponseModel<UserDTO> userSingleResponseModel;

        User userEntity = userMapper.fromDTO(entity);

        try {
            User user = userService.post(userEntity, "");

            cleanRecord(user);

            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.CREATED,
                    null,
                    userMapper.toDTO(user)
            );
        } catch (EntityExistsException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.CONFLICT,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (ForbiddenException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity.convert(
                userSingleResponseModel
        );

    }

    @Override
    public ResponseEntity<?> put(Long id, UserDTO entity, String authorization) {
        SingleResponseModel<UserDTO> userSingleResponseModel;

        User userEntity = userMapper.fromDTO(entity);

        try {
            User user = userService.put(id, userEntity, "");

            cleanRecord(user);

            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    userMapper.toDTO(user)
            );
        } catch (ForbiddenException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (EntityNotFoundException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        } catch (EntityExistsException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.CONFLICT,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity.convert(
                userSingleResponseModel
        );
    }

    @Override
    public ResponseEntity<?> delete(Long id, String authorization) {
        SingleResponseModel<UserDTO> userSingleResponseModel;

        try {
            userService.delete(id, "");
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NO_CONTENT,
                    null,
                    null
            );
        } catch (ForbiddenException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (EntityNotFoundException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity.convert(
                userSingleResponseModel
        );
    }

    private void cleanRecord(User user) {
        Location location = new Location();
        location.setLatitude((float) 0.0);
        location.setLongitude((float) 0.0);

        user.setLocation(location);
        user.setPassword("");
        user.setFirebaseToken("");
    }

}
