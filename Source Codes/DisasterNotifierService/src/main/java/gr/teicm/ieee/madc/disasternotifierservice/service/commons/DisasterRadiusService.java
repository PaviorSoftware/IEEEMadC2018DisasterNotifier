package gr.teicm.ieee.madc.disasternotifierservice.service.commons;

import gr.teicm.ieee.madc.disasternotifierservice.model.DisasterRange;

public interface DisasterRadiusService {

    DisasterRange calculate(Long base);

}
