package gift.model;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private Long price;
    private String temperatureOption;
    private String cupOption;
    private String sizeOption;
    private String imageurl;

    public boolean equalProduct(Product product) {
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(temperatureOption, product.temperatureOption) &&
                Objects.equals(cupOption, product.cupOption) &&
                Objects.equals(sizeOption, product.sizeOption) &&
                Objects.equals(imageurl, product.imageurl);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTemperatureOption() {
        return temperatureOption;
    }

    public void setTemperatureOption(String temperatureOption) {
        this.temperatureOption = temperatureOption;
    }

    public String getCupOption() {
        return cupOption;
    }

    public void setCupOption(String cupOption) {
        this.cupOption = cupOption;
    }

    public String getSizeOption() {
        return sizeOption;
    }

    public void setSizeOption(String sizeOption) {
        this.sizeOption = sizeOption;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
