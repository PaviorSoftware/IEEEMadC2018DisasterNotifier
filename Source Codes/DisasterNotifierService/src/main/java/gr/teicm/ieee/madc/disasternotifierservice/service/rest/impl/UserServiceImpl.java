package gr.teicm.ieee.madc.disasternotifierservice.service.rest.impl;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.config.ApplicationConfiguration;
import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.domain.repository.UserRepository;
import gr.teicm.ieee.madc.disasternotifierservice.model.FirebaseModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.RegisterModel;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.DistanceService;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.FCMService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.RoleService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.UserService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.AuthService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.HashingService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final RoleService roleService;
    private final AuthService authService;
    private final FCMService fcmService;
    private final DistanceService distanceService;

    public UserServiceImpl(UserRepository userRepository, HashingService hashingService, RoleService roleService, AuthService authService, FCMService fcmService, DistanceService distanceService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.roleService = roleService;
        this.authService = authService;
        this.fcmService = fcmService;
        this.distanceService = distanceService;
    }


    @Override
    public List<User> get(String authorization) {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id, String authorization) throws EntityNotFoundException {
        return getUserByIdOrElseThrow(id, new EntityNotFoundException());
    }

    @Override
    public User post(User user, String authorization) throws EntityExistsException, NoSuchAlgorithmException {

        userNotExistsOrElseThrow(user, new EntityExistsException());

        user.setPassword(
                hashingService.hash(user.getPassword())
        );

        user.setRoles(new HashSet<>());

        user.getRoles().add(
                roleService.get("SYS_USER", authorization)
        );

        return userRepository.save(user);
    }

    @Override
    public User put(Long id, User user, String authorization) throws UnauthorizedException, NoSuchAlgorithmException, ForbiddenException, EntityNotFoundException {

        checkIfUserIsGuestAndThrow(authorization, new UnauthorizedException());

        checkIfUserAuthenticatedAndAuthorized(id, authorization);

        user.setId(id);

        user.setPassword(
                hashingService.hash(user.getPassword())
        );

        return userRepository.save(user);

    }

    @Override
    public void delete(Long id, String authorization) throws ForbiddenException, NoSuchAlgorithmException, UnauthorizedException, EntityNotFoundException {
        checkIfUserIsGuestAndThrow(authorization, new UnauthorizedException());
        User user = checkIfUserAuthenticatedAndAuthorizedAndGet(id, authorization);
        userRepository.delete(user);
    }

    private void checkIfUserIsGuestAndThrow(String authorization, UnauthorizedException e) throws UnauthorizedException {
        if (authorization.equals(ApplicationConfiguration.GuestToken)) {
            throw e;
        }
    }


    @Override
    public User addRole(User user, Role role) {
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User addRoles(User user, List<Role> roles) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }

    @Override
    public User fromRegisterModel(RegisterModel registerUser) {
        return new User(
                registerUser.getUsername(),
                registerUser.geteMail(),
                registerUser.getPassword(),
                registerUser.getFirebaseToken(),
                registerUser.getLocation(),
                registerUser.getRoles()
        );
    }

    @Override
    public User getWithUsernameAndPassword(String username, String password) throws EntityNotFoundException, NoSuchAlgorithmException, UnauthorizedException {
        Optional<User> byUsername = userRepository.findByUsername(username);

        if (!byUsername.isPresent()) {
            throw new EntityNotFoundException();
        }

        User user = byUsername.get();

        if (!user.getPassword().equals(hashingService.hash(password))) {
            throw new UnauthorizedException();
        }

        return user;

    }

    @Override
    public boolean isAdmin(User user) {

        boolean result = false;

        for (Role role : user.getRoles()) {
            if (role.getName().equals("SYS_ADMIN")) {
                result = true;
            }
        }

        return !result;

    }

    @Override
    public FirebaseModel updateFirebaseToken(String authorization, FirebaseModel firebaseModel) throws UnauthorizedException, NoSuchAlgorithmException {
        User user = authService.getUser(authorization);
        user.setFirebaseToken(firebaseModel.getFirebaseToken());

        return getFirebaseTokenFrom(
                userRepository.save(user)
        );
    }

    @Override
    public FirebaseModel getFirebaseToken(String authorization) throws UnauthorizedException, NoSuchAlgorithmException {
        return getFirebaseTokenFrom(
                authService.getUser(authorization)
        );
    }

    @Override
    public Location getLocation(String authorization) throws UnauthorizedException, NoSuchAlgorithmException {

        User user = authService.getUser(authorization);

        return user.getLocation();
    }

    @Override
    public Location updateLocation(String authorization, Location location) throws UnauthorizedException, NoSuchAlgorithmException {
        User user = authService.getUser(authorization);

        user.setLocation(location);

        userRepository.save(user);

        return user.getLocation();
    }

    @Override
    public void notify(Disaster disaster) throws JSONException {
        List<User> users = userRepository.findAll();

        List<String> redUsersTokens = new ArrayList<>();
        List<String> orangeUsersTokens = new ArrayList<>();
        List<String> greenUsersTokens = new ArrayList<>();

        for (User user : users) {

            if (user.getFirebaseToken().equals("")) {
                continue;
            }

            double distance = distanceService.get(user.getLocation(), disaster.getDisasterLocation());

            if (distance <= disaster.getRedRadius()) {
                redUsersTokens.add(user.getFirebaseToken());
            } else if (distance <= disaster.getYellowRadius()) {
                orangeUsersTokens.add(user.getFirebaseToken());
            } else if (distance <= disaster.getGreenRadius()) {
                greenUsersTokens.add(user.getFirebaseToken());
            }

        }


        fcmService
                .sentToMultiple(
                        generateFCMBody(
                                "type_a",
                                "We received a disaster report near you and you are in the RED zone.",
                                "Emergency" + " - " + disaster.getDisasterType() + " - " + "RED"
                        ), redUsersTokens
                );

        fcmService
                .sentToMultiple(
                        generateFCMBody(
                                "type_a",
                                "We received a disaster report near you and you are in the ORANGE zone.",
                                "Warning" + " - " + disaster.getDisasterType() + " - " + "ORANGE"
                        ), orangeUsersTokens
                );

        fcmService
                .sentToMultiple(
                        generateFCMBody(
                                "type_a",
                                "We received a disaster report near you and you are in the GREEN zone.",
                                "Notice" + " - " + disaster.getDisasterType() + " - " + "GREEN"
                        ), greenUsersTokens
                );


    }

    private JSONObject generateFCMBody(String type, String body, String title) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        JSONObject jsonData = new JSONObject();

        jsonBody.put("collapse_key", type);
        jsonData.put("body", body);
        jsonData.put("title", title);

        jsonBody.put("notification", jsonData);

        return jsonBody;
    }

    private FirebaseModel getFirebaseTokenFrom(User user) {
        FirebaseModel firebaseModel = new FirebaseModel();

        firebaseModel.setFirebaseToken(user.getFirebaseToken());

        return firebaseModel;
    }

    private User getUserByIdOrElseThrow(Long id, EntityNotFoundException e) throws EntityNotFoundException {
        Optional<User> byId = userRepository.findById(id);

        if (!byId.isPresent()) {
            throw e;
        }

        return byId.get();

    }

    private void userNotExistsOrElseThrow(User user, EntityExistsException e) throws EntityExistsException {
        userNotExistsByUsernameOrElseThrow(user.getUsername(), e);
        userNotExistsByEMailOrElseThrow(user.geteMail(), e);
    }

    private void userNotExistsByUsernameOrElseThrow(String username, EntityExistsException e) throws EntityExistsException {
        if (userRepository.existsByUsername(username)) {
            throw e;
        }
    }

    private void userNotExistsByEMailOrElseThrow(String eMail, EntityExistsException e) throws EntityExistsException {
        if (userRepository.existsByeMail(eMail)) {
            throw e;
        }
    }

    private User checkIfUserAuthenticatedAndAuthorizedAndGet(Long id, String accessToken) throws EntityNotFoundException, ForbiddenException, UnauthorizedException, NoSuchAlgorithmException {
        User dbUser = getUserByIdOrElseThrow(id, new EntityNotFoundException());
        User user = authService.getUser(accessToken);
        checkIfIsTheSameOrAdminElseThrow(dbUser, user, new ForbiddenException());
        return user;
    }

    private void checkIfUserAuthenticatedAndAuthorized(Long id, String accessToken) throws EntityNotFoundException, UnauthorizedException, NoSuchAlgorithmException, ForbiddenException {
        checkIfUserAuthenticatedAndAuthorizedAndGet(id, accessToken);
    }

    private void checkIfIsTheSameOrAdminElseThrow(User dbUser, User user, ForbiddenException e) throws ForbiddenException {
        if (!dbUser.equals(user) && isAdmin(user)) {
            throw e;
        }
    }
}
