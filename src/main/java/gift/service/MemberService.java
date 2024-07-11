package gift.service;

import gift.controller.member.MemberDto;
import gift.controller.member.MemberRequest;
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

    public MemberDto findByEmail(String email) {
        Member member =  memberRepository.findByEmail(email)
        .orElseThrow(MemberNotExistsException::new);
        return MemberDto.of(member);
    }

    public MemberDto save(MemberRequest member) {
        memberRepository.findByEmail(member.email()).ifPresent(p -> {
            throw new MemberAlreadyExistsException();
        });
        return MemberDto.of(memberRepository.save(MemberRequest.toMember(member)));
    }

    public MemberDto update(Long id, String password) {
        Member foundMember = memberRepository.findById(id).orElseThrow(MemberNotExistsException::new);
        foundMember.setPassword(password);
        return MemberDto.of(memberRepository.save(foundMember));
    }

    public void deleteById(long id) {
        memberRepository.findById(id).orElseThrow(MemberNotExistsException::new);
        memberRepository.deleteById(id);
    }
}
