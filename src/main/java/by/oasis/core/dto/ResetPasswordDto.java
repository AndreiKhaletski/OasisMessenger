package by.oasis.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordDto {
    public String codeResetPassword;
    public String newPassword;
}
