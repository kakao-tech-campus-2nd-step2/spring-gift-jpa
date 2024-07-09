package gift.service;

import static gift.controller.member.MemberDto.of;

import gift.domain.Member;
import gift.controller.member.MemberDto;
import gift.exception.MemberAlreadyExistsException;
import gift.exception.MemberNotExistsException;
import gift.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
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
        Optional<Member> member =  memberRepository.findByEmail(email);
        member.orElseThrow(MemberNotExistsException::new);
        return of(member.get());
    }

    public MemberDto save(MemberDto member) {
        memberRepository.findByEmail(member.email()).ifPresent(p -> {
            throw new MemberAlreadyExistsException();
        });
        return of(memberRepository.save(member));
    }

    public MemberDto update(String email, MemberDto member) {
        memberRepository.findByEmail(email).orElseThrow(MemberNotExistsException::new);
        return of(memberRepository.update(email, member));
    }

    public void delete(String email) {
        memberRepository.findByEmail(email).orElseThrow(MemberNotExistsException::new);
        memberRepository.delete(email);
    }
}
