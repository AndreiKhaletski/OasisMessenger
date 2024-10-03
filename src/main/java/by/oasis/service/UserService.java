package by.oasis.service;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.core.enums.EnumStatusRegistration;
import by.oasis.dao.api.IUserResource;
import by.oasis.dao.entity.RegistrationEntity;
import by.oasis.service.api.IUserService;
import by.oasis.service.api.IVerificationService;
import by.oasis.service.converter.RegistrationConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final IUserResource userResource;
    private final PasswordEncoder encoder;
    private final RegistrationConverter converter;
    private final IVerificationService verificationService;

    public UserService(IUserResource userResource,
                       PasswordEncoder encoder,
                       RegistrationConverter converter,
                       IVerificationService verificationService) {
        this.userResource = userResource;
        this.encoder = encoder;
        this.converter = converter;
        this.verificationService = verificationService;
    }

    @Override
    @Transactional
    public void create(RegistrationDto registrationDto) {
        RegistrationEntity registrationEntity = converter.convertToRegEntity(registrationDto);

        if (userResource.findByEmail(registrationEntity.getEmail()) == null){
            registrationEntity.setUuid(UUID.randomUUID());
            registrationEntity.setPassword(encoder.encode(registrationDto.getPassword()));

            if (registrationEntity.getStatus() == EnumStatusRegistration.WAITING_ACTIVATION){
                verificationService.create(registrationDto.getEmail());
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
    public void save(RegistrationEntity registrationEntity) {
        userResource.saveAndFlush(registrationEntity);
    }
}
