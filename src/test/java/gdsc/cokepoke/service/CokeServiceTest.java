package gdsc.cokepoke.service;

import gdsc.cokepoke.dto.coke.SendCokeRequestDto;
import gdsc.cokepoke.entity.Chatroom;
import gdsc.cokepoke.entity.Coke;
import gdsc.cokepoke.repository.ChatroomRepository;
import gdsc.cokepoke.repository.CokeRepository;
import gdsc.cokepoke.repository.FriendRelationshipRepository;
import gdsc.cokepoke.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CokeServiceTest {

    @InjectMocks
    private CokeService cokeService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private FriendRelationshipRepository friendRelationshipRepository;
    @Mock
    private CokeRepository cokeRepository;
    @Mock
    private ChatroomRepository chatroomRepository;
    private SendCokeRequestDto sendCokeRequestDto;

    @Before
    public void setUp() {
        sendCokeRequestDto = new SendCokeRequestDto();
        sendCokeRequestDto.setSenderEmail("sender@gmail.com");
        sendCokeRequestDto.setReceiverEmail("receiver@gmail.com");
    }

    @Test
    public void sendCoke_존재하지않는_수신자_이메일() {
        when(memberRepository.existsByEmail(sendCokeRequestDto.getReceiverEmail())).thenReturn(false);
        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> cokeService.sendCoke(sendCokeRequestDto));
        assertEquals("존재하지 않는 사용자입니다. 이메일 주소를 다시 확인해주세요.", exception.getMessage());
    }

    @Test
    public void sendCoke_친구관계가_아닌경우() {
        when(memberRepository.existsByEmail(sendCokeRequestDto.getReceiverEmail())).thenReturn(true);
        when(friendRelationshipRepository.existsBySenderEmailAndReceiverEmail(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail())).thenReturn(false);
        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> cokeService.sendCoke(sendCokeRequestDto));
        assertEquals("친구 관계가 아닙니다. 친구 관계를 먼저 맺어주세요.", exception.getMessage());
    }

    @Test
    public void sendCoke_하루에_한번만_보낼수있음() {
        when(memberRepository.existsByEmail(sendCokeRequestDto.getReceiverEmail())).thenReturn(true);
        when(friendRelationshipRepository.existsBySenderEmailAndReceiverEmail(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail())).thenReturn(true);
        when(cokeRepository.existsBySenderAndReceiverAndCreatedDate(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail(), LocalDate.now())).thenReturn(true);
        // then
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> cokeService.sendCoke(sendCokeRequestDto));
        assertEquals("같은 친구에게 하루 최대 한 번만 Coke를 보낼 수 있습니다.", exception.getMessage());
    }

    @Test
    public void sendCoke_성공적인_케이스() {
        when(memberRepository.existsByEmail(sendCokeRequestDto.getReceiverEmail())).thenReturn(true);
        when(friendRelationshipRepository.existsBySenderEmailAndReceiverEmail(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail())).thenReturn(true);
        when(cokeRepository.existsBySenderAndReceiverAndCreatedDate(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail(), LocalDate.now())).thenReturn(false);
        when(chatroomRepository.findByFirstEmailAndSecondEmail(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail())).thenReturn(Optional.of(new Chatroom()));
        when(cokeRepository.save(any(Coke.class))).thenReturn(new Coke());

        cokeService.sendCoke(sendCokeRequestDto);

        verify(chatroomRepository, times(1)).findByFirstEmailAndSecondEmail(sendCokeRequestDto.getSenderEmail(), sendCokeRequestDto.getReceiverEmail());
        verify(cokeRepository, times(1)).save(any(Coke.class));
    }
}