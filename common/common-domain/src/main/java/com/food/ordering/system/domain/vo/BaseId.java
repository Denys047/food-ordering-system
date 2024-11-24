package com.food.ordering.system.domain.vo;

import com.food.ordering.system.domain.exception.ValueCannotBeNullException;

import java.util.Objects;

public abstract class BaseId<T> {

    private final T value;

    protected BaseId(T value) {
        if (Objects.isNull(value)) throw new ValueCannotBeNullException();
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseId<?> baseId = (BaseId<?>) o;
        return Objects.equals(value, baseId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
