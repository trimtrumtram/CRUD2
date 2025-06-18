package com.crudapi.crud.specification;

import com.crudapi.crud.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterProduct(
            String name,
            BigDecimal startPrice,
            BigDecimal endPrice
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(name != null && !name.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if(startPrice != null || endPrice != null) {
                predicates.add(criteriaBuilder.between(root.get("price"), startPrice, endPrice));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate [0]));
        };
    }
}
