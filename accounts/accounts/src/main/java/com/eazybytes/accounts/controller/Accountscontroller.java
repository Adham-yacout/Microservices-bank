package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="Accounts"
)
@RestController
@RequestMapping(path="/api",produces={MediaType.APPLICATION_JSON_VALUE})
@Validated

public class Accountscontroller {

    private final IAccountsService iAccountsService;

public Accountscontroller(IAccountsService iAccountsService){
    this.iAccountsService=iAccountsService;
}

    @Value("${build.version}")
    private  String buildVersion;

@Autowired
private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(
            summary = "Create Account REST API",
            description = "create new customer"
    )
    @ApiResponse(
            responseCode = "201",
            description = "createe succesffuly"
    )
@PostMapping("/create")
public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
    iAccountsService.createAccount(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
}
@GetMapping("/fetch")
public ResponseEntity<CustomerDto> fetchAccountDetaisls(@RequestParam
                                                        @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
                                                        String mobileNumber){
    CustomerDto customerDto= iAccountsService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
}


@PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto)
{
    boolean isUpdated = iAccountsService.updateAccount(customerDto);
    if (isUpdated){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }else{
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
}
}

@DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
                                                            String mobileNumber){
    boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
    if(isDeleted) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }
    else{
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }
}
    @Operation(
            summary = "Fetch build info",
            description = "Fetch build info"
    )
    @ApiResponse(
            responseCode = "200",
            description = "build version number"
    )
@GetMapping("/build-info")
public  ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
}


    @Operation(
            summary = "Fetch java version",
            description = "Fetch java version"
    )
    @ApiResponse(
            responseCode = "200",
            description = "java version number"
    )
    @GetMapping("/java-version")
    public  ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("java.version"));
    }


    @Operation(
            summary = "Fetch contact info ",
            description = "contact info details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "contact info"
    )
    @GetMapping("/contact-info")
    public  ResponseEntity<AccountsContactInfoDto> getcontactinfo(){
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }

}
