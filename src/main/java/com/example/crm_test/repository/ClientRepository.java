package com.example.crm_test.repository;

import com.example.crm_test.model.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

    interface Specs {

        static Specification<Client> byNameLike(String name) {
            return ((root, query, criteriaBuilder) ->
                    name == null ? criteriaBuilder.conjunction() :
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                                    "%" + name.toLowerCase() + "%"));
        }

        static Specification<Client> byDomainLike(String domain) {
            return ((root, query, criteriaBuilder) ->
                    domain == null ? criteriaBuilder.conjunction() :
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("domain")),
                                    "%" + domain.toLowerCase() + "%"));
        }
    }
}
