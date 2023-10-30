package com.company.service;

import com.company.entity.Product;
import com.company.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(Product product) {
      return  productRepository.save(product);
    }


    public Optional<List<Product> > getProductsByLoanId(Integer loanId){
       return productRepository.getAllProductsByLoanId(loanId);
    }

    public Optional<List<BigDecimal> > getProductPriceByLoanId(Integer loanId){
        return Optional.ofNullable(productRepository.getPriceByLoanId(loanId));
    }

}
