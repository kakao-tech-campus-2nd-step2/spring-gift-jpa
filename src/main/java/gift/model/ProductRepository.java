package gift.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    default void validateKaKaoName(String name) {
        if (name.contains("카카오") || name.equalsIgnoreCase("kakao")) {
            throw new IllegalArgumentException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에 사용 가능합니다.");
        }
    }
}
