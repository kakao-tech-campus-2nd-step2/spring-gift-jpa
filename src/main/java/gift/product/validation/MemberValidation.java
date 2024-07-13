package gift.product.validation;

import gift.product.dto.MemberDTO;
import gift.product.repository.MemberRepository;
import gift.product.exception.DuplicateException;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberValidation {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberValidation(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUpValidation(String email) {
        if(memberRepository.findByEmail(email) != null)
            throw new DuplicateException("이미 가입된 이메일입니다.");
    }

    public void loginValidation(MemberDTO memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.getEmail());
        if(member == null)
            throw new LoginFailedException("이메일을 잘못 입력하였습니다.");
        if(!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword()))
            throw new LoginFailedException("비밀번호가 틀립니다.");
    }
}
