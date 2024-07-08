package gift.config;

import gift.jwt.JwtUtil;

public class AuthToken {

    private final jwtUtil;

    public AuthToken(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

}
