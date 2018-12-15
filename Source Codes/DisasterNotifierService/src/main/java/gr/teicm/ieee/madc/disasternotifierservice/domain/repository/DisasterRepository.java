package gr.teicm.ieee.madc.disasternotifierservice.domain.repository;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;

import java.util.List;

public interface DisasterRepository extends BaseRepository<Disaster> {
    List<Disaster> findAllByCreator(User creator);
}
