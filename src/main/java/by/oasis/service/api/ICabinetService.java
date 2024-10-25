package by.oasis.service.api;

import by.oasis.core.dto.AuthorizationDto;
import by.oasis.core.dto.ChangePasswordDto;
import by.oasis.core.dto.RegistrationDto;
import by.oasis.dao.entity.RegistrationEntity;

public interface ICabinetService {
    void create(RegistrationDto registrationDto);

    void verification(String code, String mail);

    String authorization(AuthorizationDto authorizationDto);

    RegistrationEntity getInfoMe();

    void blackListToken(String token);

    void preChangePassword();

    void changePassword(ChangePasswordDto changePasswordDto);

    void preDeleteMeAccount();

    void deleteMeAccount(String codeDeleteAccount);
}
