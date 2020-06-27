package be.kc.persondata.controller.v1.mapper;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.model.PersonData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonDataMapperTest {
    PersonDataMapper personDataMapper = PersonDataMapper.INSTANCE;

    @Test
    public void personDataMapperTest() {
        //given
        PersonData personData = new PersonData();
        personData.setLastName("testLastName");
        personData.setFirstName("testFirstName");

        //when
        PersonDataDTO personDataDTO = personDataMapper.personDataToPersonDataDTO(personData);

        //then
        assertEquals(personData.getLastName(), personDataDTO.getLastName());
        assertEquals(personData.getFirstName(), personDataDTO.getFirstName());
    }
}