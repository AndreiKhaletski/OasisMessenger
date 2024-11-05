package by.oasis.service.api;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.dao.entity.RegistrationEntity;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    void create(RegistrationDto registrationDto);

    RegistrationEntity findByEmail(String username);

    void save(RegistrationEntity registrationEntity);

    void addTokenToLock(String token);

    void setNewPassword(RegistrationEntity registrationEntity);

    void deleteUserAfterVerification(String email);

    Optional<RegistrationEntity> findByUuid(UUID uuid);

}
