package gdsc.cokepoke.dto.member;

import lombok.*;

@Data
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
