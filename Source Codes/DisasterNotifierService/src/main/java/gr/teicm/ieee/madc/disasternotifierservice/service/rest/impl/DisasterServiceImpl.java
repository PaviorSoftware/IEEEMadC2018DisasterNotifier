package gr.teicm.ieee.madc.disasternotifierservice.service.rest.impl;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Disaster;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.domain.repository.DisasterRepository;
import gr.teicm.ieee.madc.disasternotifierservice.model.DisasterRange;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.DisasterRadiusService;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.DistanceService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.DisasterService;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.UserService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.AuthService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisasterServiceImpl implements DisasterService {

    private final DisasterRepository disasterRepository;
    private final AuthService authService;
    private final UserService userService;
    private final DistanceService distanceService;
    private final DisasterRadiusService disasterRadiusService;

    public DisasterServiceImpl(DisasterRepository disasterRepository, AuthService authService, UserService userService, DistanceService distanceService, DisasterRadiusService disasterRadiusService) {
        this.disasterRepository = disasterRepository;
        this.authService = authService;
        this.userService = userService;
        this.distanceService = distanceService;
        this.disasterRadiusService = disasterRadiusService;
    }

    @Override
    public List<Disaster> get(String authorization) {
        List<Disaster> all = disasterRepository.findAll();
        List<Disaster> activeAll = new ArrayList<>();

        for (Disaster disaster : all) {
            if (disaster.isActive()) {
                activeAll.add(disaster);
            }
        }

        return activeAll;

    }

    @Override
    public List<Disaster> get(User user) {
        return disasterRepository.findAllByCreator(user);
    }

    @Override
    public List<Disaster> near(User user, Long wantedDistance) {

        List<Disaster> all = disasterRepository.findAll();
        List<Disaster> near = new ArrayList<>();

        for (Disaster disaster : all) {
            if (distanceService.get(disaster.getDisasterLocation(), user.getLocation()) < wantedDistance) {
                near.add(disaster);
            }
        }

        return near;
    }

    @Override
    public Disaster get(Long id, String authorization) throws EntityNotFoundException {
        return getDisasterByIdOrElseThrow(id, new EntityNotFoundException());
    }

    @Override
    public Disaster post(Disaster disaster, String authorization) throws UnauthorizedException, NoSuchAlgorithmException {
        User user = authService.getUser(authorization);

        if (disaster.getSafeLocation() == null) {
            Location safeLocation = new Location();
            safeLocation.setLatitude((float) 0);
            safeLocation.setLongitude((float) 0);
            disaster.setSafeLocation(safeLocation);
        }

        calculateAndSetRadius(disaster);

        disaster.setCreator(user);
        disaster.setActive(true);

        return disasterRepository.save(disaster);
    }

    @Override
    public Disaster put(Long id, Disaster disaster, String authorization) throws UnauthorizedException, NoSuchAlgorithmException, EntityNotFoundException, ForbiddenException {
        checkIfDisasterExistsAndAuthorizedAndHasRights(id, authorization);
        disaster.setId((id));

        calculateAndSetRadius(disaster);

        return disasterRepository.save(disaster);
    }

    private void calculateAndSetRadius(Disaster disaster) {
        DisasterRange calculatedRadiusSet = disasterRadiusService.calculate(disaster.getRedRadius());

        disaster.setRedRadius(calculatedRadiusSet.getRed());
        disaster.setYellowRadius(calculatedRadiusSet.getOrange());
        disaster.setGreenRadius(calculatedRadiusSet.getGreen());
    }

    @Override
    public void delete(Long id, String authorization) throws EntityNotFoundException, UnauthorizedException, NoSuchAlgorithmException, ForbiddenException {
        checkIfDisasterExistsAndAuthorizedAndHasRights(id, authorization);
        disasterRepository.deleteById(id);
    }

    private void checkIfDisasterExistsAndAuthorizedAndHasRights(Long id, String accessToken) throws EntityNotFoundException, UnauthorizedException, NoSuchAlgorithmException, ForbiddenException {
        Disaster dbDisaster = getDisasterByIdOrElseThrow(id, new EntityNotFoundException());
        User user = authService.getUser(accessToken);
        checkIfIsCreatorOrAdminElseThrow(dbDisaster, user, new ForbiddenException());
    }

    private void checkIfIsCreatorOrAdminElseThrow(Disaster dbDisaster, User user, ForbiddenException e) throws ForbiddenException {
        if (!dbDisaster.getCreator().equals(user) && userService.isAdmin(user)) {
            throw e;
        }
    }

    private Disaster getDisasterByIdOrElseThrow(Long id, EntityNotFoundException e) throws EntityNotFoundException {
        Optional<Disaster> byId = disasterRepository.findById(id);

        if (!byId.isPresent()) {
            throw e;
        }

        return byId.get();
    }


}
