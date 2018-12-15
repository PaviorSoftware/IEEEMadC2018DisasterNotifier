package gr.teicm.ieee.madc.disasternotifierservice.service.rpc;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;
import gr.teicm.ieee.madc.disasternotifierservice.model.LoginModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.RegisterModel;

import java.security.NoSuchAlgorithmException;

public interface RegisterLoginService {
    Auth login(LoginModel loginModel) throws UnauthorizedException, NoSuchAlgorithmException, EntityNotFoundException;

    Auth register(RegisterModel registerModel) throws EntityExistsException, NoSuchAlgorithmException, UnauthorizedException, ForbiddenException;

}
