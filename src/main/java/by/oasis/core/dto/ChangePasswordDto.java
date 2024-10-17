package by.oasis.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChangePasswordDto {
    public String currentPassword;
    public String newPassword;
    public String confirmNewPassword;
}
