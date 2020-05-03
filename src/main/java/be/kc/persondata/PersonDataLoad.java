package be.kc.persondata;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.service.PersonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDataLoad implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(PersonDataLoad.class);

    @Autowired
    private PersonDataService personDataService;

    @Override
    public void run(String... args) throws Exception {
        List<PersonData> personDataList = personDataService.uploadPersonDataFromScratch();

        personDataList.stream().
                forEach(personData ->
                        logger.info(String.format("PersonData : %s", personData.toString()))
                );
    }
}
