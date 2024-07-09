package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_option")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public ProductOption(String name, Integer additionalPrice) {
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

    public void addProduct(Product product) {
        product.getOptions().add(this);
        this.product = product;
    }

    public void removeOption() {
        product.getOptions().remove(this);
        this.product = null;
    }
}
