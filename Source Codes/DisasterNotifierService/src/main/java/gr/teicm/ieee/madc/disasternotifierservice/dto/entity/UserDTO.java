package gr.teicm.ieee.madc.disasternotifierservice.dto.entity;

import gr.teicm.ieee.madc.disasternotifierservice.dto.Reference;
import gr.teicm.ieee.madc.disasternotifierservice.dto.embeddable.LocationDTO;

import java.util.Set;

public class UserDTO extends BaseEntityDTO {

    private String username;

    private String eMail;

    private String password;

    private String firebaseToken;

    private LocationDTO location;

    private Set<Reference> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public Set<Reference> getRoles() {
        return roles;
    }

    public void setRoles(Set<Reference> roles) {
        this.roles = roles;
    }
}