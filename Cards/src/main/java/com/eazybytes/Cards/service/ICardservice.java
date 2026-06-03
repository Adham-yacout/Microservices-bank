package com.eazybytes.Cards.service;

import com.eazybytes.Cards.dto.CardsDto;
import com.eazybytes.Cards.entity.Cards;

public interface ICardservice {

    void createCard(String mobileNumber);
    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);
    boolean deleteCard(String mobileNumber);
}
