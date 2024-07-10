package gift.api.member;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final MemberRepository memberRepository;

    public MemberDao(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean hasMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean hasMemberByEmailAndPassword(MemberRequest memberRequest) {
        return memberRepository.existsByEmailAndPassword(
                    memberRequest.email(), memberRequest.password());
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Long insert(MemberRequest memberRequest) {
        return memberRepository.save(new Member(
            memberRequest.email(), memberRequest.password(), memberRequest.role())).getId();
    }
}
