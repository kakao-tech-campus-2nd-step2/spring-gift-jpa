package gift.product;

public class ProductModel {
    public long id; // 상품의 고유 식별자
    public String name; // 상품 이름
    public int price;
    public String imgUrl; // 상품 이미지 URL

    // 생성자
    public ProductModel(long id, String name, int price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // 기본 생성자 : 매개변수 없이 호출될 수 있게 한다.
    public ProductModel() {
    }

    // 필드 접근 메서드
    public long id() {
        return id;
    }

    public void id(long id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public int price() {
        return price;
    }

    public void price(int price) {
        this.price = price;
    }

    public String imgUrl() {
        return imgUrl;
    }

    public void imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}