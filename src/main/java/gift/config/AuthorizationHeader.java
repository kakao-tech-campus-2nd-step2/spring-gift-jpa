package gift.config;

public class AuthorizationHeader {
    private final String authHeader;


    public AuthorizationHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    public boolean isValid() {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    public String getToken() {
        return authHeader.substring(7);
    }

    @Override
    public String toString() {
        return authHeader;
    }
}
