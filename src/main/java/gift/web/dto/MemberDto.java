package gift.web.dto;

import gift.domain.member.Member;

public record MemberDto(String email,
                        String password) { }
