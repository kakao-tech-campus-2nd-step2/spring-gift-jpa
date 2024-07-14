package gift.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    // 필드 생성
    private Long id;

    @NotNull
    @Size(min = 1, max = 15, message = "상품의 이름은 1자 15자 이내로 작성해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9()\\[\\]+\\-\\/&_\\s]*$", message = "한글, 영문자, 숫자, 특수 기호 ()[],+-&/_ 만 입력 가능합니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의 후 사용 바랍니다.")
    private String name;


    @PositiveOrZero(message = "가격은 0 이상의 숫자를 입력해 주세요.")
    private int price;

    @Pattern(regexp = "^https?://.*$", message = "올바른 이미지 URL 형식으로 입력해 주세요")
    private String imageUrl;

    public ProductDTO(){}

    /**
     * id로 상품 객체 전체를 조회할 때 사용되는 생성자
     *
     * @param id 상품 고유의 ID
     * @param name 상품의 이름
     * @param price 상품의 가격
     * @param imageUrl 상품의 이미지 주소
     */
    public ProductDTO(Long id, String name, int price, String imageUrl){
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    

    // Getter 메서드들
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

    // Setter 메서드들
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}