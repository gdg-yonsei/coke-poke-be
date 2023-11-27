package gdsc.cokepoke.dto.friend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetFriendResponseDto {
    private String friendEmail;

    public static GetFriendResponseDto of(String email) {
        return GetFriendResponseDto.builder()
                .friendEmail(email)
                .build();
    }
}
