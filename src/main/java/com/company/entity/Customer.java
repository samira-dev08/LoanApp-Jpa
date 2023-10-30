package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthdate;
    private String gender;
    @Column(name = "passport_number")
    private String passportNumber;
    @OneToOne
    @JoinColumn(name = "contact_id",referencedColumnName = "id")
    private Contact contact;
    @OneToOne
    @JoinColumn(name="address_id",referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "customer")
    private List<Loan> loans;
    @OneToMany(mappedBy = "customer")
    private List<TransactionDetails> transactions;

}
