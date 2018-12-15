package gr.teicm.ieee.madc.disasternotifierservice.domain.repository;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByeMail(String eMail);

    boolean existsByUsername(String username);

    boolean existsByeMail(String eMail);

}
