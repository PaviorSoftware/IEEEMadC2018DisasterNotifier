package gr.teicm.ieee.madc.disasternotifierservice.service.commons;

import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;

public interface DistanceService {

    double get(Location pointA, Location pointB);

}
