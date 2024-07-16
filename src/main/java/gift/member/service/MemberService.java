package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE
    public Member createMember(String email, String password) {
        Member member = new Member(email, passwordEncoder.encode(password));
        return memberRepository.save(member);
    }

    // REGISTER
    public Member register(String email, String password) {
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        Member member = new Member(email, passwordEncoder.encode(password));
        return memberRepository.save(member);
    }

    // READ (LOGIN)
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("옳지 않은 이메일이나 비밀번호 입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("옳지 않은 이메일이나 비밀번호 입니다.");
        }

        return member;
    }

    // READ (FIND BY ID)
    public Optional<Member> findById(Long member_id) {
        return memberRepository.findById(member_id);
    }

    // UPDATE EMAIL
    public Member updateEmail(Long member_id, String newEmail) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updateEmail(newEmail);
        return memberRepository.save(member);
    }

    // 패스워드 update
    public Member updatePassword(Long member_id, String newPassword) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updatePassword(passwordEncoder.encode(newPassword));
        return memberRepository.save(member);
    }

    // 회원 삭제
    public void deleteMember(Long member_id) {
        memberRepository.deleteById(member_id);
    }
}