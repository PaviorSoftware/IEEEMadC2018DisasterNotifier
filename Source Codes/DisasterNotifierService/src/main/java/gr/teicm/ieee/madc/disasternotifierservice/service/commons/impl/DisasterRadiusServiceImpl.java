package gr.teicm.ieee.madc.disasternotifierservice.service.commons.impl;

import gr.teicm.ieee.madc.disasternotifierservice.model.DisasterRange;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.DisasterRadiusService;
import org.springframework.stereotype.Service;

@Service
public class DisasterRadiusServiceImpl implements DisasterRadiusService {
    @Override
    public DisasterRange calculate(Long base) {

        double red = base * 1.25;
        double orange = red + red * 1.5;
        double green = orange + orange * 1.75;

        return new DisasterRange(
                (long) red,
                (long) orange,
                (long) green
        );
    }
}
