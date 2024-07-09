package gift.dto;

public class CreateProduct {

    public static class Request {

        private Long id;
        private String name;
        private Long price;
        private String imageUrl;

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

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
