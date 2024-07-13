package gift.dto;

public class CreateProduct {

    public static class Request {

        private final Long id;
        private final String name;
        private final Long price;
        private final String imageUrl;

        public Request(Long id, String name, Long price, String imageUrl) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }

        public String getUrl() {
            return imageUrl;
        }
    }

    public static class Response {
        private Long id;
        private String name;
        private Long price;
        private String url;

        public Response(Long id, String name, Long price, String url) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public Long getId() {
            return id;
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
