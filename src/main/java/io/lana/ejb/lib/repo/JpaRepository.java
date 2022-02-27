package io.lana.ejb.lib.repo;


import io.lana.ejb.lib.pageable.Page;
import io.lana.ejb.lib.pageable.Pageable;
import io.lana.ejb.lib.utils.ModelUtils;
import io.lana.ejb.lib.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class JpaRepository<T> implements CrudRepository<T> {
    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager em;

    protected JpaRepository() {
        this.clazz = ModelUtils.getGenericType(ModelUtils.getOriginalClass(this));
    }

    @Override
    public List<T> list(String condition, Object... params) {
        String jpql = buildJPQL(condition);
        TypedQuery<T> query = em().createQuery(jpql, clazz);
        addQueryParams(query, params);
        return query.getResultList();
    }

    @Override
    public List<T> list() {
        TypedQuery<T> query = em().createQuery("from " + clazz.getName() + " entity", clazz);
        return query.getResultList();
    }

    @Override
    public Optional<T> first(String condition, Object... params) {
        String jpql = buildJPQL(condition);
        TypedQuery<T> query = em().createQuery(jpql, clazz);
        addQueryParams(query, params);
        query.setMaxResults(1);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException ignored) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public T save(T entity) {
        if (em().contains(entity)) {
            em().persist(entity);
            return entity;
        } else {
            return em().merge(entity);
        }
    }

    @Transactional
    @Override
    public T saveAndFlush(T entity) {
        T saved = save(entity);
        flush();
        return saved;
    }

    @Transactional
    @Override
    public void flush() {
        em().flush();
    }

    @Transactional
    @Override
    public void delete(T entity) {
        Object id = ModelUtils.getId(entity).orElseThrow(() -> new IllegalArgumentException("Entity don't have @Id field"));
        T existing = em().find(clazz, id);
        if (existing == null) return;

        T toRemove = em().contains(entity) ? entity : em().merge(entity);
        em().remove(toRemove);
    }


    @Transactional
    @Override
    public void delete(String condition, Object... params) {
        List<T> founds = list(condition, params);
        founds.forEach(entity -> em().remove(entity));
    }

    @Override
    public EntityManager em() {
        return em;
    }

    @Override
    public Page<T> page(Pageable pageable, String condition, Object... params) {
        long count = count(condition, params);
        if (count == 0) {
            return Page.empty(pageable);
        }
        if (StringUtils.isNotBlank(pageable.getSort())) condition += " order by " + pageable.getSort();

        String jpql = buildJPQL(condition);
        TypedQuery<T> query = em().createQuery(jpql, clazz);
        addQueryParams(query, params);
        query.setMaxResults(pageable.getSize());
        query.setFirstResult((pageable.getPage() - 1) * pageable.getSize());

        return Page.from(query.getResultList(), pageable, count);
    }

    @Override
    public Page<T> page(Pageable pageable) {
        long count = count();
        if (count == 0) {
            return Page.empty(pageable);
        }

        String condition = "where (1 = 1)";
        if (StringUtils.isNotBlank(pageable.getSort())) condition += " order by " + pageable.getSort();

        String jpql = buildJPQL(condition);
        TypedQuery<T> query = em().createQuery(jpql, clazz);
        query.setMaxResults(pageable.getSize());
        query.setFirstResult((pageable.getPage() - 1) * pageable.getSize());

        return Page.from(query.getResultList(), pageable, count);
    }

    @Override
    public long count(String condition, Object... params) {
        String jpql = buildCount(condition);
        TypedQuery<Long> query = em().createQuery(jpql, Long.class);
        query.setMaxResults(1);
        addQueryParams(query, params);
        return Optional.ofNullable(query.getSingleResult()).orElse(0L);
    }


    private String buildCount(String jpql) {
        if (StringUtils.startsWith(jpql, "select")) {
            jpql = jpql.replaceFirst("(select).+?(?=from)", "");
        }
        return "select count(it) " + buildJPQL(jpql);
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em().createQuery("select count(it) from " + clazz.getSimpleName() + " it", Long.class);
        query.setMaxResults(1);
        return Optional.ofNullable(query.getSingleResult()).orElse(0L);
    }

    @Override
    public boolean exist(String condition, Object... params) {
        return first(condition, params).isPresent();
    }

    private void addQueryParams(TypedQuery<?> query, Object... params) {
        int i = 0;
        for (Object param : params) {
            i++;
            query.setParameter(i, param);
        }
    }

    private String buildJPQL(String jpql) {
        if (StringUtils.startsWithAny(jpql, "select", "from")) {
            return jpql;
        }
        String built = "from " + clazz.getSimpleName() + " it";
        if (StringUtils.isBlank(jpql)) return built;
        if (StringUtils.startsWith(jpql, "where")) return built + " " + jpql;
        return built + " where " + jpql;
    }
}
