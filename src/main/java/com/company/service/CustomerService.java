package com.company.service;

import com.company.dto.response.CustomerResponse;
import com.company.entity.Customer;
import com.company.entity.TransactionDetails;
import com.company.dto.request.PassportInfoRequest;
import com.company.enums.ActionStatus;
import com.company.enums.FinalStatus;
import com.company.enums.LeadStatus;
import com.company.mapper.CustomerMapper;
import com.company.repository.CustomerRepository;
import com.company.repository.TransactionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final TransactionRepository transactionRepository;

public List<CustomerResponse> allCustomers(){
   List<Customer> customers= customerRepository.findAll();
      return customers.stream()
              .map(customer -> customerMapper.toCustomerResponse(customer))
              .collect(Collectors.toList());
}


}
