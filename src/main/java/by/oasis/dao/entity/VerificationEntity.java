package by.oasis.dao.entity;

import by.oasis.core.enums.EnumStatusSendEmail;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "verification", schema = "app")
public class VerificationEntity {

    @Id
    @Column(name = "email")
    public String email;

    @Column(name = "code")
    public String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public EnumStatusSendEmail status;

    public VerificationEntity() {
    }

    public VerificationEntity(String email, String code, EnumStatusSendEmail status) {
        this.email = email;
        this.code = code;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EnumStatusSendEmail getStatus() {
        return status;
    }

    public void setStatus(EnumStatusSendEmail status) {
        this.status = status;
    }
}
