package be.kc.persondata.controller.v1;

import be.kc.persondata.dao.UserRepository;
import be.kc.persondata.model.User;
import be.kc.persondata.service.FileStorageService;
import be.kc.persondata.service.PersonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Path;

@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Value("${welcome.message}")
    private String welcomeMessage;

    @Value("${export.file.name}")
    private String path;

    @Value("${env}")
    private String env;

    @Value("${spring.servlet.multipart.location}")
    private String UPLOAD_DIR;

    private PersonDataService personDataService;

    private FileStorageService fileStorageService;

    private UserRepository userRepository;


    public IndexController(PersonDataService personDataService, FileStorageService fileStorageService, UserRepository userRepository) {
        this.personDataService = personDataService;
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
    }


    @GetMapping({"/", "index", "index.html"})
    public String index(Model model) throws Exception {
        if (env.equals("test")) {
            LOGGER.info("**********IndexController: loading file from test env *********");

            personDataService.deleteAll();
            userRepository.deleteAll();

            personDataService.loadFile(new File(path));

            User user = createValidUser();

            userRepository.save(user);
        }
        LOGGER.info("**********IndexController:" + welcomeMessage + "*********");
        model.addAttribute("message", welcomeMessage);

        return "index";
    }

    @GetMapping("/delete")
    public String delete(RedirectAttributes attributes) {
        LOGGER.info("**********IndexController: delete*********");

        personDataService.deleteAll();
        attributes.addFlashAttribute("deleteMessage", "You successfully deleted the database!");

        return "redirect:index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws Exception {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("uploadMessage", "Please select a file to upload.");
            return "redirect:/";
        }
        Path path = fileStorageService.storeFile(file);
        File uploadedFile = path.toFile();

        personDataService.deleteAll();
        personDataService.loadFile(uploadedFile);

        // return success response
        attributes.addFlashAttribute("uploadMessage",
                String.format("You successfully uploaded file %s !", uploadedFile.getName()));

        return "redirect:/";
    }

    private static User createValidUser() {
        User user = new User();
        user.setName("Kristof Camelbeke");
        user.setEmail("kristof.camelbeke@gmail.com");
        return user;
    }
}