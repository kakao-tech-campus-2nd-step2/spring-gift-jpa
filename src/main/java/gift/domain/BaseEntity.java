package gift.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected BaseEntity() {
    }

    abstract static class Builder<T extends Builder<T>> {

        Long id;

        public T id(Long id) {
            this.id = id;
            return self();
        }

        abstract BaseEntity build();

        protected abstract T self();
    }

    protected BaseEntity(Builder<?> builder) {
        id = builder.id;
    }

    public Long getId() {
        return id;
    }
}
