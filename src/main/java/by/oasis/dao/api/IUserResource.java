package by.oasis.dao.api;

import by.oasis.dao.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserResource extends JpaRepository<RegistrationEntity, UUID> {

    RegistrationEntity findByEmail(String email);
}
