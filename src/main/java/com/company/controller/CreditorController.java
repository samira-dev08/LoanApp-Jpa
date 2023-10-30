package com.company.controller;

import com.company.dto.request.LoanInfoRequest;
import com.company.dto.request.PassportInfoRequest;
import com.company.dto.request.PersonalInfoRequest;
import com.company.service.CreditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/creditor")
@RequiredArgsConstructor
public class CreditorController {
    private final CreditorService creditorService;

    @PostMapping("/check-identity")
    public ResponseEntity<String> checkIdentity(@RequestBody PassportInfoRequest passInfo) {
        creditorService.checkIdentity(passInfo);
        return ResponseEntity.ok("Identity checked successfully.");
    }

    @PostMapping("/initial-approve")
    public ResponseEntity<String> initialApprove(@RequestParam Integer customerId, @RequestBody PersonalInfoRequest personalInfo) {
        creditorService.initialApprove(customerId,personalInfo);
        return ResponseEntity.ok("Initial approval completed");
    }

    @PostMapping("/final-approve")
    public ResponseEntity<String> finalApprove(@RequestParam Integer customerId, @RequestBody LoanInfoRequest loanInfo) {
        creditorService.finalApprove(customerId,loanInfo);
        return ResponseEntity.ok("Final approve completed.");
    }
}
