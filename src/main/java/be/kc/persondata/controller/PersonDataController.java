package be.kc.persondata.controller;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.service.PersonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonDataController {

    @Autowired
    PersonDataService personDataService;

    @GetMapping("/load")
    public List<PersonData> load() throws Exception {
        return personDataService.uploadPersonDataFromScratch();
    }
}