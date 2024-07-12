package gift.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MemberDTO {

    @Email(message = "? íš¨???´ë©”??ì£¼ì†Œë¥??…ë ¥?´ì£¼?¸ìš”.")
    @NotEmpty(message = "?´ë©”?¼ì? ?„ìˆ˜ ??ª©?…ë‹ˆ??")
    public String email;

    @Size(min = 6, message = "ë¹„ë?ë²ˆí˜¸??ìµœì†Œ 6???´ìƒ?´ì–´???©ë‹ˆ??")
    @NotEmpty(message = "ë¹„ë?ë²ˆí˜¸???„ìˆ˜ ??ª©?…ë‹ˆ??")
    public String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

