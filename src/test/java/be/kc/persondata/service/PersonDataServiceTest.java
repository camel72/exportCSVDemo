package be.kc.persondata.service;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PersonDataServiceTest {

    @Autowired
    private PersonDataService personDataService;


    @BeforeEach
    public void uploadPersonDataTest() throws Exception{
        personDataService.uploadPersonDataFromScratch();
    }

    @Test
    public void getPersonDataTest() {
        List<PersonData> personDatas = personDataService.retrievePersonData();
        PersonData personData = personDatas.get(0);
        assertNotNull(personData);
        assertEquals(getPersonData().getLastName(), personData.getLastName());
    }

    private PersonData getPersonData() {
        return new PersonDataBuilder()
                .setLastName("testLastName")
                .setFirstName("testFirstName")
                .setBirthDate(LocalDate.of(2000, 1, 1))
                .setStreet("testAddress")
                .setNumber("1")
                .setSsin("12345678901")
                .setCity("testCity")
                .setAffiliation("180")
                .createPersonData();
    }
}