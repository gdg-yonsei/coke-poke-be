package gdsc.cokepoke.dto.member;

import gdsc.cokepoke.entity.Member;
import lombok.*;

@Data
@Builder
public class MemberResponseDto {
    private Long id;
    private String email;
    public static MemberResponseDto of(Member save) {
        return MemberResponseDto.builder()
                .id(save.getId())
                .email(save.getEmail())
                .build();
    }
}
