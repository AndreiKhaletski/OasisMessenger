package by.oasis.controller.http;

import by.oasis.core.dto.AuthorizationDto;
import by.oasis.core.dto.ChangePasswordDto;
import by.oasis.core.dto.RegistrationDto;
import by.oasis.service.api.ICabinetService;
import by.oasis.service.converter.RegistrationConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/cabinet")
public class CabinetController {

    private final ICabinetService cabinetService;
    private final RegistrationConverter converter;


    public CabinetController(ICabinetService cabinetService,
                             RegistrationConverter converter) {
        this.cabinetService = cabinetService;
        this.converter = converter;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> create(@RequestBody RegistrationDto registrationDto){
        cabinetService.create(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/verification")
    public ResponseEntity<?> get(@RequestParam("email") String email,
                                 @RequestParam("code") String code){
        cabinetService.verification(code, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<?> authorization(@RequestBody AuthorizationDto authorizationDto) {
//        String token = "Bearer " + cabinetService.authorization(authorizationDto);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//        return ResponseEntity.ok().headers(headers).build();
//    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authorization(@RequestBody AuthorizationDto authorizationDto){
        String authorization = cabinetService.authorization(authorizationDto);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, authorization).build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> exit(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        cabinetService.blackListToken(token);
        return ResponseEntity.ok().body("Вы успешно вышли из аккаунта!");
    }

    @GetMapping(value = "/me")
    public ResponseEntity meInformation(){
        RegistrationDto registrationDto = converter.convertToRegDto(cabinetService.getInfoMe());
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordDto changePasswordDto){
        cabinetService.changePassword(changePasswordDto);
        return ResponseEntity.ok().body("Вы успешно изменили пароль!");
    }
}
