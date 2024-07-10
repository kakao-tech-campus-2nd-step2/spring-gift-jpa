package gift.product;

import java.util.concurrent.atomic.AtomicLong;

public class Product {
    private Long id;
    private String name;
    private int price;
    private String url;

    public Product(String name, int price, String url) {
        this.id = idCounter.getAndIncrement();
        this.name = name;
        this.price = price;
        this.url = url;
    }

    private static AtomicLong idCounter = new AtomicLong(1);

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}

