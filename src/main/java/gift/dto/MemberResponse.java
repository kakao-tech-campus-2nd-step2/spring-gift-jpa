package gift.dto;

import java.util.List;

public class MemberResponse {
    private Long id;
    private String email;
    private String token;
    private List<WishResponse> wishes;

    public MemberResponse(String token) {
        this.token = token;
    }

    public MemberResponse(Long id, String email, List<WishResponse> wishes) {
        this.id = id;
        this.email = email;
        this.wishes = wishes;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public List<WishResponse> getWishes() {
        return wishes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setWishes(List<WishResponse> wishes) {
        this.wishes = wishes;
    }
}
