package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = PersonDataCSVRepository.class)
@ExtendWith(SpringExtension.class)
public class PersonDataCSVRepositoryTest {

    @Autowired
    private PersonDataCSVRepository personDataCSVRepository;

    @Test
    @Disabled
    // TODO
    public void getPersonDataByNameTest() throws Exception {
        PersonData personDataExpected = new PersonDataBuilder()
                .setLastName("testLastName")
                .setFirstName("testFirstName")
                .setBirthDate(LocalDate.of(2000, 1, 1))
                .setStreet("testAddress")
                .setNumber("1")
                .setCity("testCity")
                .setAffiliation("180")
                .createPersonData();

        PersonData personData = personDataCSVRepository.getPersonData("testLastName");
        assertNotNull(personData);
        assertEquals(personDataExpected, personData);
    }

    @Test
    public void getPersonDataTest() throws Exception {
        PersonData personDataExpected = new PersonDataBuilder()
                .setLastName("testLastName")
                .setFirstName("testFirstName")
                .setBirthDate(LocalDate.of(2000, 1, 1))
                .setStreet("testAddress")
                .setNumber("1")
                .setSsin("12345678901")
                .setCity("testCity")
                .setAffiliation("180")
                .createPersonData();

        List<PersonData> personDatas = personDataCSVRepository.getPersonDatas();
        assertNotNull(personDatas.get(0));
        assertEquals(personDataExpected, personDatas.get(0));
    }
}