package gr.teicm.ieee.madc.disasternotifierservice.domain.repository;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findByName(String name);
}
