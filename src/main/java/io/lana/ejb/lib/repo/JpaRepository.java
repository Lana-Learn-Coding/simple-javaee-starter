package io.lana.ejb.lib.repo;

import io.lana.ejb.lib.utils.ModelUtils;
import io.lana.ejb.lib.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class JpaRepository<T> {
    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager em;

    protected JpaRepository() {
        this.clazz = ModelUtils.getGenericType(ModelUtils.getOriginalClass(this));
    }

    public List<T> list(String condition, Object... params) {
        String jpql = buildJPQL(condition);
        TypedQuery<T> query = em().createQuery(jpql, clazz);
        addQueryParams(query, params);
        return query.getResultList();
    }

    public List<T> list() {
        TypedQuery<T> query = em().createQuery("from " + clazz.getName() + " entity", clazz);
        return query.getResultList();
    }

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
    public T save(T entity) {
        if (em().contains(entity)) {
            em().persist(entity);
            return entity;
        } else {
            return em().merge(entity);
        }
    }


    @Transactional
    public void delete(String condition, Object... params) {
        List<T> founds = list(condition, params);
        founds.forEach(entity -> em().remove(entity));
    }

    public EntityManager em() {
        return em;
    }

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
