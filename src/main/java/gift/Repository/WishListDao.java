package gift.Repository;

import gift.DTO.WishListDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListDao extends JpaRepository<WishListDto, Long> {

  WishListDto save(WishListDto entity);

  Optional<WishListDto> findById(Long id);

  List<WishListDto> findAll();

  void deleteById(Long id);

}
