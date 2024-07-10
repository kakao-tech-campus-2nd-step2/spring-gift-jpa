package gift.model.repository;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T, ID> {
    default void save(final T entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default void update(final T entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default Optional<T> find(final ID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default void delete(final T entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default List<T> findAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    ;
}
