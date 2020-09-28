package be.kc.persondata.controller.v1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Value("${welcome.message}")
    private String welcomMessage;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        System.out.println("************" + welcomMessage + "/////////////");
        model.addAttribute("message", welcomMessage);

        return "index";
    }
}
