package gift.web.dto.response.member;

import gift.domain.Member;

public class CreateMemberResponse {

    private Long id;

    private CreateMemberResponse() {
    }

    public CreateMemberResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static CreateMemberResponse fromEntity(Member member) {
        return new CreateMemberResponse(member.getId());
    }
}
