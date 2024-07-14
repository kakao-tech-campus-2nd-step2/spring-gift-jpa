package gift.member.application;

import gift.exception.type.NotFoundException;
import gift.member.application.command.MemberEmailUpdateCommand;
import gift.member.application.command.MemberJoinCommand;
import gift.member.application.command.MemberLoginCommand;
import gift.member.application.command.MemberPasswordUpdateCommand;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import gift.wishlist.domain.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final WishlistRepository wishlistRepository;

    public MemberService(MemberRepository memberRepository, WishlistRepository wishlistRepository) {
        this.memberRepository = memberRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Transactional
    public Long join(MemberJoinCommand command) {
        return memberRepository.save(command.toMember()).getId();
    }

    public Long login(MemberLoginCommand command) {
        return memberRepository
                .findByEmailAndPassword(command.email(), command.password())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."))
                .getId();
    }

    @Transactional
    public void updateEmail(MemberEmailUpdateCommand command) {
        Member member = memberRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));

        if (memberRepository.existsByEmail(command.email()))
                throw new IllegalArgumentException("이미 사용중인 이메일입니다.");

        member.updateEmail(command.email());
    }

    @Transactional
    public void updatePassword(MemberPasswordUpdateCommand command) {
        Member member = memberRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));

        member.updatePassword(command.password());
    }

    public MemberResponse findById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponse::from)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream().map(MemberResponse::from).toList();
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));

        wishlistRepository.deleteAllByMemberId(memberId);
        memberRepository.delete(member);
    }
}
