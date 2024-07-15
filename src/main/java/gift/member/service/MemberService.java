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

        if (!passwordEncoder.matches(password, member.password())) {
            throw new IllegalArgumentException("옳지 않은 이메일이나 비밀번호 입니다.");
        }

        return member;
    }

    // READ (FIND BY ID)
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // UPDATE EMAIL
    public Member updateEmail(Long memberId, String newEmail) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.withEmail(newEmail);
        return memberRepository.save(member);
    }

    // UPDATE PASSWORD
    public Member updatePassword(Long memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.withPassword(passwordEncoder.encode(newPassword));
        return memberRepository.save(member);
    }

    // DELETE
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}