package gift.domain.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;
    private MemberRole role;

}
