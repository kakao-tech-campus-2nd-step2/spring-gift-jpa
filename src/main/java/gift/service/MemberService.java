package gift.service;

import gift.controller.member.MemberDto;
import gift.domain.Member;
import gift.exception.MemberAlreadyExistsException;
import gift.exception.MemberNotExistsException;
import gift.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> findAll() {
        return memberRepository.findAll().stream()
            .map(MemberDto::of)
            .toList();
    }

    public MemberDto find(String email) {
        Member member =  memberRepository.findByEmail(email)
        .orElseThrow(MemberNotExistsException::new);
        return MemberDto.of(member);
    }

    public MemberDto save(MemberDto member) {
        memberRepository.findByEmail(member.email()).ifPresent(p -> {
            throw new MemberAlreadyExistsException();
        });
        return MemberDto.of(memberRepository.save(member));
    }

    public MemberDto update(String email, MemberDto member) {
        memberRepository.findByEmail(email).orElseThrow(MemberNotExistsException::new);
        return MemberDto.of(memberRepository.update(email, member));
    }

    public void delete(String email) {
        memberRepository.findByEmail(email).orElseThrow(MemberNotExistsException::new);
        memberRepository.delete(email);
    }
}
