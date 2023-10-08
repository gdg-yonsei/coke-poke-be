package gdsc.cokepoke.dto;

import lombok.*;

@Data
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;
}
