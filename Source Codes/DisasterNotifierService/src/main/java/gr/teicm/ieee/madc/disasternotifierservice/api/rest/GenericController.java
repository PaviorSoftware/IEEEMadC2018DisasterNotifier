package gr.teicm.ieee.madc.disasternotifierservice.api.rest;

import gr.teicm.ieee.madc.disasternotifierservice.config.ApplicationConfiguration;
import gr.teicm.ieee.madc.disasternotifierservice.dto.entity.BaseEntityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface GenericController<T extends BaseEntityDTO> {

    @GetMapping
    ResponseEntity<?> get(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Long id, @RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @PostMapping
    ResponseEntity<?> post(@RequestBody T entity, @RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @RequestBody T entity, @RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

}
