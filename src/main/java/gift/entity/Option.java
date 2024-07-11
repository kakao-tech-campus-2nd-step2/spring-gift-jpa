package gift.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gift.entity.compositeKey.OptionId;
import jakarta.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class Option {
    @EmbeddedId
    OptionId id;

    @ManyToOne
    Product product;

    public void setProduct(Product product){
        this.product = product;
    }

    public OptionId getId() {
        return id;
    }

    public Option(OptionId id) {
        this.id = id;
    }

    public Option() {
    }
}