package gdsc.cokepoke.dto.coke;

import gdsc.cokepoke.entity.Coke;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetCokeResponseDto {
    private Long id;
    private String senderEmail;
    private String receiverEmail;
    private LocalDateTime createdDate;

    public static GetCokeResponseDto of(Coke save) {
        return GetCokeResponseDto.builder()
                .id(save.getId())
                .senderEmail(save.getSenderEmail())
                .receiverEmail(save.getReceiverEmail())
                .createdDate(save.getCreatedDate())
                .build();
    }
}
