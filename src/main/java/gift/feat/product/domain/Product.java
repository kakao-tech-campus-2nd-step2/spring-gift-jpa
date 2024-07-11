package gift.feat.product.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Long price;
	private String imageUrl;

	protected Product() {

	}
	private Product(String name, Long price, String imageUrl) {
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public static Product of(String name, Long price, String imageUrl) {
		return new Product(name, price, imageUrl);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Product product))
			return false;
		return Objects.equals(id, product.id) && Objects.equals(name, product.name)
			&& Objects.equals(price, product.price) && Objects.equals(imageUrl, product.imageUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, imageUrl);
	}
}
