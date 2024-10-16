package by.oasis.core.dto;


import by.oasis.core.enums.EnumRoles;
import by.oasis.core.enums.EnumStatusRegistration;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationDto {
    public UUID uuid;
    public String email;
    public String password;
    public EnumRoles role;
    public EnumStatusRegistration status;
    public String name;
    public String surname;
    public String nickname;
    public LocalDate birth_day;
    private LocalDateTime dt_create;
    private LocalDateTime dt_update;
    private String token;
}
