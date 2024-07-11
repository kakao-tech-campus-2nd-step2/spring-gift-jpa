package gift.repository;

import gift.model.Gift;
import gift.model.GiftResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {

}
