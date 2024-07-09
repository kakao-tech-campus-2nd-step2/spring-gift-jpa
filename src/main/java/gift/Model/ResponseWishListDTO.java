package gift.Model;

public class ResponseWishListDTO {
    private String name;
    private int count;

    public ResponseWishListDTO(){}

    public ResponseWishListDTO(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
