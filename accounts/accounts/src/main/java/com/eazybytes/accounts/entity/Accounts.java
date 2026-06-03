package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor //creates constructor with all fields
@NoArgsConstructor // creates an empty constructor
public class Accounts extends BaseEntity {

    private long customerId;
    @Id
    private Long accountNumber;

    private String accountType;
    private String branchAddress;


}
