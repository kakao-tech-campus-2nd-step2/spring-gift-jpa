package gift.dto;

public class UserDTO {
    public record LoginDTO(String email,String password){

    }

    public record SignUpDTO(String email,String password){
    }

    public record Token(String token){

    }
}
