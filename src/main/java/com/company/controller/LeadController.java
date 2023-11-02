package com.company.controller;

import com.company.dto.response.CustomerResponse;
import com.company.enums.LeadStatus;
import com.company.service.CustomerService;
import com.company.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lead")
@RequiredArgsConstructor
public class LeadController {
    private final LeadService leadService;
    private final CustomerService customerService;

    @PostMapping("/identity-status/{id}")
    public void identityStatus(@PathVariable("id") Integer customerId,
                               @RequestParam LeadStatus status,
                               @RequestParam(required = false) String rejectReason) {
        leadService.identityStatus(customerId, status, rejectReason);
    }

    @PostMapping("/initial-status/{id}")
    public void initialStatus(@PathVariable("id") Integer customerId,
                              @RequestParam LeadStatus status,
                              @RequestParam(required = false) String rejectReason) {
        leadService.initialStatus(customerId, status, rejectReason);

    }

    @PostMapping("/final-status/{id}")
    public void finalStatus(@PathVariable("id") Integer customerId,
                            @RequestParam LeadStatus status,
                            @RequestParam(required = false) String rejectReason) {
        leadService.finalStatus(customerId, status, rejectReason);
    }
    @GetMapping("/customers")
    public List<CustomerResponse> listCustomers(){
    return customerService.allCustomers();
    }
}
