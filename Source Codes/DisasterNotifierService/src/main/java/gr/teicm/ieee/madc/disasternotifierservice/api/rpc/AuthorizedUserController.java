package gr.teicm.ieee.madc.disasternotifierservice.api.rpc;

import gr.teicm.ieee.madc.disasternotifierservice.config.ApplicationConfiguration;
import gr.teicm.ieee.madc.disasternotifierservice.dto.embeddable.LocationDTO;
import gr.teicm.ieee.madc.disasternotifierservice.model.FirebaseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AuthorizedUserController {

    @GetMapping
    ResponseEntity<?> getMe(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @GetMapping("reports")
    ResponseEntity<?> myReports(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @GetMapping("firebase")
    ResponseEntity<?> getMyFirebaseToken(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @PutMapping("firebase")
    ResponseEntity<?> updateMyFirebaseToken(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization, @RequestBody FirebaseModel firebaseModel);

    @GetMapping("location")
    ResponseEntity<?> getMyLocation(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization);

    @PutMapping("location")
    ResponseEntity<?> updateMyLocation(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization, @RequestBody LocationDTO location);

    @GetMapping("near/{distance}")
    ResponseEntity<?> getNearDisasters(@RequestHeader(defaultValue = ApplicationConfiguration.GuestToken) String authorization, @PathVariable Long distance);
}
