package gift.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class Option {
    @Embeddable
    public static class OptionId implements Serializable {
        int id;
        String option;

        public int getId() {
            return id;
        }

        public String getOption() {
            return option;
        }

        public OptionId(int id, String option) {
            this.id = id;
            this.option = option;
        }

        public OptionId() {
        }


    }

    @EmbeddedId
    OptionId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
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