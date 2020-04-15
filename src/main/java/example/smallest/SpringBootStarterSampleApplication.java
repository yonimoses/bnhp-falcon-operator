package example.smallest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarterSampleApplication {
    private final static Logger log = LoggerFactory.getLogger(SpringBootStarterSampleApplication.class);

    public static void main(String[] args) {
        log.info("Starting boot application");
        SpringApplication.run(SpringBootStarterSampleApplication.class, args);
    }

}
