package com.company.service;

import com.company.entity.Customer;
import com.company.entity.TransactionDetails;
import com.company.enums.ActionStatus;
import com.company.enums.FinalStatus;
import com.company.enums.LeadStatus;
import com.company.exception.IdentityException;
import com.company.exception.NotFoundException;
import com.company.repository.CustomerRepository;
import com.company.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LeadService {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public void identityStatus(Integer customerId, LeadStatus status, String rejectReason) {
        Customer customer = customerRepository.getCustomerById(customerId);
        // Long transactionId = customer.getTransactionDetailsId();
        TransactionDetails transactionDetails = transactionRepository.getTransactionByCustomerId(customerId);
        transactionDetails.setCustomer(customer);
        if (status != LeadStatus.APPROVE && Objects.isNull(rejectReason)) {
            throw new RuntimeException("Reject reason can not be empty");
        } else {
            if (status == LeadStatus.APPROVE) {
                transactionDetails.setActionStatus(ActionStatus.IDENTITY_CHECK_APPROVED.toString());
            } else {
                transactionDetails.setRejectReason(rejectReason);
            }
        }
        transactionRepository.save(transactionDetails);
    }

    public void initialStatus(Integer customerId, LeadStatus status, String rejectReason) {
        //Customer customer = customerRepository.getCustomerById(customerId);
        //Long transactionId = customer.getTransactionDetailsId();
        TransactionDetails transactionDetails = transactionRepository.getTransactionByCustomerId(customerId);
        if (status != LeadStatus.APPROVE && Objects.isNull(rejectReason)) {
            throw new RuntimeException("Reject reason can not be empty");
        } else {
            if (status == LeadStatus.APPROVE) {
                transactionDetails.setActionStatus(ActionStatus.INITIAL_CHECK_APPROVED.toString());
            } else {
                transactionDetails.setRejectReason(rejectReason);
            }
            transactionRepository.save(transactionDetails);
        }
    }

    public void finalStatus(Integer customerId, LeadStatus status, String rejectReason) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer not found"));
        TransactionDetails transactionDetails = transactionRepository.getTransactionByCustomerId(customerId);
        if (status != LeadStatus.APPROVE && Objects.isNull(rejectReason)) {
            throw new RuntimeException("Reject reason can not be empty");
        } else {
            if (status == LeadStatus.APPROVE) {
                transactionDetails.setActionStatus(ActionStatus.FINAL_CHECK_APPROVED.toString());
                transactionDetails.setFinalStatus(FinalStatus.COMPLETED.toString());
            } else {
                transactionDetails.setRejectReason(rejectReason);
            }
            transactionRepository.save(transactionDetails);
        }
    }

}

