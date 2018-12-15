package gr.teicm.ieee.madc.disasternotifierservice.api.rpc;

import gr.teicm.ieee.madc.disasternotifierservice.model.LoginModel;
import gr.teicm.ieee.madc.disasternotifierservice.model.RegisterModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {
    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody LoginModel loginModel);

    @PostMapping("register")
    ResponseEntity<?> register(@RequestBody RegisterModel registerModel);
}
