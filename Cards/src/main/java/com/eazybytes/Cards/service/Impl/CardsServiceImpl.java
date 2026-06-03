package com.eazybytes.Cards.service.Impl;

import com.eazybytes.Cards.CardsConstants.CardsConstants;
import com.eazybytes.Cards.dto.CardsDto;
import com.eazybytes.Cards.entity.Cards;
import com.eazybytes.Cards.mapper.CardsMapper;
import com.eazybytes.Cards.repsitory.CardsRepository;
import com.eazybytes.Cards.service.ICardservice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardservice {
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new com.eazybytes.cards.exception.CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        Cards newcard=createNewCard(mobileNumber);
        cardsRepository.save(newcard);

    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new com.eazybytes.cards.exception.ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        // log.info("om el account fl fetch "+String.valueOf(customerDto.getAccountDto().getAccountNumber()));


        return CardsMapper.mapToCardsDto(cards,new CardsDto());


    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new com.eazybytes.cards.exception.ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return  true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new com.eazybytes.cards.exception.ResourceNotFoundException("customer","mobileNumber",mobileNumber)
        );

        cardsRepository.deleteById(String.valueOf(cards.getCardId())); // delete by primary key
        return  true;
    }
}
