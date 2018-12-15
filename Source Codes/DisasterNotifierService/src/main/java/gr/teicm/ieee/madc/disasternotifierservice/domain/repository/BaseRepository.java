package gr.teicm.ieee.madc.disasternotifierservice.domain.repository;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
