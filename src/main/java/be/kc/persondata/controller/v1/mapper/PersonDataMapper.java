package be.kc.persondata.controller.v1.mapper;


import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.model.PersonData;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.ERROR
)

public interface PersonDataMapper {

    PersonDataMapper INSTANCE = Mappers.getMapper(PersonDataMapper.class);

    PersonDataDTO personDataToPersonDataDTO(PersonData personData);
}
