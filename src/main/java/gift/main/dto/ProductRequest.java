package gift.main.dto;

import java.util.Objects;

public record ProductRequest(String name, int price, String imageUrl) {
    public ProductRequest {
        Objects.requireNonNull(name);
        Objects.requireNonNull(imageUrl);
        //입력값이 null이면 NPE를, 그렇지 않다면 입력값을 반환한다.
        //null 검사를 위헤서 사용한다.
    }
}
