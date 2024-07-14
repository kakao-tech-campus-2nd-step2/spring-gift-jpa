package gift.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static gift.constant.Message.*;

public class AddProductRequest {

    @NotEmpty(message = REQUIRED_FIELD_MSG)
    @Size(max = 15, message = LENGTH_ERROR_MSG)
    private String name;

    @NotNull(message = REQUIRED_FIELD_MSG)
    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    private int price;

    @NotEmpty(message = REQUIRED_FIELD_MSG)
    private String imageUrl;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
