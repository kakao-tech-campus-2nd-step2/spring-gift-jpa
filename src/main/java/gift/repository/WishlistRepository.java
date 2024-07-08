package gift.repository;

import gift.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    public List<WishlistItem> findListByUserId(long userId);

}
