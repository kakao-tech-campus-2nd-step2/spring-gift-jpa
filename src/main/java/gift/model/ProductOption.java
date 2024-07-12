package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_option")
public class ProductOption extends BaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "additional_price")
    private Integer additionalPrice;

    public ProductOption() {
    }

    public ProductOption(Product product, String name, Integer additionalPrice) {
        this.product = product;
        this.name = name;
        this.additionalPrice = additionalPrice;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public Integer getAdditionalPrice() {
        return additionalPrice;
    }

    public void updateOptionInfo(String name, Integer additionalPrice) {
        this.name = name;
        this.additionalPrice = additionalPrice;
    }
}
