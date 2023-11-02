package com.company.service;

import com.company.entity.Customer;
import com.company.entity.Loan;
import com.company.entity.Product;
import com.company.exception.NotFoundException;
import com.company.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final ProductService productService;
    private static final Double PRE_AMOUNT_PERCENT = 40.0;


    public Loan create(Customer customer, BigDecimal interestRate) {
        Loan loan = Loan.builder()
                .interestRate(interestRate)
                .customer(customer)
                .build();
        return loanRepository.save(loan);
    }

    @Transactional
    public void  addTotalAndPreAmountToLoan(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Not found Loan"));
       List<BigDecimal> priceList= productService.getProductPriceByLoanId(loanId).get();
        BigDecimal totalAmount = BigDecimal.ZERO;
        //BigDecimal result=BigDecimal.ZERO;
        for (BigDecimal price : priceList) {
            totalAmount.add(price);
        }
        BigDecimal preAmount = totalAmount.multiply(BigDecimal.valueOf(PRE_AMOUNT_PERCENT / 100));
        loan.setTotalAmount(totalAmount);
        loan.setPreAmount(preAmount);
        loanRepository.save(loan);

    }
}
