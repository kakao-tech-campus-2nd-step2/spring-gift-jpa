package gift.domain;

import java.util.Objects;

public abstract class BaseEntity {

    protected final Long id;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity baseEntity = (BaseEntity) o;
        return Objects.equals(id, baseEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
