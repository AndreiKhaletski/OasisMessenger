package by.oasis.service.emailservice.api;

import by.oasis.dao.entity.VerificationEntity;

public interface IEmailService {
    void sendEmailMessage(VerificationEntity VerificationEntity, String title, String text);
}
