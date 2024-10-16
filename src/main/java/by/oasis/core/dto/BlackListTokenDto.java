package by.oasis.core.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlackListTokenDto {
    public UUID uuid;
    public String token;
    private LocalDateTime blocktime_at;
}
