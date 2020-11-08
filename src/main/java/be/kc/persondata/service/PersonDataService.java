package be.kc.persondata.service;

import be.kc.persondata.controller.v1.mapper.PersonDataMapper;
import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.dao.PersonDataCSVRepository;
import be.kc.persondata.dao.PersonDataRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Data
public class PersonDataService {

    private final PersonDataRepository personDataRepository;
    private final PersonDataCSVRepository personDataCSVRepository;
    private final PersonDataMapper personDataMapper;

    public PersonDataService(PersonDataRepository personDataRepository, PersonDataCSVRepository personDataCSVRepository, PersonDataMapper personDataMapper) {
        this.personDataRepository = personDataRepository;
        this.personDataCSVRepository = personDataCSVRepository;
        this.personDataMapper = personDataMapper;
    }

    public List<PersonDataDTO> findAllSortedByLastNameFirstNameAndCity() {
        return personDataRepository.findAll()
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .sorted(comparing(PersonDataDTO::getLastName)
                        .thenComparing(PersonDataDTO::getFirstName)
                        .thenComparing(PersonDataDTO::getCity))
                .collect(Collectors.toList());
    }

    public void loadFile(File file) throws Exception {
        personDataCSVRepository.uploadFileToDB(file);
    }

    public List<PersonDataDTO> findByLastName(String lastName) {
        return personDataRepository.findByLastName(lastName)
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .collect(Collectors.toList());
    }

    public List<PersonDataDTO> findByLastNameAndFirstName(String lastName, String firstName) {
        return personDataRepository.findByLastNameAndFirstName(lastName, firstName)
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        personDataRepository.deleteAllInBatch();
    }
}