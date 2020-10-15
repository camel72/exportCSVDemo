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

    private static final String FILE_NAME = "export.csv";

    @Autowired
    private PersonDataService personDataService;


    @BeforeEach
    public void uploadPersonDataTest() throws Exception {
        personDataService.loadFile(FILE_NAME);
    }

    @Test
    public void findAllSortedByLastNameFirstNameAndCityTest() {
        List<PersonDataDTO> personDatas = personDataService.findAll();
        PersonDataDTO personDataDTO = personDatas.get(0);
        assertNotNull(personDataDTO);
        assertEquals(getPersonDataDto().getLastName(), personDataDTO.getLastName());
        assertEquals(getPersonDataDto().getSsin(), personDataDTO.getSsin());
        assertEquals("11111111111", personDatas.get(1).getSsin());
        assertEquals("testCity3", personDatas.get(2).getCity());
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