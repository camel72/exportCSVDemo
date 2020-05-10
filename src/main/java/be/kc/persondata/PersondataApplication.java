package be.kc.persondata;

import be.kc.persondata.service.PersonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersondataApplication {

    Logger logger = LoggerFactory.getLogger(PersondataApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PersondataApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PersonDataService personDataService) throws Exception {
        return args -> personDataService.uploadPersonDataFromScratch().stream().forEach(
                personData ->
                        logger.info(String.format("PersonData : %s", personData.toString()))
        );
    }
}