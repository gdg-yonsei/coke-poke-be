package gdsc.cokepoke.dto;

import lombok.*;

@Data
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
