package gift.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserJoinRequest(
        @JsonProperty("joinName")String name,
        @JsonProperty("joiEmail")String email,
        @JsonProperty("joinPassword")String password,
        String role ) {

}
