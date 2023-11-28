package gdsc.cokepoke.dto.coke;

import lombok.Data;

@Data
public class SendCokeRequestDto {
    private String senderEmail;
    private String receiverEmail;
}

