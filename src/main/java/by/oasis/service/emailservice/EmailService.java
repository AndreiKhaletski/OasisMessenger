package by.oasis.service.emailservice;

import by.oasis.dao.entity.VerificationEntity;
import by.oasis.service.emailservice.api.IEmailService;
import by.oasis.service.emailservice.properties.EmailProperties;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {

    private final EmailProperties emailProperties;

    public EmailService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public void sendEmailMessage(VerificationEntity VerificationEntity) {

        // Создание сессии электронной почты unkingip@mail.ru
        Session session = Session.getInstance(emailProperties.getProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        emailProperties.getProps().getProperty("mail.smtp.user"),
                        emailProperties.getProps().getProperty("mail.smtp.password")
                );
            }
        });

        // Создание сообщения электронной почты mail.ru
        Message messageToUser = new MimeMessage(session);
        try {
            messageToUser.setFrom(new InternetAddress(
                    emailProperties.getProps().getProperty("mail.smtp.user"))
            );
            messageToUser.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(VerificationEntity.getEmail()));
            messageToUser.setSubject("Подтверждение почтового адреса");
            messageToUser.setText(VerificationEntity.getCode());

            // Отправка сообщения для пользователя из MessagesDTO
            Transport.send(messageToUser);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email" + e.getMessage(), e);
        }
    }
}
