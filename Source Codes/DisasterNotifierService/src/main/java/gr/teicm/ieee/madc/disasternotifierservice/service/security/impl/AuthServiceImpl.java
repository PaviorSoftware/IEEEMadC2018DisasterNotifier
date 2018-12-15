package gr.teicm.ieee.madc.disasternotifierservice.service.security.impl;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.config.ApplicationConfiguration;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.domain.repository.AuthRepository;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.DateService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.AuthService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.Base64Service;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.EncryptService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.HashingService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final DateService dateService;
    private final EncryptService encryptService;
    private final HashingService hashingService;
    private final Base64Service base64Service;

    public AuthServiceImpl(AuthRepository authRepository, DateService dateService, EncryptService encryptService, HashingService hashingService, Base64Service base64Service) {
        this.authRepository = authRepository;
        this.dateService = dateService;
        this.encryptService = encryptService;
        this.hashingService = hashingService;
        this.base64Service = base64Service;
    }


    @Override
    public Auth create(User user) throws NoSuchAlgorithmException {
        String encryptedToken = createEncryptedToken(user);

        Long time = dateService.getTimeAfterHours(ApplicationConfiguration.tokenDurationInHours);

        Auth dbAuth = createAndSaveDBAuth(user, base64Service.encode(encryptedToken), time);

        return createResponseAuth(user, base64Service.encode(encryptedToken), time, dbAuth.getId());
    }

    private Auth createAndSaveDBAuth(User user, String encryptedToken, Long time) throws NoSuchAlgorithmException {
        Auth dbAuth = new Auth();
        dbAuth.setAccessToken(hashingService.hash(encryptedToken));
        dbAuth.setUser(user);
        dbAuth.setExpire(time);
        dbAuth = authRepository.save(dbAuth);
        return dbAuth;
    }

    private Auth createResponseAuth(User user, String encryptedToken, Long time, Long id) {
        Auth responseAuth = new Auth();
        responseAuth.setAccessToken(encryptedToken);
        responseAuth.setUser(user);
        responseAuth.setExpire(time);
        responseAuth.setId(id);
        return responseAuth;
    }

    private String createUnencryptedToken(User user) {
        return String.valueOf(
                dateService.getCurrentTime()
        ).concat(
                String.valueOf(user.getId())
        ).concat(
                user.getUsername()
        ).concat(
                user.getPassword()
        );
    }

    private String createEncryptedToken(String unencryptedToken) {
        return encryptService.encrypt(unencryptedToken);
    }

    private String createEncryptedToken(User user) {
        return createEncryptedToken(
                createUnencryptedToken(user)
        );
    }

    @Override
    public User getUser(String token) throws UnauthorizedException, NoSuchAlgorithmException {

        Optional<Auth> optionalAuth = authRepository.findByAccessToken(
                hashingService.hash(
                        encryptService.decrypt(token)
                )
        );

        if (!optionalAuth.isPresent()) {
            throw new UnauthorizedException();
        }

        Auth auth = optionalAuth.get();

        


        return auth.getUser();
    }
}
