package gift.dto;

public class Wishlist {

    public static class Request {

        private String productName;
        private int quantity;

        public Request(){}

        public Request(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
        }

        public String getProductName() {
            return this.productName;
        }

        public int getQuantity() {
            return this.quantity;
        }

    }

    public static class Response {

        private String productName;
        private int quantity;

        public Response(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
        }

        public String getProductName() {
            return this.productName;
        }

        public int getQuantity() {
            return this.quantity;
        }
    }
}
