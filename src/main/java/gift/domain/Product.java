package gift.domain;

public record Product(Long id, String name, Double price, String imageUrl) {

    public Product(String name, Double price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

}

