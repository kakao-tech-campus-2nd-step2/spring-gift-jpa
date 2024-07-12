package gift.Login.service;

import gift.Login.model.Member;

public interface MemberService {

    void registerMember(String email, String password);

    String generateToken(Member member);

    String login(String email, String password);
}
