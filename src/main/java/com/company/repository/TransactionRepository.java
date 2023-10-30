package com.company.repository;

import com.company.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetails,Integer> {

     TransactionDetails getTransactionByCustomerId(Integer customerId);

}
