package gift.repository;

import gift.dto.product.ProductWithOptionDTO;
import gift.entity.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<Option, Option.OptionId> {
    @Query("delete from Option u where u.id = :product_id")
    void deleteByProductID(@Param("product_id") int id);

    @Query("SELECT new gift.dto.product.ProductWithOptionDTO(p.id, p.name , p.price , p.imageUrl , o.id.option) FROM Product p join Option o ON p.id = o.id.id")
    Page<ProductWithOptionDTO> findAllWithOption(Pageable pageable);



}
