package gdsc.cokepoke.service;

import gdsc.cokepoke.dto.member.MemberRequestDto;
import gdsc.cokepoke.dto.member.MemberResponseDto;
import gdsc.cokepoke.entity.Member;
import gdsc.cokepoke.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired AuthService authService;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() throws Exception {
        // given
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setEmail("abc@gmail.com");
        memberRequestDto.setPassword("1234");

        // when
        MemberResponseDto memberResponseDto = authService.signup(memberRequestDto);

        // then
        Member findMember = memberRepository.findByEmail(memberRequestDto.getEmail()).get();
        assertEquals(memberResponseDto.getId(), findMember.getId());
    }

    @Test(expected = RuntimeException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        MemberRequestDto memberRequestDto1 = new MemberRequestDto();
        memberRequestDto1.setEmail("abc@gmail.com");
        memberRequestDto1.setPassword("1234");

        MemberRequestDto memberRequestDto2 = new MemberRequestDto();
        memberRequestDto2.setEmail("abc@gmail.com");
        memberRequestDto2.setPassword("2345");

        // when
        authService.signup(memberRequestDto1);
        authService.signup(memberRequestDto2); // 예외가 발생해야 한다.

        // then
        fail("예외가 발생해야 한다.");
    }

}
