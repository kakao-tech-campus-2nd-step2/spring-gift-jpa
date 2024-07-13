package gift.dto;

public class EditProduct {

    public static class Request {

        private Long id;
        private String name;
        private Long price;
        private String url;

        public Request(Long id, String name, Long price, String url) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public Request(String name, Long price, String url) {
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
