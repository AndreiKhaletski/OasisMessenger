package by.oasis.controller.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/reg")
    public String registration(){
        return "registration";
    }

    @GetMapping("/ver")
    public String verification(){
        return "verification";
    }
}
