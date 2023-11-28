package gdsc.cokepoke.controller;

import gdsc.cokepoke.dto.coke.*;
import gdsc.cokepoke.service.CokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CokeController {
    private final CokeService cokeService;

    // Coke 전송
    @PostMapping("/coke")
    public ResponseEntity<SendCokeResponseDto> sendCoke(@RequestBody SendCokeRequestDto sendCokeRequestDto) {
        return ResponseEntity.ok(cokeService.sendCoke(sendCokeRequestDto));
    }

    // 채팅방 목록 조회
    @GetMapping("/chatroom/{email:.+}/")
    public ResponseEntity<List<ChatroomResponseDto>> getChatroomList(@PathVariable("email") String email) {
        return ResponseEntity.ok(cokeService.getChatroomList(email));
    }

    // 채팅방 내 모든 Coke 조회
    @GetMapping("/coke/{chatroomId}")
    public ResponseEntity<List<GetCokeResponseDto>> getCokeList(@PathVariable("chatroomId") Long chatroomId) {
        return ResponseEntity.ok(cokeService.getCokeList(chatroomId));
    }


}
