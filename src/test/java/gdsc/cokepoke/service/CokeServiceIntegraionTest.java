package gdsc.cokepoke.service;

import gdsc.cokepoke.dto.coke.SendCokeRequestDto;
import gdsc.cokepoke.dto.coke.SendCokeResponseDto;
import gdsc.cokepoke.entity.FriendRelationship;
import gdsc.cokepoke.entity.Member;
import gdsc.cokepoke.repository.ChatroomRepository;
import gdsc.cokepoke.repository.CokeRepository;
import gdsc.cokepoke.repository.FriendRelationshipRepository;
import gdsc.cokepoke.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CokeServiceIntegraionTest {

    @Autowired CokeService cokeService;
    @Autowired MemberRepository memberRepository;
    @Autowired ChatroomRepository chatroomRepository;
    @Autowired FriendRelationshipRepository friendRelationshipRepository;
    @Autowired CokeRepository cokeRepository;

    private SendCokeRequestDto sendCokeRequestDto;

    @Before
    public void setUp() {
        sendCokeRequestDto = new SendCokeRequestDto();
        sendCokeRequestDto.setSenderEmail("sender@gmail.com");
        sendCokeRequestDto.setReceiverEmail("receiver@gmail.com");

        // 테스트용 사용자 생성 (sender, receiver)
        Member sender = Member.builder()
                .email(sendCokeRequestDto.getSenderEmail())
                .password("1234")
                .build();
        memberRepository.save(sender);

        Member receiver = Member.builder()
                .email(sendCokeRequestDto.getReceiverEmail())
                .password("1234")
                .build();
        memberRepository.save(receiver);
    }

    @Test
    @DisplayName("존재하지 않는 수신자인 경우")
    public void sendCoke_존재하지않는_수신자_이메일() {
        // when
        sendCokeRequestDto.setReceiverEmail("존재하지않는메일.com");

        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> cokeService.sendCoke(sendCokeRequestDto));
        assertEquals("존재하지 않는 사용자입니다. 이메일 주소를 다시 확인해주세요.", exception.getMessage());
    }

    @Test
    @DisplayName("친구 관계가 아닌 경우")
    public void sendCoke_친구관계가_아닌경우() {
        // when
        // :sender와 receiver는 아직 친구를 맺지 않음

        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> cokeService.sendCoke(sendCokeRequestDto));
        assertEquals("친구 관계가 아닙니다. 친구 관계를 먼저 맺어주세요.", exception.getMessage());
    }

    @Test
    @DisplayName("하루에 두번 이상 보낼 수 없음")
    public void sendCoke_하루에_두번이상_보낼수없음() {
        // when
        // 1. sender와 receiver가 친구 관계를 맺음
        friendRelationshipRepository.save(FriendRelationship.builder()
                .senderEmail(sendCokeRequestDto.getSenderEmail())
                .receiverEmail(sendCokeRequestDto.getReceiverEmail())
                .build());
        // 2. sender가 receiver에게 이미 오늘 한번 Coke를 보냄
        SendCokeResponseDto sendCokeResponseDto = cokeService.sendCoke(sendCokeRequestDto);
        assertEquals(sendCokeRequestDto.getSenderEmail(), sendCokeResponseDto.getSenderEmail());
        assertEquals(sendCokeRequestDto.getReceiverEmail(), sendCokeResponseDto.getReceiverEmail());

        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> cokeService.sendCoke(sendCokeRequestDto));
        assertEquals("같은 친구에게 하루 최대 한 번만 Coke를 보낼 수 있습니다.", exception.getMessage());
    }


    @Test
    @DisplayName("성공적인 케이스")
    public void sendCoke_성공적인_케이스() {
        // when
        friendRelationshipRepository.save(FriendRelationship.builder()
                .senderEmail(sendCokeRequestDto.getSenderEmail())
                .receiverEmail(sendCokeRequestDto.getReceiverEmail())
                .build());

        // then
        SendCokeResponseDto sendCokeResponseDto = cokeService.sendCoke(sendCokeRequestDto);
        assertEquals(sendCokeRequestDto.getSenderEmail(), sendCokeResponseDto.getSenderEmail());
        assertEquals(sendCokeRequestDto.getReceiverEmail(), sendCokeResponseDto.getReceiverEmail());
    }








}
