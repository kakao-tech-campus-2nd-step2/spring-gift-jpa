package gift.model;

import jakarta.validation.constraints.Min;

public class Wishlist {
	
	private Long id;
	private String productName;
	
	@Min(value = 0, message = "음수를 입력할 수 없습니다.")
	private int quantity;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
