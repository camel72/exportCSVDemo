package be.kc.persondata;

import be.kc.persondata.service.PersonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class PersondataApplication {

    Logger logger = LoggerFactory.getLogger(PersondataApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PersondataApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PersonDataService personDataService) throws Exception {
        Instant start = Instant.now();
        logger.info("*******populating Database, hold on this may take a while!******");
        personDataService.uploadPersonDataFromScratch();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMinutes();
        logger.info(String.format("loading file 2 DB took %s minutes", timeElapsed));
        // TODO remove
//        return args -> personDataService.uploadPersonDataFromScratch().stream().forEach(
//                personData ->
//                        logger.info(String.format("PersonData : %s", personData.toString()))
//        );
        return null;
    }
}