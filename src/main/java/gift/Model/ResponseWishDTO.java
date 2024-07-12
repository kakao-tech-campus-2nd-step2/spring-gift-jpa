package gift.Model;

public class ResponseWishDTO {
    private String name;
    private int count;

    public ResponseWishDTO() {
    }

    public ResponseWishDTO(String name, int count) {
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
