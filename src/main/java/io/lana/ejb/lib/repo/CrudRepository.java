package io.lana.ejb.lib.repo;

import io.lana.ejb.lib.pageable.Page;
import io.lana.ejb.lib.pageable.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T entity);

    T saveAndFlush(T entity);

    void delete(T entity);

    void delete(String condition, Object... params);

    void flush();

    EntityManager em();

    Page<T> page(Pageable pageable, String condition, Object... params);

    Page<T> page(Pageable pageable);

    List<T> list(String condition, Object... params);

    List<T> list();

    Optional<T> first(String condition, Object... params);

    long count(String condition, Object... params);

    long count();

    boolean exist(String condition, Object... params);
}
