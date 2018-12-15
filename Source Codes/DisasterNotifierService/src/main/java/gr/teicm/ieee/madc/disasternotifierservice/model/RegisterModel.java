package gr.teicm.ieee.madc.disasternotifierservice.model;

import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class RegisterModel extends User {
    public RegisterModel() {
    }

    public RegisterModel(@NotNull @Size(
            min = 4,
            max = 15
    ) String username, @NotNull @Size(
            min = 8,
            max = 50
    ) String eMail, @NotNull String password, @NotNull String firebaseToken, @NotNull Location location, Set<Role> roles) {
        super(username, eMail, password, firebaseToken, location, roles);
    }
}
