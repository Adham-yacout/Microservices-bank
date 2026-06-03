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
public class Customer extends BaseEntity {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long CustomerId;

    private String name;

    private String email;
    @Column(name="mobile_number") //not necessary here actually as we replaced _ with capital letter but this is for reference if coloumn name is different
    private String mobileNumber;

}
