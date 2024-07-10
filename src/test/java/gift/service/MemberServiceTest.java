package gift.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import gift.database.JdbcMemeberRepository;
import gift.dto.MemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcMemeberRepository jdbcMemeberRepository;

    @Test
    @DisplayName("기본 로그인 테스트")
    void register() {

        //given
        //email과 password를 입력하면, 계정을 생성해준다.
        //user role이 null 이면 common으로 설정한다.
        String email = "testmail";
        MemberDTO memberDTO = new MemberDTO(email, "abcd", null);

        //when
        memberService.register(memberDTO);

        //then
        assertNotNull(jdbcMemeberRepository.findByEmail(email));

    }

}