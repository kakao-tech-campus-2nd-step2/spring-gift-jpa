package gift.compositeKey;

import gift.entity.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class OptionId implements Serializable {
    int id;
    String option;


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
