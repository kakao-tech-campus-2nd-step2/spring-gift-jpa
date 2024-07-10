package gift.service.member;

import gift.domain.member.Member;
import gift.domain.member.MemberRepository;
import gift.web.dto.MemberDto;
import gift.web.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> getMembers() {
        return memberRepository.findAll()
            .stream()
            .map(MemberDto::from)
            .collect(Collectors.toList());
    }

    public MemberDto getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .map(MemberDto::from)
            .orElseThrow(() -> new MemberNotFoundException("멤버가 엄슴다"));
    }

    public void createMember(MemberDto memberDto) {
        memberRepository.save(MemberDto.toEntity(memberDto));
    }

    public MemberDto updateMember(String email, MemberDto memberDto) {
        memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException("멤버가 없슴다."));
        Member newMember = MemberDto.toEntity(memberDto);
        memberRepository.save(newMember);
        return MemberDto.from(newMember);
    }

    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException("멤버가 없슴다."));
        memberRepository.delete(member);
    }
}