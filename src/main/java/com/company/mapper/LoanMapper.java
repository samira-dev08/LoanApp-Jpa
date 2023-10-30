package com.company.mapper;

import com.company.entity.Loan;
import com.company.dto.request.LoanInfoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toLoan(LoanInfoRequest loanInfo);
}
