package by.oasis.service;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.core.enums.EnumStatusRegistration;
import by.oasis.dao.api.IUserResource;
import by.oasis.dao.entity.RegistrationEntity;
import by.oasis.service.api.IBlackListTokenService;
import by.oasis.service.api.IUserService;
import by.oasis.service.api.IVerificationService;
import by.oasis.service.converter.RegistrationConverter;
import by.oasis.service.emailservice.TextMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final IUserResource userResource;
    private final PasswordEncoder encoder;
    private final RegistrationConverter converterReg;
    private final IVerificationService verificationService;
    private final IBlackListTokenService blackListTokenService;

    public UserService(IUserResource userResource,
                       PasswordEncoder encoder,
                       RegistrationConverter converterReg,
                       IVerificationService verificationService,
                       IBlackListTokenService blackListTokenService) {
        this.userResource = userResource;
        this.encoder = encoder;
        this.converterReg = converterReg;
        this.verificationService = verificationService;
        this.blackListTokenService = blackListTokenService;
    }

    @Override
    @Transactional
    public void create(RegistrationDto registrationDto) {
        RegistrationEntity registrationEntity = converterReg.convertToRegEntity(registrationDto);

        if (userResource.findByEmail(registrationEntity.getEmail()) == null){
            registrationEntity.setUuid(UUID.randomUUID());
            registrationEntity.setPassword(encoder.encode(registrationDto.getPassword()));

            if (registrationEntity.getStatus() == EnumStatusRegistration.WAITING_ACTIVATION){

                TextMessage textMessage = new TextMessage();
                verificationService.create(
                        registrationDto.getEmail(),
                        textMessage.WELCOME_TITLE,
                        textMessage.WELCOME_TEXT);
            }
            userResource.save(registrationEntity);
        }else {
            throw new IllegalArgumentException("Такой пользователь уже зарегистрирован!");
        }
    }

    @Override
    public RegistrationEntity findByEmail(String email) {
        return userResource.findByEmail(email);
    }

    @Override
    @Transactional
    public void save(RegistrationEntity registrationEntity) {
        userResource.save(registrationEntity);
    }

    @Override
    @Transactional
    public void addTokenToLock(String token) {

        //Проблема с @Version, а точнее с версией обновления...
        // В RegistrationEntity @Version закомментирован
        RegistrationEntity registrationEntity = userResource
                        .findByEmail(UserHolder.getUser().getUsername());
        registrationEntity.setToken(null);
        registrationEntity.setDtUpdate(LocalDateTime.now(ZoneOffset.UTC));
        System.out.println("Текущая вер.: " + registrationEntity.getDtUpdate());
        userResource.saveAndFlush(registrationEntity);
        System.out.println("Обновленная вер.: " + registrationEntity.getDtUpdate());

        blackListTokenService.add(token);
    }

    @Override
    @Transactional
    public void setNewPassword(RegistrationEntity registrationEntity) {
        userResource.save(registrationEntity);
    }

    @Override
    public void deleteUserAfterVerification(String email) {
        RegistrationEntity registrationEntity = findByEmail(email);
        userResource.deleteById(registrationEntity.getUuid());
    }
}
