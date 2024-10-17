package by.oasis.dao.api;

import by.oasis.dao.entity.BlackListTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBlackListTokenResource extends JpaRepository<BlackListTokenEntity, UUID> {
    
    Boolean existsByToken(String token);
}
