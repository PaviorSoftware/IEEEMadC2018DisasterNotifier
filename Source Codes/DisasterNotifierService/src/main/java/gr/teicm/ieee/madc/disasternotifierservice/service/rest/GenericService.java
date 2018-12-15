package gr.teicm.ieee.madc.disasternotifierservice.service.rest;

import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityExistsException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.EntityNotFoundException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.ForbiddenException;
import gr.teicm.ieee.madc.disasternotifierservice.commons.exception.UnauthorizedException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface GenericService<ENTITY, ID> {
    List<ENTITY> get(String authorization);

    ENTITY get(ID id, String authorization) throws EntityNotFoundException;

    ENTITY post(ENTITY entity, String authorization) throws EntityExistsException, NoSuchAlgorithmException, UnauthorizedException, ForbiddenException;

    ENTITY put(ID id, ENTITY entity, String authorization) throws UnauthorizedException, NoSuchAlgorithmException, EntityNotFoundException, ForbiddenException, EntityExistsException;

    void delete(ID id, String authorization) throws EntityNotFoundException, UnauthorizedException, NoSuchAlgorithmException, ForbiddenException;
}
