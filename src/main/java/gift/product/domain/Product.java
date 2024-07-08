package gift.product.domain;

import gift.product.dto.ProductRequestDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public class Product {
    @Id
    private Long id;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private ProductName name;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private ProductPrice price;
    private String imageUrl;

    // JDBC 에서 엔티티 클래스를 인스턴스화할 때 반드시 기본 생성자와 파라미터 생성자가 필요하다
    public Product() {}

    public Product(Long id, ProductName name, ProductPrice price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, ProductRequestDto productRequestDto) {
        this.id = id;
        this.name = productRequestDto.name();
        this.price = productRequestDto.price();
        this.imageUrl = productRequestDto.imageUrl();
    }


    public Long getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean checkNew() {
        return id == null;
    }
}
