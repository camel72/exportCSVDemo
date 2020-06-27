package be.kc.persondata.controller.v1.mapper;


import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.model.PersonData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonDataMapper {

    PersonDataMapper INSTANCE = Mappers.getMapper(PersonDataMapper.class);

    PersonDataDTO personDataToPersonDataDTO(PersonData personData);
}
