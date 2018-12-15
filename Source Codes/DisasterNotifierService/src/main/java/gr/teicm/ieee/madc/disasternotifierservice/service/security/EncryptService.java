package gr.teicm.ieee.madc.disasternotifierservice.service.security;

public interface EncryptService {
    String encrypt(String data);

    String decrypt(String data);
}
