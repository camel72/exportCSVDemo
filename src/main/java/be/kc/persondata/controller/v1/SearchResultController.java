package be.kc.persondata.controller.v1;

import be.kc.persondata.controller.v1.model.PersonDataDTO;
import be.kc.persondata.service.PersonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchResultController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultController.class);

    private PersonDataService service;

    public SearchResultController(PersonDataService personDataService) {
        service = personDataService;
    }

    @GetMapping("/map/street/{street}/number/{number}/city/{city}")
    public String map(@PathVariable("street") String street, @PathVariable("number") String number, @PathVariable("city") String city) {
        LOGGER.info("**********SearchResultController: map*********" + street);

        String redirectUrl = String.format("https://www.google.com/maps/search/?api=1&query=%s %s %S", street, number, city);
        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/search/street/{street}/number/{number}")
    public String search(@PathVariable("street") String street, @PathVariable("number") String number, @PathVariable("city") String city, Model model) {
        LOGGER.info("**********SearchResultController: searchSameAddress*********");

        List<PersonDataDTO> personDataDTOList = service.findByStreetAndNumberAndCity(street, number, city);
        model.addAttribute(personDataDTOList);
        return "searchResult";
    }
}