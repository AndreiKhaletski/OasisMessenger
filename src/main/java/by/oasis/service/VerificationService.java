package by.oasis.service;

import by.oasis.core.enums.EnumStatusSendEmail;
import by.oasis.dao.api.IUserVerificationResource;
import by.oasis.dao.entity.VerificationEntity;
import by.oasis.service.api.IVerificationService;
import by.oasis.service.emailservice.TextMessage;
import by.oasis.service.emailservice.api.IEmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.ExecutorService;

@Service
@Transactional(readOnly = true)
public class VerificationService implements IVerificationService {

    private final ExecutorService executorService;
    private final IEmailService emailService;
    private final IUserVerificationResource verificationResource;


    public VerificationService(ExecutorService executorService,
                               IEmailService emailService,
                               IUserVerificationResource verificationResource) {
        this.executorService = executorService;
        this.emailService = emailService;
        this.verificationResource = verificationResource;
    }

    @Override
    @Transactional
    public void create(String email, String title, String text) {
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setEmail(email);
        verificationEntity.setCode(String.valueOf(new Random().nextInt(100000)));
        verificationEntity.setStatus(EnumStatusSendEmail.LOADED);

        executorService.submit(() -> {
            try {
                emailService.sendEmailMessage(verificationEntity, title, text);
                verificationEntity.setStatus(EnumStatusSendEmail.OK);
                verificationResource.saveAndFlush(verificationEntity);

            }catch (Exception e){
                verificationEntity.setStatus(EnumStatusSendEmail.ERROR);
                verificationResource.saveAndFlush(verificationEntity);
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String get(String email) {
        return verificationResource.findByEmail(email).getCode();
    }


}
