package gr.teicm.ieee.madc.disasternotifierservice.api.rest.impl;

import gr.teicm.ieee.madc.disasternotifierservice.api.rest.DisasterController;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.converter.ResponseModelToResponseEntity;
import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.DisasterDTO;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.entity.DisasterMapper;
import gr.teicm.ieee.madc.disasternotifierservice.model.ListResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.SingleResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.DisasterService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.UserService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/disasters")
public class DisasterControllerImpl implements DisasterController {

    private final DisasterService disasterService;
    private final DisasterMapper disasterMapper;
    private final UserService userService;

    public DisasterControllerImpl(DisasterService disasterService, DisasterMapper disasterMapper, UserService userService) {
        this.disasterService = disasterService;
        this.disasterMapper = disasterMapper;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> get(String authorization) {
        ListResponseModel<DisasterDTO> disasterListResponseModel;

        List<Disaster> disasters = disasterService.get(authorization);

        if (disasters.isEmpty()) {
            disasterListResponseModel = new ListResponseModel<>(
                    HttpStatus.NO_CONTENT,
                    null,
                    null
            );
        } else {

            for (Disaster disaster : disasters) {
                cleanRecord(disaster);
            }

            disasterListResponseModel = new ListResponseModel<>(
                    HttpStatus.OK,
                    null,
                    disasterMapper.toDTO(disasters)
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterListResponseModel);


    }

    @Override
    public ResponseEntity<?> get(Long id, String authorization) {
        SingleResponseModel<DisasterDTO> disasterSingleResponseModel;

        try {
            Disaster disaster = disasterService.get(id, authorization);

            cleanRecord(disaster);

            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    disasterMapper.toDTO(disaster)
            );
        } catch (EntityNotFoundException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterSingleResponseModel);

    }

    @Override
    public ResponseEntity<?> post(DisasterDTO entity, String authorization) {
        SingleResponseModel<DisasterDTO> disasterSingleResponseModel;

        try {
            Disaster post = disasterService.post(
                    disasterMapper.fromDTO(entity),
                    authorization
            );

            userService.notify(post);

            cleanRecord(post);

            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.CREATED,
                    null,
                    disasterMapper.toDTO(post)
            );
        } catch (EntityExistsException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (ForbiddenException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException | JSONException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> put(Long id, DisasterDTO entity, String authorization) {
        SingleResponseModel<DisasterDTO> disasterSingleResponseModel;

        try {
            Disaster put = disasterService.put(
                    id,
                    disasterMapper.fromDTO(entity),
                    authorization
            );

            cleanRecord(put);

            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    disasterMapper.toDTO(put)
            );
        } catch (ForbiddenException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    null
            );
        } catch (EntityExistsException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.CONFLICT,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (EntityNotFoundException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> delete(Long id, String authorization) {
        SingleResponseModel<DisasterDTO> disasterSingleResponseModel;

        try {
            disasterService.delete(
                    id,
                    authorization
            );
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NO_CONTENT,
                    null,
                    null
            );
        } catch (EntityNotFoundException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        } catch (ForbiddenException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            disasterSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(disasterSingleResponseModel);
    }

    private void cleanRecord(Disaster disaster) {
        User creator = disaster.getCreator();

        Location location = new Location();
        location.setLatitude((float) 0.0);
        location.setLongitude((float) 0.0);

        creator.setLocation(location);

        creator.setFirebaseToken("");
        creator.setPassword("");
    }
}
