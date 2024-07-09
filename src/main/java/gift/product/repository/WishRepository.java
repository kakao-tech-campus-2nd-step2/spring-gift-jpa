package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import java.util.List;

public interface WishRepository {

    public Wish save(Wish wish);

    public List<Wish> findAll(LoginMember loginMember);

    public Wish findById(Long id, LoginMember loginMember) throws Exception;

    public void delete(Long id);
}
