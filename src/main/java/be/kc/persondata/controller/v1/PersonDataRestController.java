package be.kc.persondata.controller.v1;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.service.FileStorageService;
import be.kc.persondata.service.PersonDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persondata/")
public class PersonDataRestController {

    private PersonDataService personDataService;

    private FileStorageService fileStorageService;

    public PersonDataRestController(PersonDataService personDataService, FileStorageService fileStorageService) {
        this.personDataService = personDataService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("load")
    public ResponseEntity<String> load(@RequestParam("file") MultipartFile file) throws Exception {
        Path path = fileStorageService.storeFile(file);
        File uploadedFile = path.toFile();
        personDataService.loadFile(uploadedFile);

        return new ResponseEntity<>(uploadedFile.getName(), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public void deleteDatabase() {
        personDataService.deleteAll();
    }

    @GetMapping("lastname/{lastName}")
    public ResponseEntity<List<PersonDataDTO>> findbyLastName(@PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(personDataService.findByLastName(lastName), HttpStatus.OK);
    }
}