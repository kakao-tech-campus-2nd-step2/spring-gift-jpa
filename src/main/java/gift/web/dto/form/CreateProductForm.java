package gift.web.dto.form;

import jakarta.validation.constraints.NotBlank;
import java.net.URL;
import org.hibernate.validator.constraints.Range;

public class CreateProductForm {

    private String name;
    private Integer price;
    private URL imageUrl;

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

}
