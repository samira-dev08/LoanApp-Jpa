package com.company.repository;

import com.company.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p WHERE p.loan.id = ?1")
    Optional<List<Product>> getAllProductsByLoanId(Integer loanId);

    @Query("SELECT p.price FROM Product p WHERE p.loan.id = :loanId")
    List<BigDecimal> getPriceByLoanId(@Param("loanId") Integer loanId);
}
