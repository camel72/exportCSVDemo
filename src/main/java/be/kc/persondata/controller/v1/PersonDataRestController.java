package be.kc.persondata.controller.v1;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.service.PersonDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persondata/")
public class PersonDataRestController {

    @Value("${export.file.name}")
    private String fileName;

    private PersonDataService personDataService;


    public PersonDataRestController(PersonDataService personDataService) {
        this.personDataService = personDataService;
    }


    // TODO better way than use GetMapping?
    @GetMapping("load")
    public HttpStatus load() throws Exception {
        personDataService.loadFile(fileName);
        return HttpStatus.OK;
    }

    @GetMapping("lastname/{lastName}")
    public ResponseEntity<List<PersonDataDTO>> findbyLastName(@PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(personDataService.findByLastName(lastName), HttpStatus.OK);
    }
}