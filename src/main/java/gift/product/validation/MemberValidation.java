package gift.product.validation;

import gift.product.repository.MemberRepository;
import gift.product.exception.DuplicateException;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberValidation {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberValidation(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void signUpValidation(Member member) {
        if(memberRepository.findByEmail(member.getEmail()) != null)
            throw new DuplicateException("이미 가입된 이메일입니다.");
    }

    public Member loginValidation(String email) {
        Member member = memberRepository.findByEmail(email);
        if(member == null)
            throw new LoginFailedException("이메일을 잘못 입력하였습니다.");
        return member;
    }
}
