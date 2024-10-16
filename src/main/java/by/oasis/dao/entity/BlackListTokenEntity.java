package by.oasis.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "black_list_tokens", schema = "app")
public class BlackListTokenEntity {

    @Id
    @Column(name = "uuid")
    public UUID uuid;

    @Column(name = "token")
    public String token;

    @Column(name = "blocktime_at")
    @CreationTimestamp
    private LocalDateTime blocktime_at;

    public BlackListTokenEntity() {
    }

    public BlackListTokenEntity(UUID uuid,
                                String token,
                                LocalDateTime blocktime_at) {
        this.uuid = uuid;
        this.token = token;
        this.blocktime_at = blocktime_at;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getBlocktime_at() {
        return blocktime_at;
    }

    public void setBlocktime_at(LocalDateTime blocktime_at) {
        this.blocktime_at = blocktime_at;
    }
}
