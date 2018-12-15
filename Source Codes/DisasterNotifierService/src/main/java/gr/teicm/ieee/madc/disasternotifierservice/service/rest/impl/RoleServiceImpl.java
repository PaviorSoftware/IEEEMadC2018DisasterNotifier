package gr.teicm.ieee.madc.disasternotifierservice.service.rest.impl;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;
import gr.teicm.ieee.madc.disasternotifierservice.config.ApplicationConfiguration;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.Role;
import gr.teicm.ieee.madc.disasternotifierservice.domain.entity.User;
import gr.teicm.ieee.madc.disasternotifierservice.domain.repository.RoleRepository;
import gr.teicm.ieee.madc.disasternotifierservice.service.rest.RoleService;
import gr.teicm.ieee.madc.disasternotifierservice.service.security.AuthService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AuthService authService;

    public RoleServiceImpl(RoleRepository roleRepository, AuthService authService) {
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @Override
    public List<Role> get(String authorization) {
        return roleRepository.findAll();
    }

    @Override
    public Role get(Long id, String authorization) throws EntityNotFoundException {
        return getRoleByIdOrElseThrow(id, new EntityNotFoundException());
    }

    @Override
    public Role get(String name, String authorization) {
        return getRoleByNameOrElsePost(name);
    }

    @Override
    public boolean userHaveThis(User user, Role role) {
        boolean response = false;

        for (Role tmpRole : user.getRoles()) {
            response = tmpRole.equals(role);
        }

        return response;
    }

    private Role getRoleByNameOrElsePost(String name) {
        Optional<Role> byName = roleRepository.findByName(name);
        Role role;

        if (!byName.isPresent()) {
            role = new Role(name);
            roleRepository.save(role);
        } else {
            role = byName.get();
        }

        return role;

    }

    @Override
    public Role post(Role role, String authorization) throws EntityExistsException, UnauthorizedException, NoSuchAlgorithmException, ForbiddenException {
        checkIfUserIsGuestAndThrow(authorization, new UnauthorizedException());

        User user = authService.getUser(authorization);

        isAdminOrElseThrow(user, new ForbiddenException());

        roleNotExistByNameOrElseThrow(role.getName(), new EntityExistsException());

        return roleRepository.save(role);
    }

    @Override
    public Role put(Long id, Role role, String authorization) throws UnauthorizedException, NoSuchAlgorithmException, ForbiddenException, EntityNotFoundException, EntityExistsException {
        checkIfUserIsGuestAndThrow(authorization, new UnauthorizedException());

        User user = authService.getUser(authorization);
        isAdminOrElseThrow(user, new ForbiddenException());

        getRoleByIdOrElseThrow(id, new EntityNotFoundException());
        Optional<Role> possibleOptionalDuplicate = roleRepository.findByName(role.getName());

        checkWeDontHaveConflictOrElseThrow(
                id,
                possibleOptionalDuplicate,
                new EntityExistsException()
        );

        role.setId(id);

        return roleRepository.save(role);
    }

    private void checkWeDontHaveConflictOrElseThrow(
            Long id,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<Role> possibleOptionalDuplicate,
            EntityExistsException e
    ) throws EntityExistsException {
        if (possibleOptionalDuplicate.isPresent()) {
            Role possibleDuplicate = possibleOptionalDuplicate.get();
            if (!possibleDuplicate.getId().equals(id)) {
                // We have conflict
                throw e;
            }
        }
    }

    @Override
    public void delete(Long id, String authorization) throws UnauthorizedException, NoSuchAlgorithmException, ForbiddenException, EntityNotFoundException {
        checkIfUserIsGuestAndThrow(authorization, new UnauthorizedException());

        User user = authService.getUser(authorization);
        isAdminOrElseThrow(user, new ForbiddenException());

        Role role = getRoleByIdOrElseThrow(id, new EntityNotFoundException());

        roleRepository.delete(role);
    }

    private Role getRoleByIdOrElseThrow(Long id, EntityNotFoundException e) throws EntityNotFoundException {
        Optional<Role> byId = roleRepository.findById(id);

        if (!byId.isPresent()) {
            throw e;
        }

        return byId.get();

    }

    private void roleNotExistByNameOrElseThrow(String name, EntityExistsException e) throws EntityExistsException {
        Optional<Role> byId = roleRepository.findByName(name);

        if (!byId.isPresent()) {
            throw e;
        }
    }

    private void isAdminOrElseThrow(User user, ForbiddenException e) throws ForbiddenException {
        Role adminRole = getRoleByNameOrElsePost(ApplicationConfiguration.WebmasterRole);

        if (!user.getRoles().contains(adminRole)) {
            throw e;
        }
    }

    private void checkIfUserIsGuestAndThrow(String authorization, UnauthorizedException e) throws UnauthorizedException {
        if (authorization.equals(ApplicationConfiguration.GuestToken)) {
            throw e;
        }
    }
}
