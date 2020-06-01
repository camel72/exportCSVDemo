package be.kc.persondata.controller.v1;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.service.PersonDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persondata/")
public class PersonDataController {
    private PersonDataService personDataService;

    public PersonDataController(PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    // TODO better way than use GetMapping?
    @GetMapping("load")
    public HttpStatus load() throws Exception {
        personDataService.loadFile();
        return HttpStatus.OK;
    }

    @GetMapping("lastname/{lastName}")
    public ResponseEntity<List<PersonData>> findbyLastName(@PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(personDataService.findByLastName(lastName), HttpStatus.OK);
    }
}