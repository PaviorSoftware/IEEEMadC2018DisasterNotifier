package gr.teicm.ieee.madc.disasternotifierservice.service.rest;

import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;

import java.util.List;

public interface DisasterService extends GenericService<Disaster, Long> {
    List<Disaster> get(User user);

    List<Disaster> near(User user, Long wantedDistance);
}
