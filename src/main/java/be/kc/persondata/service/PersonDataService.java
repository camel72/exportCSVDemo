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

    public void uploadPersonData(List<PersonData> personDataList) {
        personDataRepository.saveAll(personDataList);
    }

    public void uploadPersonDataFromScratch() throws Exception {
        List<PersonData> personDatas = personDataCSVRepository.getPersonDatas();
        uploadPersonData(personDatas);
    }
}