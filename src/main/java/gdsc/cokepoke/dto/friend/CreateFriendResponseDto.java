package gdsc.cokepoke.dto.friend;

import gdsc.cokepoke.entity.FriendRelationship;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFriendResponseDto {
    private Long id;

    public static CreateFriendResponseDto of(FriendRelationship save) {
        return CreateFriendResponseDto.builder()
                .id(save.getId())
                .build();
    }
}
