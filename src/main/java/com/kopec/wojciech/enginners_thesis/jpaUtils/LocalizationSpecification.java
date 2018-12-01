package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LocalizationSpecification implements Specification<Localization> {
    private LocalizationSearch criteria;


    @Nullable
    @Override
    public Predicate toPredicate(Root<Localization> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
