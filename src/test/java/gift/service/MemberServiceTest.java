package gift.service;

import gift.product.exception.DuplicateException;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    void testNormalSignUp() {
        System.out.println("[MemberServiceTest] testNormalSignUp()");
        Member member = new Member("normal@signup.test", "1234");
        memberService.signUp(member);
    }

    @Test
    void testDuplicateEmail() {
        System.out.println("[MemberServiceTest] testDuplicateEmail()");
        Member originMember = new Member("duplicate@email.com", "1234");
        memberService.signUp(originMember);
        Assertions.assertThrows(DuplicateException.class, () -> {
            memberService.signUp(originMember);
        });
    }

    @Test
    void testNormalLogin() {
        System.out.println("[MemberServiceTest] testNormalLogin()");
        Member member = new Member("normal@login.test", "1234");
        memberService.signUp(member);
        memberService.login(member);
    }

    @Test
    void testLoginNotExistEmail() {
        System.out.println("[MemberServiceTest] testLoginNotExistEmail()");
        Member notExistMember = new Member("notexist@email.com", "1234");
        Assertions.assertThrows(LoginFailedException.class, () -> {
            memberService.login(notExistMember);
        });
    }

    @Test
    void testLoginWrongPassword() {
        System.out.println("[MemberServiceTest] testLoginWrongPassword()");
        Member member = new Member("wrong@password.test", "1234");
        memberService.signUp(member);
        Member wrongMember = new Member(member.getEmail(), "1235");
        Assertions.assertThrows(LoginFailedException.class, () -> {
            memberService.login(wrongMember);
        });
    }
}
