package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name="customer",
        description = " schema to hold customers ,loans and account information"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "name can't be null or empty")
    @Size(min=5 , max =30 , message ="the length of the customer name should be between 5 and 30")
    private String name;
    @NotEmpty (message = "email can't be null or empty")
    @Email(message="Email address should be a valid value")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String mobileNumber;
    private AccountDto accountDto;

    private CardsDto cardsDto;
    private LoansDto loansDto;

}
