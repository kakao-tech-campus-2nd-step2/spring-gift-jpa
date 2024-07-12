package gift.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class MemberDTO {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;

    public MemberDTO(Long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberDTO getMemberDTO(Member member){
        return new MemberDTO(member.getId(), member.getEmail(), member.getPassword());
    }
}
