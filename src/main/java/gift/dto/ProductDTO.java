package gift.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    public record SaveDTO(String name, int price,String imageUrl,String option){

    }

    public record WithOptionDTO(Integer id, String name,Integer price, String imageUrl, String option){
    }

}
