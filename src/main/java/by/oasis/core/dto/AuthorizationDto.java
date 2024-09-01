package by.oasis.core.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorizationDto {
    public String email;
    public String password;
}
