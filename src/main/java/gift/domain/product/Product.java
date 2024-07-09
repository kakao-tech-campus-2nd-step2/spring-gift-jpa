package gift.domain.product;

public class Product {
    private Long id;
    private String name;
    private int price;
    private String imgUrl;

    public Product(Long id, String name, int price, String imgUrl) {
        checkName(name);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product(String name, int price, String imgUrl) {
        checkName(name);
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        checkName(name);
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private void checkName(String name){
        if(name.contains("카카오")){
            throw new IllegalArgumentException("카카오가 포함된 이름은 담당 MD와 협의가 필요합니다.");
        }
    }
}
