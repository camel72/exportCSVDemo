package be.kc.persondata.controller.v1;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import be.kc.persondata.service.PersonDataService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchFormController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchFormController.class);

    @Value("${welcome.message}")
    private String welcomMessage;

    @Autowired
    private PersonDataService service;


    @RequestMapping("/searchForm")
    public String searchForm(Model model) {
        LOGGER.info("**********in searchForm *********");
        model.addAttribute("message", welcomMessage);
        model.addAttribute("personData", new PersonDataBuilder().createPersonData());

        return "searchForm";
    }

    @PostMapping("/search")
    public String searchPersonData(@ModelAttribute PersonData personData, Model model) {
        LOGGER.info("**********searchFrom: searchPersonData *********");
        List<PersonDataDTO> personDataDTOList = null;
        if (StringUtils.isEmpty(personData.getLastName()) && StringUtils.isEmpty(personData.getFirstName())) {
            personDataDTOList = service.findAllSortedByLastNameFirstNameAndCity();
        } else if (StringUtils.isNotBlank(personData.getLastName()) && StringUtils.isEmpty(personData.getFirstName())) {
            personDataDTOList = service.findByLastName(personData.getLastName());
        } else {
            personDataDTOList = service.findByLastNameAndFirstName(personData.getLastName(), personData.getFirstName());
        }

        if (personDataDTOList != null) {
            model.addAttribute(personDataDTOList);
            personDataDTOList.stream().forEach(personDataDTO -> LOGGER.info(String.format("record :[%s]", personDataDTO.toString())));
        } else {
            LOGGER.info("empty resultset");
        }
        return "searchResult";
    }
}