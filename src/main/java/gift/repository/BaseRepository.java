package gift.repository;

import gift.exception.NotFoundElementException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    default T findByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundElementException("존재하지 않는 리소스에 대한 접근입니다."));
    }
}
