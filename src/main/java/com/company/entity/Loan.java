package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "pre_amount")
    private BigDecimal preAmount;
    @Column(name = "interest_rate")
    private BigDecimal interestRate;
    @OneToMany(mappedBy = "loan")
    private List<Product> productList;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}

