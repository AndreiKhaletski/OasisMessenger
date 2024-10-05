package by.oasis.service;

import by.oasis.core.dto.AuthorizationDto;
import by.oasis.core.dto.RegistrationDto;
import by.oasis.core.enums.EnumRoles;
import by.oasis.core.enums.EnumStatusRegistration;
import by.oasis.dao.entity.RegistrationEntity;
import by.oasis.service.api.ICabinetService;
import by.oasis.service.api.IUserService;
import by.oasis.service.api.IVerificationService;
import by.oasis.service.jwt.JwtTokenHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return token;
        }else{
            throw new IllegalArgumentException("Необходимо подтвердить аккаунт для входа в систему!");
        }
    }

    @Override
    public RegistrationEntity getInfoMe() {
        return userService.findByEmail(UserHolder.getUser().getUsername());
    }
}
