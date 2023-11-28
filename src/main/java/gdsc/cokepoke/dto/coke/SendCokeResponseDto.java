package gdsc.cokepoke.dto.coke;

import gdsc.cokepoke.entity.Coke;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendCokeResponseDto {
    private Long id;

    public static SendCokeResponseDto of(Coke save) {
        return SendCokeResponseDto.builder()
                .id(save.getId())
                .build();
    }
}
