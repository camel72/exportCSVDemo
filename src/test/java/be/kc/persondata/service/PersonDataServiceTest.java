package be.kc.persondata.service;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonDataServiceTest {

    private static final String PATH = "src/test/resources/export.csv";

    @Autowired
    private PersonDataService personDataService;


    @BeforeEach
    public void uploadPersonDataTest() throws Exception {
        personDataService.loadFile(new File(PATH));
    }

    @Test
    public void findAllSortedByLastNameFirstNameAndCityTest() {
        List<PersonDataDTO> personDatas = personDataService.findAllSortedByLastNameFirstNameAndCity();

        PersonDataDTO personDataDTO = personDatas.get(6);
        assertNotNull(personDataDTO);
        assertEquals(getPersonDataDto().getLastName(), personDataDTO.getLastName());
        assertEquals(getPersonDataDto().getSsin(), personDataDTO.getSsin());
        assertEquals("11111111111", personDatas.get(7).getSsin());
        assertEquals("testCity3", personDatas.get(8).getCity());
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

    @Test
    public void findByStreetNameTest() {
        List<PersonDataDTO> searchList = personDataService.findByStreetName("testStraat");
        assertTrue(searchList.size() == 3);
        PersonDataDTO personData = searchList.get(0);

        PersonDataDTO personDataDTOExpected = getPersonDataDto();
        assertEquals(personDataDTOExpected.getLastName(), personData.getLastName());
        assertEquals(personDataDTOExpected.getFirstName(), personData.getFirstName());
    }


    @Test
    void deleteAll() {
        personDataService.deleteAll();

        assertTrue(personDataService.findAllSortedByLastNameFirstNameAndCity().isEmpty());
    }


    private PersonDataDTO getPersonDataDto() {
        return PersonDataDTO.builder()
                .lastName("testLastName")
                .firstName("testFirstName")
                .birthDate(LocalDate.of(2000, 1, 1))
                .street("testStraat")
                .number("1")
                .ssin("12345678901")
                .city("testCity")
                .affiliation("180")
                .build();
    }
}