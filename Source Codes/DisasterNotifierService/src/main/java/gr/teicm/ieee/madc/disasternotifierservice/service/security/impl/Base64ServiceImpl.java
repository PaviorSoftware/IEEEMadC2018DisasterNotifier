package gr.teicm.ieee.madc.disasternotifierservice.service.security.impl;

import gr.teicm.ieee.madc.disasternotifierservice.service.security.Base64Service;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

@Service
public class Base64ServiceImpl implements Base64Service {

    private final Base64.Encoder encoder;
    private final Base64.Decoder decoder;

    public Base64ServiceImpl() {
        encoder = Base64.getEncoder();
        decoder = Base64.getDecoder();
    }

    @Override
    public String encode(String decodedData) {

        byte[] encoded = encoder.encode(decodedData.getBytes());

        return DatatypeConverter.printHexBinary(encoded);
    }

    @Override
    public String decode(String encodedData) {

        byte[] decoded = decoder.decode(encodedData.getBytes());

        return DatatypeConverter.printHexBinary(decoded);

    }
}
