package com.company.service;

import com.company.dto.request.PassportInfoRequest;
import com.company.dto.request.ProductRequest;
import com.company.entity.*;
import com.company.dto.request.LoanInfoRequest;
import com.company.dto.request.PersonalInfoRequest;
import com.company.enums.ActionStatus;
import com.company.enums.FinalStatus;
import com.company.exception.IdentityException;
import com.company.exception.InitialException;
import com.company.mapper.*;
import com.company.repository.AddressRepository;
import com.company.repository.ContactRepository;
import com.company.repository.CustomerRepository;
import com.company.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditorService {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final ProductService productService;
    private final AddressService addressService;
    private final ContactService contactService;
    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;
    private final LoanService loanService;

    public void checkIdentity(PassportInfoRequest passInfo) {
        Customer customer = customerMapper.toCustomer(passInfo);
        customerRepository.save(customer);
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .actionStatus(ActionStatus.WAITING_FOR_IDENTITY_APPROVE.toString())
                .finalStatus(FinalStatus.IN_PROGRESS.toString())
                .customer(customer)
                .build();
        transactionRepository.save(transactionDetails);
    }

    public void initialApprove(Integer customerId, PersonalInfoRequest personalInfo) {
        Customer customer = customerRepository.getCustomerById(customerId);
        TransactionDetails transactionDetails = transactionRepository.getTransactionByCustomerId(customerId);
        if (!(transactionDetails.getActionStatus().equals(ActionStatus.IDENTITY_CHECK_APPROVED.toString()))) {
            throw new IdentityException("Identity check failed!");
        }
        transactionDetails.setActionStatus(ActionStatus.WAITING_FOR_INITIAL_APPROVE.toString());
        transactionRepository.save(transactionDetails);
        Address address=addressService.create(personalInfo.getAddress());
        Contact contact=contactService.create(personalInfo.getContact());
        customer.setAddress(address);
        customer.setContact(contact);
        customerRepository.save(customer);
    }

    public void finalApprove(Integer customerId, LoanInfoRequest loanInfo) {

        Customer customer = customerRepository.getCustomerById(customerId);
        TransactionDetails transactionDetails = transactionRepository.getTransactionByCustomerId(customerId);
        if (!(transactionDetails.getActionStatus().equals(ActionStatus.INITIAL_CHECK_APPROVED.toString()))) {
            throw new InitialException("Initial check failed!");
        }
        Loan loan = loanService.create(customer, loanInfo.getInterestRate());
        for (ProductRequest productRequest : loanInfo.getProductList()) {
            Product product = productMapper.toProduct(productRequest);
            product.setLoan(loan);
            productService.create(product);
        }
        loanService.addTotalAndPreAmountToLoan(loan.getId());

        transactionDetails.setActionStatus(ActionStatus.WAITING_FOR_FINAL_APPROVE.toString());
        transactionRepository.save(transactionDetails);

    }
}
