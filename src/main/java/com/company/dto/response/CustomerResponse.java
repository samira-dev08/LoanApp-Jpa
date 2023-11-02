package com.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String name;
    private String surname;
    private String patronymic;
    private Date birthDate;
    private String gender;
    private String passportNumber;

}
