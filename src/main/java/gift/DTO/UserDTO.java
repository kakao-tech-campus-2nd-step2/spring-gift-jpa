package gift.DTO;

public class UserDTO {
    String userId;
    String email;
    String password;

    public UserDTO(){

    }

    public UserDTO(String userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getUserId(){
        return userId;
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

    public void setPassword(String password){
        this.password = password;
    }
}
