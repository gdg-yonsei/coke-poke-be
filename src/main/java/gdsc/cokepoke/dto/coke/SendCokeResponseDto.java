package gdsc.cokepoke.dto.coke;

import gdsc.cokepoke.entity.Coke;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendCokeResponseDto {
    private Long id;
    private String senderEmail;
    private String receiverEmail;

    public static SendCokeResponseDto of(Coke save) {
        return SendCokeResponseDto.builder()
                .id(save.getId())
                .senderEmail(save.getSenderEmail())
                .receiverEmail(save.getReceiverEmail())
                .build();
    }
}
