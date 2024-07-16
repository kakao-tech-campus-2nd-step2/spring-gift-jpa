package gift.service;

import gift.controller.GlobalMapper;
import gift.controller.auth.LoginRequest;
import gift.controller.member.MemberRequest;
import gift.controller.member.MemberResponse;
import gift.domain.Member;
import gift.exception.MemberAlreadyExistsException;
import gift.exception.MemberNotExistsException;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public MemberService(MemberRepository memberRepository, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Page<MemberResponse> findAll(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        List<MemberResponse> memberResponses = memberPage.stream()
            .map(GlobalMapper::toMemberResponse).toList();
        return new PageImpl<>(memberResponses, pageable, memberPage.getTotalElements());
    }

    public MemberResponse findById(UUID id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotExistsException::new);
        return GlobalMapper.toMemberResponse(member);
    }

    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotExistsException::new);
        return GlobalMapper.toMemberResponse(member);
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
