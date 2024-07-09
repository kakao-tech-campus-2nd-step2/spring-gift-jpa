package gift.config;

public class AuthorizationHeader {
    private String authHeader;

    public AuthorizationHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    public boolean isValid() {
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            return true;
        }
        return false;
    }

    public String getToken() {
        if (isValid()) {
            return authHeader.substring(7);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return authHeader;
    }
}
