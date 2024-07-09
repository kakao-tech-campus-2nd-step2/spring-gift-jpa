package gift.service.member;

import gift.domain.member.Member;
import gift.domain.member.MemberRepository;
import gift.web.dto.MemberDto;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> getMembers() {
        return List.copyOf(memberRepository.findAll()
                .stream()
                .map(MemberDto::from)
                .toList()
                );
    }

    public MemberDto getMemberByEmail(String email) {
        return MemberDto.from(memberRepository.getMemberByEmail(email));
    }

    public void createMember(MemberDto memberDto) {
        MemberDto.from(memberRepository.insertMember(MemberDto.toEntity(memberDto)));
    }

    public MemberDto updateMember(String email, MemberDto memberDto) {
        memberRepository.getMemberByEmail(email);
        Member newMember = MemberDto.toEntity(memberDto);
        memberRepository.updateMember(newMember);
        return MemberDto.from(newMember);
    }

    public void deleteMember(String email) {
        memberRepository.getMemberByEmail(email);
        memberRepository.deleteMember(email);
    }
}