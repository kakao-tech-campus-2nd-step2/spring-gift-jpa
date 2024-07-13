package gift.dto;

import gift.entity.Product;

public class ProductDetailDTO {
    public static class Request {
        private String name;
        private String url;
        private Long price;

        public Request(String name, Long price, String url) {
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class Response {
        private String name;
        private String url;
        private Long price;

        public Response(String name, Long price, String url) {
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public static Response fromEntity(Product product) {
            return new Response(product.getName(), product.getPrice(), product.getUrl());
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }

        public String getUrl() {
            return url;
        }
    }
}
