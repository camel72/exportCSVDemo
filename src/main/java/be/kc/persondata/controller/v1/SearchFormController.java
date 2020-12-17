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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.function.Predicate;

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

    @RequestMapping("/search")
    public String searchPersonData(@ModelAttribute PersonData personData, Model model) {
        LOGGER.info("**********searchFrom: searchPersonData *********");
        List<PersonDataDTO> personDataDTOList = null;
        if (isFindAll().test(personData)) {
            personDataDTOList = service.findAllSortedByLastNameFirstNameAndCity();
        } else if (isFindByLastName().test(personData)) {
            personDataDTOList = service.findByLastName(personData.getLastName());
        } else if (isFindByLastAndFirstName().test(personData)) {
            personDataDTOList = service.findByLastNameAndFirstName(personData.getLastName(), personData.getFirstName());
        } else if (isFindByStreetAndNumber().test(personData)) {
            personDataDTOList = service.findByStreetAndNumberAndCity(personData.getStreet(), personData.getNumber(), personData.getCity());
        }

        if (personDataDTOList != null) {
            model.addAttribute(personDataDTOList);
            personDataDTOList.stream()
                    .parallel()
                    .forEach(
                            personDataDTO ->
                                    LOGGER.info(String.format("record :[%s]", personDataDTO.toString()))
                    );
        } else {
            LOGGER.info("empty resultset");
        }
        return "searchResult";
    }


    private Predicate<PersonData> isFindAll() {
        return personData -> StringUtils.isEmpty(personData.getLastName())
                && StringUtils.isEmpty(personData.getFirstName())
                && StringUtils.isEmpty(personData.getStreet())
                && StringUtils.isEmpty(personData.getNumber());
    }

    private Predicate<PersonData> isFindByLastName() {
        return personData -> StringUtils.isNotBlank(personData.getLastName())
                && StringUtils.isEmpty(personData.getFirstName());
    }


    private Predicate<PersonData> isFindByLastAndFirstName() {
        return personData -> StringUtils.isNotBlank(personData.getLastName())
                && StringUtils.isNotBlank(personData.getFirstName());
    }

    private Predicate<PersonData> isFindByStreetAndNumber() {
        return personData -> StringUtils.isNotBlank(personData.getStreet())
                && StringUtils.isNotBlank(personData.getNumber());
    }
}