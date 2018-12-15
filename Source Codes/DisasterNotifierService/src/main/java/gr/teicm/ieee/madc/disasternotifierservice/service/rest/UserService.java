package gr.teicm.ieee.madc.disasternotifierservice.service.rest;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.model.FirebaseModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.RegisterModel;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService extends GenericService<User, Long> {
    User addRole(User user, Role role);

    User addRoles(User user, List<Role> roles);

    User fromRegisterModel(RegisterModel user);

    User getWithUsernameAndPassword(String username, String password) throws EntityNotFoundException, NoSuchAlgorithmException, UnauthorizedException;

    boolean isAdmin(User user);

    FirebaseModel updateFirebaseToken(String authorization, FirebaseModel firebaseModel) throws UnauthorizedException, NoSuchAlgorithmException;

    FirebaseModel getFirebaseToken(String authorization) throws UnauthorizedException, NoSuchAlgorithmException;

    Location getLocation(String authorization) throws UnauthorizedException, NoSuchAlgorithmException;

    Location updateLocation(String authorization, Location location) throws UnauthorizedException, NoSuchAlgorithmException;

    void notify(Disaster disaster) throws JSONException;
}
