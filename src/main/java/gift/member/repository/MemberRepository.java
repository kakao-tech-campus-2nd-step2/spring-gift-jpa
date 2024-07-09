package gift.member.repository;

import gift.member.Member;
import gift.member.dto.MemberReqDto;
import gift.member.exception.MemberNotFoundByIdException;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcClient jdbcClient;

    public MemberRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Member> findMembers() {
        var sql = """
                select id, name, email, password, role
                from member
                """;

        return jdbcClient.sql(sql)
                .query(Member.class)
                .list();
    }

    public Member findMemberByIdOrThrow(Long memberId) {
        var sql = """
                select id, name, email, password, role
                from member
                where id = ?
                """;

        return jdbcClient.sql(sql)
                .param(memberId)
                .query(Member.class)
                .optional()
                .orElseThrow(() -> MemberNotFoundByIdException.EXCEPTION);
    }

    // 일반 사용자로 회원 가입
    // 관리자 계정은 데이터베이스에서 직접 추가
    // 추후 관리자 계정을 직접 추가하는 로직을 위해 role을 파라미터로 받음
    public Long addMember(MemberReqDto member, String role) {
        var sql = """
                insert into member (name, email, password, role)
                values (?, ?, ?, ?)
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param(member.name())
                .param(member.email())
                .param(member.password())
                .param(role)
                .update(keyHolder);

        return keyHolder.getKey().longValue();
    }

    public boolean isMemberExistById(Long memberId) {
        var sql = """
                select count(*)
                from member
                where id = ?
                """;

        return jdbcClient.sql(sql)
                .param(memberId)
                .query(Long.class)
                .single() > 0L;
    }

    public boolean isMemberExistByEmail(String email) {
        var sql = """
                select count(*)
                from member
                where email = ?
                """;

        return jdbcClient.sql(sql)
                .param(email)
                .query(Long.class)
                .single() > 0L;
    }

    public void updateMemberById(Long memberId,  MemberReqDto memberReqDto) {
        var sql = """
                update member
                set name = ?, email = ?, password = ?, role = ?
                where id = ?
                """;

        jdbcClient.sql(sql)
                .param(memberReqDto.name())
                .param(memberReqDto.email())
                .param(memberReqDto.password())
                .param(memberId)
                .update();
    }

    public void deleteMemberById(Long memberId) {
        var sql = """
                delete from member
                where id = ?
                """;

        jdbcClient.sql(sql)
                .param(memberId)
                .update();
    }
}
