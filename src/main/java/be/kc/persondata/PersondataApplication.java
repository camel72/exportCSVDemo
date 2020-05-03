package be.kc.persondata;

import be.kc.persondata.service.PersonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersondataApplication {


    public static void main(String[] args) {
        SpringApplication.run(PersondataApplication.class, args);
    }
}
