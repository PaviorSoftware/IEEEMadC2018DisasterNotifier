package gr.teicm.ieee.madc.disasternotifierservice.service.security;

import java.security.NoSuchAlgorithmException;

public interface HashingService {
    String hash(String data) throws NoSuchAlgorithmException;
}
