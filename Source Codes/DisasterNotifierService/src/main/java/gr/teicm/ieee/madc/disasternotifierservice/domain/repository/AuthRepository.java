package gr.teicm.ieee.madc.disasternotifierservice.domain.repository;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Auth;

import java.util.Optional;

public interface AuthRepository extends BaseRepository<Auth> {
    Optional<Auth> findByAccessToken(String accessToken);
}
