package gift.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gift.main.deserializer.ConverterRoleDeserializer;
import gift.main.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record UserJoinRequest(
        //여기서는 enum 타입의 에러를 어떻게 사용할지..?
        String name,
        @NotBlank(message = "이메일을 적어주세요.")
        String email,
        @NotBlank(message = "패스워드 입력해주세요.")
        String password,
        @JsonDeserialize(using = ConverterRoleDeserializer.class)
        Role role) {


}
