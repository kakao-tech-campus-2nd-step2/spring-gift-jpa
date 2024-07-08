package gift.member.application;

import gift.exception.type.NotFoundException;
import gift.member.application.command.MemberJoinCommand;
import gift.member.application.command.MemberLoginCommand;
import gift.member.application.command.MemberUpdateCommand;
import gift.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(MemberJoinCommand command) {
        return memberRepository.join(command.toMember());
    }

    public String login(MemberLoginCommand command) {
        return memberRepository.login(command.toMember());
    }

    public void update(MemberUpdateCommand command) {
        memberRepository.findByEmail(command.email())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));

        memberRepository.update(command.toMember());
    }

    public MemberResponse findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponse::from)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream().map(MemberResponse::from).toList();
    }

    public void delete(String email) {
        memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));

        memberRepository.delete(email);
    }
}
