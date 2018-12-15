package gr.teicm.ieee.madc.disasternotifierservice.service.security;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;

import java.security.NoSuchAlgorithmException;

public interface AuthService {
    Auth create(User user) throws NoSuchAlgorithmException;

    User getUser(String token) throws UnauthorizedException, NoSuchAlgorithmException;
}
