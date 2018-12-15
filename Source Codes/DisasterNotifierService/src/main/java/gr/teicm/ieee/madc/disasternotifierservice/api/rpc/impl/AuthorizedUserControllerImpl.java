package gr.teicm.ieee.madc.disasternotifierservice.api.rpc.impl;

import gr.teicm.ieee.madc.disasternotifierservice.api.rpc.AuthorizedUserController;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.converter.ResponseModelToResponseEntity;
import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.dto.embeddable.LocationDTO;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.DisasterDTO;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.UserDTO;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.embeddable.LocationMapper;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.entity.DisasterMapper;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.entity.UserMapper;
import gr.teicm.ieee.madc.disasternotifierservice.model.FirebaseModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.ListResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.SingleResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.DisasterService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.UserService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/me")
public class AuthorizedUserControllerImpl implements AuthorizedUserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;
    private final DisasterService disasterService;
    private final DisasterMapper disasterMapper;

    public AuthorizedUserControllerImpl(AuthService authService, UserService userService, UserMapper userMapper, LocationMapper locationMapper, DisasterService disasterService, DisasterMapper disasterMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.locationMapper = locationMapper;
        this.disasterService = disasterService;
        this.disasterMapper = disasterMapper;
    }


    @Override
    public ResponseEntity<?> getMe(String authorization) {
        SingleResponseModel<UserDTO> userSingleResponseModel;

        try {
            User user = getUser(authorization);

            cleanRecord(user);

            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    userMapper.toDTO(user)
            );
        } catch (UnauthorizedException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
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

        return ResponseModelToResponseEntity
                .convert(userSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> myReports(String authorization) {

        ListResponseModel<DisasterDTO> disasterDTOListResponseModel;

        try {
            List<Disaster> mine = disasterService.get(getUser(authorization));
            if (mine.isEmpty()) {
                disasterDTOListResponseModel = new ListResponseModel<>(
                        HttpStatus.NO_CONTENT,
                        null,
                        null
                );
            } else {

                for (Disaster disaster : mine) {
                    cleanRecord(disaster);
                }

                disasterDTOListResponseModel = new ListResponseModel<>(
                        HttpStatus.OK,
                        null,
                        disasterMapper.toDTO(mine)
                );
            }
        } catch (UnauthorizedException e) {
            disasterDTOListResponseModel = new ListResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            disasterDTOListResponseModel = new ListResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterDTOListResponseModel);

    }

    @Override
    public ResponseEntity<?> getMyFirebaseToken(String authorization) {

        SingleResponseModel<FirebaseModel> userSingleResponseModel;

        try {
            FirebaseModel firebaseToken = userService.getFirebaseToken(authorization);
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    firebaseToken

            );
        } catch (UnauthorizedException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
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

        return ResponseModelToResponseEntity
                .convert(userSingleResponseModel);

    }

    @Override
    public ResponseEntity<?> updateMyFirebaseToken(String authorization, FirebaseModel firebaseModel) {
        SingleResponseModel<FirebaseModel> userSingleResponseModel;

        try {
            FirebaseModel firebaseToken = userService.updateFirebaseToken(authorization, firebaseModel);
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    firebaseToken

            );
        } catch (UnauthorizedException e) {
            userSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
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

        return ResponseModelToResponseEntity
                .convert(userSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> getMyLocation(String authorization) {
        SingleResponseModel<LocationDTO> locationSingleResponseModel;

        try {
            Location location = userService.getLocation(authorization);
            locationSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    locationMapper.toDTO(location)
            );
        } catch (UnauthorizedException e) {
            locationSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            locationSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(locationSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> updateMyLocation(String authorization, LocationDTO location) {
        SingleResponseModel<LocationDTO> locationSingleResponseModel;

        try {
            Location updatedLocation = userService.updateLocation(
                    authorization,
                    locationMapper.fromDTO(location)
            );
            locationSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    locationMapper.toDTO(updatedLocation)
            );
        } catch (UnauthorizedException e) {
            locationSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            locationSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(locationSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> getNearDisasters(String authorization, Long distance) {

        ListResponseModel<Disaster> disasterListResponseModel;

        try {
            List<Disaster> near = disasterService.near(getUser(authorization), distance);

            if (near.isEmpty()) {
                disasterListResponseModel = new ListResponseModel<>(
                        HttpStatus.NO_CONTENT,
                        null,
                        null
                );
            } else {

                for (Disaster disaster : near) {
                    cleanRecord(disaster);
                }

                disasterListResponseModel = new ListResponseModel<>(
                        HttpStatus.OK,
                        null,
                        near
                );
            }

        } catch (UnauthorizedException e) {
            disasterListResponseModel = new ListResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            disasterListResponseModel = new ListResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterListResponseModel);

    }

    private void cleanRecord(User user) {
        Location location = new Location();
        location.setLatitude((float) 0.0);
        location.setLongitude((float) 0.0);

        user.setLocation(location);
        user.setPassword("");
        user.setFirebaseToken("");
    }

    private void cleanRecord(Disaster disaster) {
        cleanRecord(disaster.getCreator());
    }

    private User getUser(String authorization) throws UnauthorizedException, NoSuchAlgorithmException {
        return authService.getUser(authorization);
    }


}
