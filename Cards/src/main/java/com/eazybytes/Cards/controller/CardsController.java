package com.eazybytes.Cards.controller;

import com.eazybytes.Cards.CardsConstants.CardsConstants;
import com.eazybytes.Cards.dto.CardsDto;
import com.eazybytes.Cards.entity.Cards;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.Cards.service.ICardservice;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/api",produces={MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {
    private ICardservice iCardservice;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        iCardservice.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetchCard")
    public  ResponseEntity<CardsDto> fetchCard(@RequestParam
                                                      @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
                                                       String mobileNumber){
        CardsDto cardsDto = iCardservice.fetchCard(mobileNumber);

        return  ResponseEntity.status(HttpStatus.OK).body(cardsDto);

    }

    @DeleteMapping("/DeleteCard")
    public  ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                                    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
                                                    String mobileNumber)
    {

        boolean isDeleted = iCardservice.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_500,CardsConstants.MESSAGE_500));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = iCardservice.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

}
