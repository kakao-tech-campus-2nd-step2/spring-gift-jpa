package gift.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {
    @NotBlank(message = "기존 비밀번호는 필수 항목입니다.")
    private String oldPassword;

    @NotBlank(message = "새로운 비밀번호는 필수 항목입니다.")
    @Size(min = 4, message = "비밀번호는 4자리 이상 입력해주세요.")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String password) {
        this.oldPassword = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
