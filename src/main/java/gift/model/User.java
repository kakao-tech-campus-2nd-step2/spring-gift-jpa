package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class User {
	
	private Long id;
	
	@Email(message = "이메일 형식일 올바르지 않습니다.")
	@NotBlank(message = "이메일 입력은 필수 입니다.")
	private String email;
	
	@NotBlank(message = "비밀번호 입력은 필수 입니다.")
	private String password;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
