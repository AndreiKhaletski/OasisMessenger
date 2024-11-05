package by.oasis.controller.http;

import by.oasis.core.dto.AuthorizationDto;
import by.oasis.core.dto.ChangePasswordDto;
import by.oasis.core.dto.RegistrationDto;
import by.oasis.core.dto.ResetPasswordDto;
import by.oasis.service.api.ICabinetService;
import by.oasis.service.converter.RegistrationConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping(value = "/pre-change-password")
    public ResponseEntity<?> preChangePasswordUser(){
        cabinetService.preChangePassword();
        return ResponseEntity.ok().body("Код для изменения аккаунта выслан на вашу почту");
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordDto changePasswordDto){
        cabinetService.changePassword(changePasswordDto);
        return ResponseEntity.ok().body("Вы успешно изменили пароль!");
    }

    @PostMapping(value = "/pre-delete-me-account")
    public ResponseEntity<?> preDeleteMeAccount(){
        cabinetService.preDeleteMeAccount();
        return ResponseEntity.ok().body("Код для удаления аккаунта выслан на вашу почту");
    }

    @DeleteMapping(value = "/delete-me-account")
    public ResponseEntity<?> deleteAccount(@RequestParam("codeDeleteAccount") String codeDeleteAccount){
        try {
            cabinetService.deleteMeAccount(codeDeleteAccount);
            return ResponseEntity.ok().body("Аккаунт успешно удален");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при удалении аккаунта");
        }
    }

    @PostMapping(value = "/pre-password-reset")
    public ResponseEntity<?> prePasswordReset(@RequestParam("email") String email) {
        cabinetService.prePasswordReset(email);
        return ResponseEntity.ok().body("Код для сброса пароля выслан на вашу почту");
    }

    @PutMapping("/password-reset/{uuid}")
    public ResponseEntity<?> passwordReset(@PathVariable UUID uuid, @RequestBody ResetPasswordDto resetPasswordDto) {
        boolean isResetSuccessful = cabinetService.resetPassword(
            uuid,
            resetPasswordDto.getCodeResetPassword(),
            resetPasswordDto.getNewPassword());
        if (isResetSuccessful) {
            return ResponseEntity.ok().body("Ваш пароль был успешно сброшен");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка сброса пароля");
        }
    }
}
