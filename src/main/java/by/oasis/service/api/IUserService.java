package by.oasis.service.api;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.dao.entity.RegistrationEntity;

public interface IUserService {
    void create(RegistrationDto registrationDto);

    RegistrationEntity findByEmail(String username);

    void save(RegistrationEntity registrationEntity);

    void addTokenToLock(String token);

    void setNewPassword(RegistrationEntity registrationEntity);

    Boolean toketInBlackList(String token);

    void deleteUserAfterVerification(String email);
}
