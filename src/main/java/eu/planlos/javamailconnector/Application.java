package eu.planlos.javamailconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Profile;

@Profile("java-mail-connector-dev")
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}