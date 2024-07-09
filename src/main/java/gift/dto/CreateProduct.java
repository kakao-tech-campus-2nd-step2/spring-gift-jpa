package gift.dto;

public class CreateProduct {

    public static class Request {

        private long id;
        private String name;
        private long price;
        private String imageUrl;

        public Request(long id, String name, long price, String imageUrl) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public long getPrice() {
            return price;
        }

        public String getUrl() {
            return imageUrl;
        }
    }

    public static class Response {
        private long id;
        private String name;
        private long price;
        private String url;

        public Response(long id, String name, long price, String url) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
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
