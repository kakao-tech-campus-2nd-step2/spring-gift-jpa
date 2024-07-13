package gift.dto;

import gift.entity.Wish;

import java.util.Base64;

public class WishDto {

    private static String makeTokenFrom(Long userId) {
        return Base64.getEncoder().encodeToString(userId.toString().getBytes());
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

    public static class Response {
        private final Long id;
        private final Long productId;
        private final String tokenValue;

        public Response(Long id, Long productId, String tokenValue) {
            this.id = id;
            this.productId = productId;
            this.tokenValue = tokenValue;
        }

        public static Response fromEntity(Wish wish) {
            String token = makeTokenFrom(wish.getUserId());
            return new Response(wish.getId(), wish.getProductId(), token);
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
