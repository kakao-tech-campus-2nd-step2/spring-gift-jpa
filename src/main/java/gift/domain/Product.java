package gift.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Product {

    private Product() {
    }

    public static class CreateProduct {

        @NotNull(message = "name은 필수 입니다.")
        @Size(max = 15, message = "value는 15자 이상 초과 할 수 없습니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s\\(\\)\\[\\]\\+\\-\\&\\/_]*$",
            message = "특수 문자는 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다.")
        private String name;
        @NotNull(message = "price은 필수 입니다.")
        private Integer price;
        @NotNull(message = "imageUrl은 필수 입니다.")
        private String imageUrl;

        public CreateProduct(String name, Integer price, String imageUrl) {
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public Integer getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    public static class UpdateProduct {

        @NotNull(message = "name은 필수 입니다.")
        @Size(max = 15, message = "value는 15자 이상 초과 할 수 없습니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s\\(\\)\\[\\]\\+\\-\\&\\/_]*$",
            message = "특수 문자는 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다.")
        private String name;
        @NotNull(message = "price은 필수 입니다.")
        private Integer price;
        @NotNull(message = "imageUrl은 필수 입니다.")
        private String imageUrl;

        public UpdateProduct(String name, Integer price, String imageUrl) {
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public Integer getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    public static class ProductSimple {

        private Long id;
        private String name;

        public ProductSimple(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
