package by.oasis.service.emailservice.properties;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailProperties {

    private final Properties props;

    public EmailProperties(EmailConfiguration emailConfigurationl) {
        // Настройка свойств электронной почты mail.ru
        this.props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", emailConfigurationl.getRecipient());
        props.put("mail.smtp.password", emailConfigurationl.getPassword());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

    public Properties getProps() {
        return props;
    }
}