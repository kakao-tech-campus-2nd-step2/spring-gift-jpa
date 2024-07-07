package gift.Login.model;

public class Product {
    private Long id;
    private String name;
    private long price;
    private String temperatureOption;
    private String cupOption;
    private String sizeOption;
    private String imageUrl;

    public Product() {}

    public Product(Long id, String name, long price, String temperatureOption, String cupOption, String sizeOption, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.temperatureOption = temperatureOption;
        this.cupOption = cupOption;
        this.sizeOption = sizeOption;
        this.imageUrl = imageUrl;
    }

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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
