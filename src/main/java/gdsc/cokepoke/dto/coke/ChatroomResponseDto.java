package gdsc.cokepoke.dto.coke;

import gdsc.cokepoke.entity.Chatroom;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatroomResponseDto {
    private Long id;
    private String member1Email;
    private String member2Email;

    public static ChatroomResponseDto of(Chatroom save) {
        return ChatroomResponseDto.builder()
                .id(save.getId())
                .member1Email(save.getMember1Email())
                .member2Email(save.getMember2Email())
                .build();
    }
}
