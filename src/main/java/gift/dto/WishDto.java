package gift.dto;

import gift.entity.Wish;

public class WishDto {
    private long id;
    private long productId;
    private String token;

    public WishDto(long id, long productId, String token) {
        this.id = id;
        this.productId = productId;
        this.token = token;
    }

    public WishDto(long productId, String token) {
        this.productId = productId;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public String getToken() {
        return token;
    }

    public static WishDto fromEntity(Wish wish) {
        return new WishDto(wish.getId(), wish.getProductId(), wish.getToken());
    }

    public static class Request {
        private long id;
        private long productId;
        private String token;

        public Request(long id, long productId, String token) {
            this.id = id;
            this.productId = productId;
            this.token = token;
        }

        public long getId() {
            return id;
        }

        public long getProductId() {
            return productId;
        }

        public String getToken() {
            return token;
        }
    }
}
