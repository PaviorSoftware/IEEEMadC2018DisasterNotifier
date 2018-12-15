package gr.teicm.ieee.madc.disasternotifierservice.service.security.impl;

import gr.teicm.ieee.madc.disasternotifierservice.service.security.EncryptService;
import org.springframework.stereotype.Service;

@Service
public class EncryptServiceImpl implements EncryptService {
    @Override
    public String encrypt(String data) {
        return data;
    }

    @Override
    public String decrypt(String data) {
        return data;
    }
}
