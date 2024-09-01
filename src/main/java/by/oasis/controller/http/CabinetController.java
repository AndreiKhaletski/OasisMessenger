package by.oasis.controller.http;

import by.oasis.service.api.ICabinetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {

    private final ICabinetService cabinetService;


    public CabinetController(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }


    // создание, верификация, логин, обо мне, выход;

    @PostMapping(value = "/registration")
    public ResponseEntity<?> create(){

        return null;
    }

    @GetMapping(value = "/verification")
    public ResponseEntity<?> get(){

        return null;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authorization(){

        return null;
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> exit(){

        return null;
    }

    @GetMapping(value = "/me")
    public ResponseEntity meInformation(){

        return null;
    }
}
