package gift.repository;

import gift.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishProduct, Long> {
    /*
     * DB에 위시리스트 내용을 입력받아 저장
     */
    WishProduct save(WishProduct wishProduct);
    /*
     * DB에 저장된 모든 위시리스트 내용을 반환
     */
    List<WishProduct> findAll();
    /*
     * DB에서 userId 열을 기준으로 위시리스트를 반환
     */
    List<WishProduct> findByUserId(Long userId);
    /*
     * DB에 특정한 wishProduct가 있는지 확인
     */
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    /*
     * DB에서 userId와 productId를 기준으로 위시리스트 항목을 가져옴
     */
    WishProduct findByUserIdAndProductId(Long userId, Long productId);
    /*
     * DB에서 userId와 productId 열을 기준으로 위시리스트 내용을 삭제
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
