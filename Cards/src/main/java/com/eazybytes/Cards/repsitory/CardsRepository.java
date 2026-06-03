package com.eazybytes.Cards.repsitory;

import com.eazybytes.Cards.entity.Cards;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CardsRepository extends JpaRepository<Cards,String> {

    Optional<Cards> findByMobileNumber(String mobileNumber);

    @Transactional //ya yshel kolo ya myshelh kolo
    @Modifying
    void deleteByMobileNumber(String CardNumber);

    Optional<Cards> findByCardNumber(String cardNumber);
}
