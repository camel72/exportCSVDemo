package be.kc.persondata.controller.v1;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.dao.UserRepository;
import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import be.kc.persondata.model.User;
import be.kc.persondata.service.PersonDataService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private String welcomeMessage;

    private PersonDataService personDataService;

    private UserRepository userRepository;

    public SearchFormController(PersonDataService personDataService, UserRepository userRepository) {
        this.personDataService = personDataService;
        this.userRepository = userRepository;
    }


    @RequestMapping("/searchForm")
    public String searchForm(Model model, @AuthenticationPrincipal OAuth2User principal) {
        LOGGER.debug("**********in searchForm *********");

        User user = getAuthenticatedUser(principal);
        if (user != null) {
            model.addAttribute("message", welcomeMessage + ": welcome " + user.getName());
            model.addAttribute("personData", new PersonDataBuilder().createPersonData());

            return "searchForm";
        }

        model.addAttribute("message", "You do not have permission to search.");
        return "error";
    }


    @RequestMapping("/search")
    public String searchPersonData(@ModelAttribute PersonData personData, Model model) {
        LOGGER.debug("**********searchFrom: searchPersonData *********");

        // TODO refactor making use of Spring specification: https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
        List<PersonDataDTO> personDataDTOList = null;
        if (isFindAll().test(personData)) {
            personDataDTOList = personDataService.findAllSortedByLastNameFirstNameAndCity();
        } else if (isFindByStreetAndCity().test(personData)) {
            personDataDTOList = personDataService.findByStreetAndCity(personData.getStreet(), personData.getCity());
        } else if (isFindByLastName().test(personData)) {
            personDataDTOList = personDataService.findByLastName(personData.getLastName());
        } else if (isFindByLastAndFirstName().test(personData)) {
            personDataDTOList = personDataService.findByLastNameAndFirstName(personData.getLastName(), personData.getFirstName());
        } else if (isFindByStreetAndNumber().test(personData)) {
            personDataDTOList = personDataService.findByStreetAndNumberAndCity(personData.getStreet(), personData.getNumber(), personData.getCity());
        } else if (isFindByStreet().test(personData)) {
            personDataDTOList = personDataService.findByStreet(personData.getStreet());
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

    private Predicate<PersonData> isFindByStreetAndCity() {
        return personData -> StringUtils.isNotBlank(personData.getStreet())
                && StringUtils.isNotBlank(personData.getCity());
    }

    private Predicate<PersonData> isFindByStreetAndNumber() {
        return personData -> StringUtils.isNotBlank(personData.getStreet())
                && StringUtils.isNotBlank(personData.getNumber());
    }

    private Predicate<PersonData> isFindByStreet() {
        return personData -> StringUtils.isNotBlank(personData.getStreet());
    }

    private User getAuthenticatedUser(OAuth2User principal) {
        return userRepository.findByNameAndEmail(
                principal.getAttribute("name"),
                principal.getAttribute("email")
        );
    }
}