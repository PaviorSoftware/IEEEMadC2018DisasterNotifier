package gr.teicm.ieee.madc.disasternotifierservice.domain.entity;

import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    @NotNull
    @Size(
            min = 4,
            max = 15
    )
    private String username;

    @Column(unique = true)
    @NotNull
    @Size(
            min = 8,
            max = 50
    )
    private String eMail;

    @NotNull
    private String password;

    @NotNull
    private String firebaseToken;

    @NotNull
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    private Set<Role> roles;

    public User() {
    }

    public User(@NotNull @Size(
            min = 4,
            max = 15
    ) String username, @NotNull @Size(
            min = 8,
            max = 50
    ) String eMail, @NotNull String password, @NotNull String firebaseToken, @NotNull Location location, Set<Role> roles) {
        this.username = username;
        this.eMail = eMail;
        this.password = password;
        this.firebaseToken = firebaseToken;
        this.location = location;
        this.roles = roles;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User tmp = (User) obj;

        return this.getId().equals(tmp.getId());
    }
}
