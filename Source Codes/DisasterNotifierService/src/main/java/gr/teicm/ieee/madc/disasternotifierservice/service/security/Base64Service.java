package gr.teicm.ieee.madc.disasternotifierservice.service.security;

public interface Base64Service {
    String encode(String decodedData);

    String decode(String encodedData);
}
