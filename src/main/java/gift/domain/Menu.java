package gift.domain;

import jakarta.persistence.*;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    public Menu() {}

    public Menu(String name, int price, String imageUrl) {
        this(null,name,price,imageUrl);
    }

    public Menu(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Menu(Long id, MenuRequest menuRequest){
        this.id = id;
        this.name = menuRequest.name();
        this.price = menuRequest.price();
        this.imageUrl = menuRequest.imageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void update(Menu menu) {
        this.id = menu.id;
        this.name = menu.name;
        this.price = menu.price;
        this.imageUrl = menu.imageUrl;
    }

    public static Menu MapMenuRequestToMenu(MenuRequest menuRequest){
        return new Menu(menuRequest.name(),menuRequest.price(), menuRequest.imageUrl());
    }

    public static MenuResponse MapMenuToMenuResponse(Menu menu){
        return new MenuResponse(menu.getId(),menu.getName(),menu.getPrice(),menu.getImageUrl());
    }

}
