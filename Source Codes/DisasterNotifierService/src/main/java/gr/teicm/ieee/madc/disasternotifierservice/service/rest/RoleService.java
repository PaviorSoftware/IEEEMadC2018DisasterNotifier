package gr.teicm.ieee.madc.disasternotifierservice.service.rest;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;

public interface RoleService extends GenericService<Role, Long> {
    Role get(String name, String authorization);

    boolean userHaveThis(User user, Role role);
}
