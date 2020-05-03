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
        assertNotNull(personData.getId());
        assertEquals(getPersonData().getLastName(), personData.getLastName());
    }

    @Test
    public void findByLastNameTest(){
        assertEquals(getPersonData().getLastName(), personDataService.findByLastName("testLastName").get(0).getLastName());
    }
    @Test
    public void findByLastAndFirstNameTest(){
        PersonData personData = personDataService.findByLastNameAndFirstName("testLastName", "testFirstName").get(0);
        assertEquals(getPersonData().getLastName(), personData.getLastName());
        assertEquals(getPersonData().getFirstName(), personData.getFirstName());
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