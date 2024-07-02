package gift.web.dto.form;

import java.net.URL;

public class CreateProductFormDto {

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
