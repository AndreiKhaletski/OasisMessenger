package by.oasis.dao.api;

import by.oasis.dao.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserVerificationResource extends JpaRepository<VerificationEntity, UUID> {

    VerificationEntity findByEmail(String email);
}
