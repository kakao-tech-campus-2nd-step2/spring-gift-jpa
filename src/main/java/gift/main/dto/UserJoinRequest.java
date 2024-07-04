package gift.main.dto;


public record UserJoinRequest(
        String name,
        String email,
        String password,
        String role ) {

}
