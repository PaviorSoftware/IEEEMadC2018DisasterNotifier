package gr.teicm.ieee.madc.disasternotifierservice.config;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class ApplicationConfiguration {


    // Application info

    public static final String applicationName = "Disaster Notifier Service";
    public static final String applicationDevBrand = "IEEE Student Branch - TEI of Central Macedonia";
    public static final String applicationDevSite = "http://ieee.teicm.gr";
    public static final List<String> applicationDevNames = Arrays.asList(
            "Iordanis Kostelidis",
            "Pavlos Kokozidis"
    );

    // Application Variables

    public static final String protocol = "http://"; // ToDo - Make enum of HTTP and HTTPS
    public static final String host = "localhost";
    public static final String port = String.valueOf(8080);

    // Auth Variables

    public static final Integer tokenDurationInHours = 12;

    // Role Variables

    public static final String WebmasterRole = "SYS_ADMIN";
    public static final String BasicRole = "SYS_USER";


    public static final String GuestToken = "guest";
}
