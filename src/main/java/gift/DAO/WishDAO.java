package gift.DAO;

import gift.Entity.WishEntity;
import gift.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishDAO {

    @Autowired
    private WishRepository wishRepository;

    public List<WishEntity> findAll() {
        return wishRepository.findAll();
    }

    public Optional<WishEntity> findById(Long id) {
        return wishRepository.findById(id);
    }

    public WishEntity save(WishEntity wishEntity) {
        return wishRepository.save(wishEntity);
    }

    public void deleteById(Long id) {
        wishRepository.deleteById(id);
    }
}
