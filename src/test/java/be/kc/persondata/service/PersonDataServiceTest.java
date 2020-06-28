package be.kc.persondata.service;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
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
    public void uploadPersonDataTest() throws Exception {
        personDataService.loadFile();
    }

    @Test
    public void getPersonDataTest() {
        List<PersonDataDTO> personDatas = personDataService.retrievePersonData();
        PersonDataDTO personDataDTO = personDatas.get(0);
        assertNotNull(personDataDTO);
        assertEquals(getPersonDataDto().getLastName(), personDataDTO.getLastName());
    }

    @Test
    public void findByLastNameTest() {
        assertEquals(getPersonDataDto().getLastName(), personDataService.findByLastName("testLastName").get(0).getLastName());
    }

    @Test
    public void findByLastAndFirstNameTest() {
        PersonDataDTO personData = personDataService.findByLastNameAndFirstName("testLastName", "testFirstName").get(0);
        PersonDataDTO personDataDTOExpected = getPersonDataDto();
        assertEquals(personDataDTOExpected.getLastName(), personData.getLastName());
        assertEquals(personDataDTOExpected.getFirstName(), personData.getFirstName());
    }

    private PersonDataDTO getPersonDataDto() {
        return PersonDataDTO.builder()
                .lastName("testLastName")
                .firstName("testFirstName")
                .birthDate(LocalDate.of(2000, 1, 1))
                .street("testAddress")
                .number("1")
                .ssin("12345678901")
                .city("testCity")
                .affiliation("180")
                .build();
    }
}