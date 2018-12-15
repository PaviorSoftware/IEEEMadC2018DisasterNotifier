package gr.teicm.ieee.madc.disasternotifierservice.service.rpc.impl;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.model.LoginModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.RegisterModel;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.RoleService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.UserService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rpc.RegisterLoginService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.AuthService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class RegisterLoginServiceImpl implements RegisterLoginService {

    private final UserService userService;
    private final AuthService authService;

    public RegisterLoginServiceImpl(UserService userService, AuthService authService, RoleService roleService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public Auth login(LoginModel loginModel) throws UnauthorizedException, NoSuchAlgorithmException, EntityNotFoundException {

        User user = userService.getWithUsernameAndPassword(
                loginModel.getUsername(),
                loginModel.getPassword()
        );

        return authService.create(user);
    }

    @Override
    public Auth register(RegisterModel registerModel) throws EntityExistsException, NoSuchAlgorithmException, UnauthorizedException, ForbiddenException {

        User user = userService.fromRegisterModel(registerModel);

        user = userService.post(user, "anonymous");

        return authService.create(user);
    }
}
