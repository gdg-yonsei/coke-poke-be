package gdsc.cokepoke.dto.friend;

import lombok.Data;

@Data
public class CreateFriendRequestDto {
    private String senderEmail;
    private String receiverEmail;
}
