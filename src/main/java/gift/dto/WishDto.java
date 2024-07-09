package gift.dto;

import gift.entity.Wish;

import java.util.Base64;

public class WishDto {
    private Long id;
    private Long productId;
    private String token;

    public WishDto(Long id, Long productId, String token) {
        this.id = id;
        this.productId = productId;
        this.token = token;
    }

    public WishDto(Long productId, String token) {
        this.productId = productId;
        this.token = token;
    }

    public static WishDto fromEntity(Wish wish) {

        String token = makeTokenFrom(wish.getUserId());

        return new WishDto(wish.getId(), wish.getProductId(), token);
    }

    private static String makeTokenFrom(Long userId) {
        return Base64.getEncoder().encodeToString(userId.toString().getBytes());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getToken() {
        return token;
    }

    public static class Request {
        private Long id;
        private Long productId;
        private String token;

        public Request(Long id, Long productId, String token) {
            this.id = id;
            this.productId = productId;
            this.token = token;
        }

        public Long getId() {
            return id;
        }

        public Long getProductId() {
            return productId;
        }

        public String getToken() {
            return token;
        }
    }
}
