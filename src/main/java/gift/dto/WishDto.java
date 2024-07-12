package gift.dto;

import gift.entity.Wish;

import java.util.Base64;

public class WishDto {
    private Long id;
    private final Long productId;
    private final String tokenValue;

    public WishDto(Long id, Long productId, String tokenValue) {
        this.id = id;
        this.productId = productId;
        this.tokenValue = tokenValue;
    }

    public WishDto(Long productId, String tokenValue) {
        this.productId = productId;
        this.tokenValue = tokenValue;
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

    public String getTokenValue() {
        return tokenValue;
    }

    public static class Request {
        private final Long id;
        private final Long productId;
        private final String tokenValue;

        public Request(Long id, Long productId, String tokenValue) {
            this.id = id;
            this.productId = productId;
            this.tokenValue = tokenValue;
        }

        public Long getId() {
            return id;
        }

        public Long getProductId() {
            return productId;
        }

        public String getTokenValue() {
            return tokenValue;
        }
    }
}
