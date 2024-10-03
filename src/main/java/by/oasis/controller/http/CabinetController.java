package by.oasis.controller.http;

import by.oasis.core.dto.AuthorizationDto;
import by.oasis.core.dto.RegistrationDto;
import by.oasis.service.api.ICabinetService;
import by.oasis.service.converter.RegistrationConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
//        URI verificationUri = URI.create("/ver");
//        return ResponseEntity.status(HttpStatus.FOUND).location(verificationUri).build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/verification")
    public ResponseEntity<?> get(@RequestParam("code") String code,
                                 @RequestParam("mail") String email){
        cabinetService.verification(code, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authorization(@RequestBody AuthorizationDto authorizationDto){
        String authorization = cabinetService.authorization(authorizationDto);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, authorization).build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> exit(){

        return null;
    }

    @GetMapping(value = "/me")
    public ResponseEntity meInformation(){
        RegistrationDto registrationDto = converter.convertToRegDto(cabinetService.getInfoMe());
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }
}
