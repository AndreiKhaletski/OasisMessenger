package by.oasis.controller.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String mainPage(){
        return "mainPage";
    }

    @GetMapping("/main/reg")
    public String registration(){
        return "registration";
    }

    @GetMapping("/main/ver")
    public String verification(){
        return "verification";
    }

    @GetMapping("/main/log")
    public String login(){
        return "login";
    }
}
