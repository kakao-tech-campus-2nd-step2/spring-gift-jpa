package gift.member.domain;

import gift.member.application.command.MemberJoinCommand;
import gift.member.application.command.MemberLoginCommand;
import gift.member.application.command.MemberUpdateCommand;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    String login(Member member);

    String join(Member member);

    void update(Member member);

    void delete(String email);
}
