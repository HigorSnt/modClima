package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Farm;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    private final EntityManager entityManager;

    public CustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Farm> list(String name, String code) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery = cb.createQuery(Farm.class);
        Root<Farm> root = criteriaQuery.from(Farm.class);

        List<Predicate> predicates = new LinkedList<>();

        criteriaQuery.select(root);

        if (!name.isEmpty()) {
            Predicate predicate = cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );

            predicates.add(predicate);
        }

        if (!code.isEmpty()) {
            Predicate predicate = cb.like(
                    cb.lower(root.get("code")),
                    "%" + code.toLowerCase() + "%"
            );

            predicates.add(predicate);
        }

        criteriaQuery.where(cb.and(predicates.toArray(Predicate[]::new)));

        TypedQuery<Farm> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
