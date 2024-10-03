package by.oasis.dao.entity;

import by.oasis.core.enums.EnumRoles;
import by.oasis.core.enums.EnumStatusRegistration;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
public class RegistrationEntity {

    @Id
    @Column(name = "uuid")
    public UUID uuid;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public EnumRoles role;

    @Column(name = "name")
    public String name;

    @Column(name = "surname")
    public String surname;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "birth_day")
    public LocalDate birthDay;

    @CreationTimestamp
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @UpdateTimestamp
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumStatusRegistration status;

    public RegistrationEntity() {
    }

    public RegistrationEntity(UUID uuid,
                              String email,
                              String password,
                              EnumRoles role,
                              String name,
                              String surname,
                              String nickname,
                              LocalDate birthDay,
                              LocalDateTime dtCreate,
                              LocalDateTime dtUpdate,
                              EnumStatusRegistration status) {
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.birthDay = birthDay;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumRoles getRole() {
        return role;
    }

    public void setRole(EnumRoles role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public EnumStatusRegistration getStatus() {
        return status;
    }

    public void setStatus(EnumStatusRegistration status) {
        this.status = status;
    }
}
