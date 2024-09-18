package com.example.crm_test.repository;

import com.example.crm_test.model.Contact;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactRepository extends JpaRepository<Contact, Integer>, JpaSpecificationExecutor<Contact> {

    interface Specs {

        static Specification<Contact> byNameLike(String name) {
            return ((root, query, criteriaBuilder) ->
                    name == null ? criteriaBuilder.conjunction() :
                            criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                                            "%" + name.toLowerCase() + "%"),
                                    criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                                            "%" + name.toLowerCase() + "%"))
                            ));
        }

        static Specification<Contact> byEmailLike(String email) {
            return ((root, query, criteriaBuilder) ->
                    email == null ? criteriaBuilder.conjunction() :
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                                    "%" + email.toLowerCase() + "%"));
        }
    }
}
