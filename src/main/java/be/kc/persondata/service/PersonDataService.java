package be.kc.persondata.service;

import be.kc.persondata.dao.PersonDataCSVRepository;
import be.kc.persondata.dao.PersonDataRepository;
import be.kc.persondata.model.PersonData;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class PersonDataService {

    private final PersonDataRepository personDataRepository;
    private final PersonDataCSVRepository personDataCSVRepository;

    public PersonDataService(PersonDataRepository personDataRepository, PersonDataCSVRepository personDataCSVRepository) {
        this.personDataRepository = personDataRepository;
        this.personDataCSVRepository = personDataCSVRepository;
    }

    public List<PersonData> retrievePersonData() {
        return personDataRepository.findAll();
    }

    public void loadFile() throws Exception {
        personDataCSVRepository.uploadFileToDB();
    }

    public List<PersonData> findByLastName(String lastName){
        return personDataRepository.findByLastName(lastName);
    }

    public List<PersonData> findByLastNameAndFirstName(String lastName, String firstName){
        return personDataRepository.findByLastNameAndFirstName(lastName, firstName);
    }
}