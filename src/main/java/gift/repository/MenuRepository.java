package gift.repository;

import gift.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long>{
    Optional<Menu> findById(Long id);
    Page<Menu> findAll(Pageable pageable);
}
