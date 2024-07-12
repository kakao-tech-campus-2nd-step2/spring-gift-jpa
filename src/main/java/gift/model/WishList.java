package gift.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WishList {

    private Long id;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("product_price")
    private int price;

    public WishList() {}

    public WishList(Long id, Long memberId, Long productId, String name, int price) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
