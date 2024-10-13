package by.oasis.controller.http;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/administration")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService adminService) {
        this.userService = adminService;
    }


    @PostMapping
    public ResponseEntity<?> create (@RequestBody RegistrationDto registrationDto){
        userService.create(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
