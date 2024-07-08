package gift.service;

import gift.exception.LoginErrorException;
import gift.exception.MemberException;
import gift.model.Member;
import gift.repository.MemberDao;
import gift.repository.MemberRepository;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member join(String email, String password) {
        try {
            return memberRepository.save(new Member(email, password));
        } catch (DataIntegrityViolationException e) {
            throw new MemberException("중복된 이메일의 회원이 이미 존재합니다.");
        }

    }

    public Member login(String email, String password) {

        return memberRepository.findByEmail(email)
            .filter(member -> member.login(email, password))
            .orElseThrow(() -> new LoginErrorException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }

}
