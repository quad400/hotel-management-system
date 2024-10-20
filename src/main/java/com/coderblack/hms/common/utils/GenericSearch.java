package com.coderblack.hms.common.utils;

import com.coderblack.hms.common.response.SearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class GenericSearch<T> {

    @Autowired
    private EntityManager entityManager;

    public <T> SearchResponse<T> search(
            String searchText,
            Class<T> entityClass,
            int page, int size,
            String[] classFields,
            String[] joinFields,
            String[] fields,
            String sortField,
            SortDirection sortDirection,
            String userId
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);

        if (searchText != null && !searchText.isEmpty()) {
            String searchWildcard = "%" + searchText.toLowerCase() + "%";

            addSearchPredicates(criteriaBuilder, predicates, root, searchWildcard, classFields);

            if (joinFields != null) {
                for (String joinField : joinFields) {
                    Join<T, ?> join = root.join(joinField, JoinType.LEFT);
                    try {
                        addSearchPredicates(criteriaBuilder, predicates, join, searchWildcard, fields);
                    } catch (IllegalArgumentException ignored) {

                    }
                }
            }
        }

        Predicate userPredicate = null;
        Predicate finalPredicate = null;
        if (userId != null) {
            userPredicate = criteriaBuilder.equal(root.get("user").get("id"), userId);
        }

        if (userPredicate != null) {
            if (!predicates.isEmpty()) {
                Predicate orPredicateGroup = criteriaBuilder.or(predicates.toArray(new Predicate[0]));

                finalPredicate = criteriaBuilder.and(userPredicate, orPredicateGroup);
            } else {
                finalPredicate = userPredicate;
            }
        } else if (!predicates.isEmpty()) {
            finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        }

        if (finalPredicate != null) {
            criteriaQuery.select(root).where(finalPredicate);
            countQuery.select(criteriaBuilder.count(countRoot)).where(finalPredicate);
        } else {
            criteriaQuery.select(root);
            countQuery.select(criteriaBuilder.count(countRoot));
        }

        if (sortField != null && !sortField.isEmpty()) {
            if (sortDirection == SortDirection.ASC) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortField)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortField)));
            }
        }
        List<T> result = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        Long totalCounts = entityManager.createQuery(criteriaQuery).getResultList().stream().count();
//        Long totalCounts = (long) 9;

        return new SearchResponse<>(result, totalCounts);
    }


    public <T> SearchResponse<T> search(
            String searchText,
            Class<T> entityClass,
            int page, int size,
            String[] classFields,
            String[] joinFields,
            String[] fields,
            String sortField,
            SortDirection sortDirection
    ) {
        return this.search(searchText, entityClass, page, size, classFields, joinFields, fields, sortField, sortDirection, null);
    }

    private void addSearchPredicates(CriteriaBuilder criteriaBuilder, List<Predicate> predicates, From<?, ?> root, String searchPattern, String... fields) {
        for (String field : fields) {
            Class<?> fieldType = root.get(field).getJavaType();

            if (String.class.equals(fieldType)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field).as(String.class)), searchPattern));
            } else if (Integer.class.equals(fieldType)) {
                try {
                    Integer searchValue = Integer.valueOf(searchPattern.replace("%", ""));
                    predicates.add(criteriaBuilder.equal(root.get(field), searchValue));
                } catch (NumberFormatException ignored) {
                }
            } else if (Enum.class.isAssignableFrom(fieldType)) {
                try {
                    String enumValue = searchPattern.replace("%", "").toUpperCase();
                    Enum<?> enumConstant = Enum.valueOf((Class<Enum>) fieldType, enumValue);
                    predicates.add(criteriaBuilder.equal(root.get(field), enumConstant));
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }
}

