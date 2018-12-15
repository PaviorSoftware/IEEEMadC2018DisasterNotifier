package gr.teicm.ieee.madc.disasternotifierservice.service.security.impl;

import gr.teicm.ieee.madc.disasternotifierservice.service.security.HashingService;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashingServiceImpl implements HashingService {
    @Override
    public String hash(String data) throws NoSuchAlgorithmException {

        MessageDigest digestService = MessageDigest.getInstance("SHA-256");
        byte[] hashedData = digestService.digest(data.getBytes());

        return DatatypeConverter.printHexBinary(hashedData);
    }
}
