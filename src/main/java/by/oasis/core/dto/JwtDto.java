package by.oasis.core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class JwtDto {

    @NotBlank(message = "Значение токена не должно быть пустым")
    private final String token;
}
