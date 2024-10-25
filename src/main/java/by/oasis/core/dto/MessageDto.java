package by.oasis.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    private String from;
    private String text;
}
