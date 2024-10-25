package by.oasis.service;

import by.oasis.core.dto.AuthorizationDto;
import by.oasis.core.dto.ChangePasswordDto;
import by.oasis.core.dto.RegistrationDto;
import by.oasis.core.enums.EnumRoles;
import by.oasis.core.enums.EnumStatusRegistration;
import by.oasis.dao.entity.RegistrationEntity;
import by.oasis.service.api.ICabinetService;
import by.oasis.service.api.IUserService;
import by.oasis.service.api.IVerificationService;
import by.oasis.service.emailservice.TextMessage;
import by.oasis.service.jwt.JwtTokenHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class CabinetService implements ICabinetService {

    private final IUserService userService;
    private final IVerificationService verificationService;
    private final JwtTokenHandler jwtTokenHandler;
    private final PasswordEncoder passwordEncoder;

    public CabinetService(IUserService userService,
                          IVerificationService verificationService,
                          JwtTokenHandler jwtTokenHandler,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.verificationService = verificationService;
        this.jwtTokenHandler = jwtTokenHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(RegistrationDto registrationDto) {
        registrationDto.setRole(EnumRoles.ROLE_USER);
        registrationDto.setStatus(EnumStatusRegistration.WAITING_ACTIVATION);
        userService.create(registrationDto);
    }

    @Override
    @Transactional
    public void verification(String code, String email) {
        RegistrationEntity registrationEntity = userService.findByEmail(email);

        if(Objects.equals(code, verificationService.get(email))){
            registrationEntity.setStatus(EnumStatusRegistration.ACTIVATED);
            userService.save(registrationEntity);
        }else {
            throw new IllegalArgumentException("Неверный код верификации!");
        }
    }

    @Override
    @Transactional
    public String authorization(AuthorizationDto authorizationDto) {
        RegistrationEntity registrationEntity = userService.findByEmail(authorizationDto.getEmail());

        if (!passwordEncoder.matches(authorizationDto.getPassword(), registrationEntity.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль!");
        }

        if (registrationEntity.getStatus() == EnumStatusRegistration.ACTIVATED){
            String token = jwtTokenHandler.generateAccessToken(
                    authorizationDto.getEmail(),
                    String.valueOf(registrationEntity.getRole()));
            registrationEntity.setToken(token);
            registrationEntity.setDtUpdate(LocalDateTime.now(ZoneOffset.UTC));
            userService.save(registrationEntity);
            return token;
        }else{
            throw new IllegalArgumentException("Необходимо подтвердить аккаунт для входа в систему!");
        }
    }

    @Override
    public RegistrationEntity getInfoMe() {
        return userService.findByEmail(UserHolder.getUser().getUsername());
    }

    @Override
    @Transactional
    public void blackListToken(String token) {
        userService.addTokenToLock(token);
    }

    @Override
    public void preChangePassword() {
        RegistrationEntity registrationEntity = userService.findByEmail(UserHolder.getUser().getUsername());
        Boolean blackToken = userService.toketInBlackList("Bearer " + registrationEntity.getToken());

        if (blackToken){
            throw new IllegalArgumentException("Ваш токен аннулирован!");
        }

        TextMessage textMessage = new TextMessage();
        verificationService.create(
                registrationEntity.getEmail(),
                textMessage.CHANGE_PASSWORD_TITLE,
                textMessage.CHANGE_PASSWORD_TEXT);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        RegistrationEntity registrationEntity = userService.findByEmail(UserHolder.getUser().getUsername());
        Boolean blackToken = userService.toketInBlackList("Bearer " + registrationEntity.getToken());

        String codeToDB = verificationService.get(registrationEntity.getEmail());

        if (blackToken){
            throw new IllegalArgumentException("Ваш токен аннулирован!");
        }

        if (!Objects.equals(changePasswordDto.getCodeToChangePassword(), codeToDB)){
            throw new IllegalArgumentException("Неверный код подтверждения!");
        }

        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), registrationEntity.getPassword())){
            throw new IllegalArgumentException("Текущий пароль не совпадает с паролем вашего аккаунта!");
        }

        if (Objects.equals(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmNewPassword())){
            registrationEntity.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userService.setNewPassword(registrationEntity);
        }else{
            throw new IllegalArgumentException("Поля нового пароля не совпадают!");
        }
    }

    @Override
    public void preDeleteMeAccount() {
        RegistrationEntity registrationEntity = userService.findByEmail(UserHolder.getUser().getUsername());
        Boolean blackToken = userService.toketInBlackList("Bearer " + registrationEntity.getToken());

        if (blackToken){
            throw new IllegalArgumentException("Ваш токен аннулирован!");
        }

        TextMessage textMessage = new TextMessage();
        verificationService.create(
                registrationEntity.getEmail(),
                textMessage.DELETE_ME_ACCOUNT_TITLE,
                textMessage.DELETE_ME_ACCOUNT_TEXT);
    }

    @Override
    @Transactional
    public void deleteMeAccount(String codeDeleteAccount) {
        RegistrationEntity registrationEntity = userService.findByEmail(UserHolder.getUser().getUsername());
        Boolean blackToken = userService.toketInBlackList("Bearer " + registrationEntity.getToken());

        String codeToDB = verificationService.get(registrationEntity.getEmail());

        if (blackToken){
            throw new IllegalArgumentException("Ваш токен аннулирован!");
        }

        if(!Objects.equals(codeDeleteAccount, codeToDB)){
            throw new IllegalArgumentException("Неверный код подтверждения!");
        }

        userService.deleteUserAfterVerification(UserHolder.getUser().getUsername());
    }
}
