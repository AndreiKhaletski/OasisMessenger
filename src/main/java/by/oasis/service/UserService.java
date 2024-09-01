package by.oasis.service;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.dao.api.IUserResource;
import by.oasis.dao.entity.RegistrationEntity;
import by.oasis.service.api.IUserService;
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

    public UserService(IUserResource userResource, PasswordEncoder encoder, RegistrationConverter converter) {
        this.userResource = userResource;
        this.encoder = encoder;
        this.converter = converter;
    }

    @Override
    @Transactional
    public void create(RegistrationDto registrationDto) {
        RegistrationEntity registrationEntity = converter.convertToRegEntity(registrationDto);
        registrationEntity.setUuid(UUID.randomUUID());
        registrationEntity.setPassword(encoder.encode(registrationDto.getPassword()));
        userResource.saveAndFlush(registrationEntity);
    }

    @Override
    public RegistrationEntity findByEmail(String email) {
        return userResource.findByEmail(email);
    }
}
