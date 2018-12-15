package gr.teicm.ieee.madc.disasternotifierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisasterNotifierServiceApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(DisasterNotifierServiceApplication.class);

        springApplication.run(args);
    }

}
