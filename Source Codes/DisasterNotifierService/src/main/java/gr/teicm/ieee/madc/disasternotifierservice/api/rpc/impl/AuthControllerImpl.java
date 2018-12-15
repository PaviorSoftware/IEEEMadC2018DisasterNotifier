package gr.teicm.ieee.madc.disasternotifierservice.api.rpc.impl;

import gr.teicm.ieee.madc.disasternotifierservice.api.rpc.AuthController;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.converter.ResponseModelToResponseEntity;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.AuthDTO;
import gr.teicm.ieee.madc.disasternotifierservice.mapper.entity.AuthMapper;
import gr.teicm.ieee.madc.disasternotifierservice.model.LoginModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.RegisterModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.SingleResponseModel;
import gr.teicm.ieee.madc.disasternotifierservice.service.rpc.RegisterLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {

    private final RegisterLoginService registerLoginService;
    private final AuthMapper authMapper;

    public AuthControllerImpl(RegisterLoginService registerLoginService, AuthMapper authMapper) {
        this.registerLoginService = registerLoginService;
        this.authMapper = authMapper;
    }

    @Override
    public ResponseEntity<?> login(LoginModel loginModel) {

        SingleResponseModel<AuthDTO> authSingleResponseModel;

        try {
            Auth auth = registerLoginService.login(loginModel);
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    authMapper.toDTO(auth)
            );
        } catch (UnauthorizedException e) {
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        } catch (EntityNotFoundException e) {
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }


        return ResponseModelToResponseEntity
                .convert(authSingleResponseModel);
    }

    @Override
    public ResponseEntity<?> register(RegisterModel registerModel) {
        SingleResponseModel<AuthDTO> authSingleResponseModel;

        try {
            Auth auth = registerLoginService.register(registerModel);
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.OK,
                    null,
                    authMapper.toDTO(auth)
            );
        } catch (EntityExistsException e) {
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.CONFLICT,
                    e.getMessage(),
                    null
            );
        } catch (NoSuchAlgorithmException | ForbiddenException e) {
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        } catch (UnauthorizedException e) {
            authSingleResponseModel = new SingleResponseModel<>(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
            );
        }

        return ResponseModelToResponseEntity
                .convert(authSingleResponseModel);

    }
}
