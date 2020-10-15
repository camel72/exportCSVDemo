package be.kc.persondata.controller.v1;

import be.kc.persondata.service.PersonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Value("${welcome.message}")
    private String welcomMessage;

    @Value("${export.file.name}")
    private String fileName;

    @Value("${env}")
    private String env;

    @Autowired
    private PersonDataService service;


    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) throws Exception {
        if (env.equals("test")) {
            LOGGER.info("**********IndexController: loading file *********");
            service.loadFile(fileName);
        }
        LOGGER.info("**********IndexController:" + welcomMessage + "*********");
        model.addAttribute("message", welcomMessage);

        return "index";
    }
}