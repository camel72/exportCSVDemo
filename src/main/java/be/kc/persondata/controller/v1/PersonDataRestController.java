package be.kc.persondata.controller.v1;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.service.PersonDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persondata/")
public class PersonDataRestController {

    @Value("${spring.servlet.multipart.location}")
    private String UPLOAD_DIR;

    private PersonDataService personDataService;

    public PersonDataRestController(PersonDataService personDataService) {
        this.personDataService = personDataService;
    }


    @PostMapping("load")
    public ResponseEntity<String> load(@RequestParam("file") MultipartFile file) throws Exception {
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        personDataService.loadFile(path.toFile());
        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }

    @GetMapping("lastname/{lastName}")
    public ResponseEntity<List<PersonDataDTO>> findbyLastName(@PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(personDataService.findByLastName(lastName), HttpStatus.OK);
    }
}