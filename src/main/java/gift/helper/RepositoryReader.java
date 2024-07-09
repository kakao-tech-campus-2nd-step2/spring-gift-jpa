package gift.helper;

import gift.exception.NotFoundElementException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryReader {

    public RepositoryReader() {
    }

    public <T> T findEntityById(JpaRepository<T, Long> repository, Long id) {
        var entity = repository.findById(id);
        if (entity.isEmpty()) throw new NotFoundElementException("존재하지 않는 리소스에 대한 접근입니다.");
        return entity.get();
    }
}
