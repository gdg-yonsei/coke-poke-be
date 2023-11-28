package gdsc.cokepoke.service;

import gdsc.cokepoke.dto.coke.*;
import gdsc.cokepoke.entity.Chatroom;
import gdsc.cokepoke.entity.Coke;
import gdsc.cokepoke.repository.ChatroomRepository;
import gdsc.cokepoke.repository.CokeRepository;
import gdsc.cokepoke.repository.FriendRelationshipRepository;
import gdsc.cokepoke.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CokeService {
    private final MemberRepository memberRepository;
    private final FriendRelationshipRepository friendRelationshipRepository;
    private final CokeRepository cokeRepository;
    private final ChatroomRepository chatroomRepository;

    public SendCokeResponseDto sendCoke(SendCokeRequestDto sendCokeRequestDto) {
        // Exception 1 : 존재하지 않는 Receiver Email인 경우
        if (!memberRepository.existsByEmail(sendCokeRequestDto.getReceiverEmail())) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다. 이메일 주소를 다시 확인해주세요.");
        }
        // Exception 2 : 친구 관계가 아닌 경우
        if (!friendRelationshipRepository.existsBySenderEmailAndReceiverEmail(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail())) {
            throw new IllegalArgumentException("친구 관계가 아닙니다. 친구 관계를 먼저 맺어주세요.");
        }
        // Exception 3 : Coke는 같은 사용자에게 하루 최대 한 번만 보낼 수 있음
        LocalDate today = LocalDate.now();
        if (cokeRepository.existsBySenderAndReceiverAndCreatedDate(
                sendCokeRequestDto.getSenderEmail(),
                sendCokeRequestDto.getReceiverEmail(),
                today)) {
            throw new IllegalArgumentException("같은 친구에게 하루 최대 한 번만 Coke를 보낼 수 있습니다.");
        }
        // 최초 Coke 전송일 경우 채팅방 먼저 생성
        Chatroom chatroom = chatroomRepository.findByFirstEmailAndSecondEmail(
                sendCokeRequestDto.getSenderEmail(),
                sendCokeRequestDto.getReceiverEmail())
                .orElseGet(() -> {
                    Chatroom newChatroom = Chatroom.builder()
                            .member1Email(sendCokeRequestDto.getSenderEmail())
                            .member2Email(sendCokeRequestDto.getReceiverEmail())
                            .build();
                    return chatroomRepository.save(newChatroom);
                });
        // 이후 채팅방에 Coke 전송
        Coke coke = Coke.builder()
                .chatroom(chatroom)
                .senderEmail(sendCokeRequestDto.getSenderEmail())
                .receiverEmail(sendCokeRequestDto.getReceiverEmail())
                .build();
        return SendCokeResponseDto.of(cokeRepository.save(coke));
    }

    public List<ChatroomResponseDto> getChatroomList(String userEmail) {
        // 채팅방 목록 조회
        List<Chatroom> chatroomList = chatroomRepository.findByMemberEmail(userEmail);
        return chatroomList.stream()
                .map(ChatroomResponseDto::of)
                .collect(Collectors.toList());

    }

    public List<GetCokeResponseDto> getCokeList(Long chatroomId) {
        // Exception : 해당 채팅방 아이디가 존재하지 않는 경우
        if (!chatroomRepository.existsById(chatroomId)) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
        // 채팅방 내 모든 Coke 내역 조회
        List<Coke> cokeList = cokeRepository.findByChatroomId(chatroomId);
        return cokeList.stream()
                .map(GetCokeResponseDto::of)
                .collect(Collectors.toList());
    }
}
