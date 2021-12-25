package be.kc.persondata.service;

import be.kc.persondata.controller.v1.mapper.PersonDataMapper;
import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.dao.PersonDataCSVRepository;
import be.kc.persondata.dao.PersonDataRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
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
                .sorted(getDefaultPersonDataDTOComparator())
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
                .sorted(getDefaultPersonDataDTOComparator())
                .collect(Collectors.toList());
    }

    public List<PersonDataDTO> findByLastNameAndFirstName(String lastName, String firstName) {
        return personDataRepository.findByLastNameAndFirstName(lastName, firstName)
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .sorted(getDefaultPersonDataDTOComparator())
                .collect(Collectors.toList());
    }

    public List<PersonDataDTO> findByStreetAndCity(String street, String city) {
        return personDataRepository.findByStreetAndCity(street, city)
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .sorted(comparing(PersonDataDTO::getNumber)
                        .thenComparing(PersonDataDTO::getLastName)
                        .thenComparing(PersonDataDTO::getFirstName))
                .collect(Collectors.toList());
    }


    public List<PersonDataDTO> findByStreetAndNumberAndCity(String street, String number, String city) {
        return personDataRepository.findByStreetAndNumberAndCity(street, number, city)
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .sorted(getStreetAndNumberPersonDataDTOComparator())
                .collect(Collectors.toList());
    }

    public List<PersonDataDTO> findByStreet(String street) {
        return personDataRepository.findByStreet(street)
                .stream()
                .parallel()
                .map(personData -> personDataMapper.personDataToPersonDataDTO(personData))
                .sorted(getStreetAndNumberPersonDataDTOComparator())
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        personDataRepository.deleteAllInBatch();
    }


    private Comparator<PersonDataDTO> getDefaultPersonDataDTOComparator() {
        return comparing(PersonDataDTO::getLastName)
                .thenComparing(PersonDataDTO::getFirstName)
                .thenComparing(PersonDataDTO::getCity)
                .thenComparing(PersonDataDTO::getStreet)
                .thenComparing(PersonDataDTO::getNumber)
                .thenComparing(PersonDataDTO::getBirthDate);
    }

    private Comparator<PersonDataDTO> getStreetAndNumberPersonDataDTOComparator() {
        return comparing(PersonDataDTO::getCity)
                .thenComparing(PersonDataDTO::getStreet)
                .thenComparing(PersonDataDTO::getNumber)
                .thenComparing(PersonDataDTO::getLastName)
                .thenComparing(PersonDataDTO::getFirstName);
    }
}