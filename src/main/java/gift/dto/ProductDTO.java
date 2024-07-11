package gift.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    public static class SaveDTO {
        String name;
        int price;
        String imageUrl;
        String option;

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getOption() {
            return option;
        }

        public SaveDTO(String name, int price, String imageUrl, String option) {
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
            this.option = option;
        }
    }

    public static class WithOptionDTO {
        Integer id;
        String name;
        Integer price;
        String imageUrl;
        String option;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getOption() {
            return option;
        }

        public WithOptionDTO(Integer id, String name, Integer price, String imageUrl, String option) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
            this.option = option;
        }
    }

}
