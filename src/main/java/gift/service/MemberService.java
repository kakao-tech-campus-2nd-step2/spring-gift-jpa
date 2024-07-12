package gift.service;

import gift.controller.GlobalMapper;
import gift.controller.auth.LoginResponse;
import gift.controller.member.MemberRequest;
import gift.controller.member.MemberResponse;
import gift.controller.auth.LoginRequest;
import gift.domain.Member;
import gift.exception.MemberAlreadyExistsException;
import gift.exception.MemberNotExistsException;
import gift.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(GlobalMapper::toMemberResponse)
            .toList();
    }

    public MemberResponse findById(UUID id) {
        Member member =  memberRepository.findById(id)
        .orElseThrow(MemberNotExistsException::new);
        return GlobalMapper.toMemberResponse(member);
    }

    public LoginResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotExistsException::new);
        return GlobalMapper.toLoginResponse(member);
    }

    public MemberResponse save(LoginRequest member) {
        memberRepository.findByEmail(member.email()).ifPresent(p -> {
            throw new MemberAlreadyExistsException();
        });
        return GlobalMapper.toMemberResponse(memberRepository.save(GlobalMapper.toMember(member)));
    }

    public MemberResponse update(UUID id, MemberRequest member) {
        Member target = memberRepository.findById(id).orElseThrow(MemberNotExistsException::new);
        target.setMember(member);
        return GlobalMapper.toMemberResponse(memberRepository.save(target));
    }

    public void delete(UUID id) {
        memberRepository.findById(id).orElseThrow(MemberNotExistsException::new);
        memberRepository.deleteById(id);
    }
}
