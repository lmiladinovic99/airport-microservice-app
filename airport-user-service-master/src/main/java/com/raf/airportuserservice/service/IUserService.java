package com.raf.airportuserservice.service;

import com.raf.airportuserservice.domain.CreditCard;
import com.raf.airportuserservice.domain.User;
import com.raf.airportuserservice.forms.CreditCardForm;
import com.raf.airportuserservice.forms.RegistrationForm;

import java.util.List;

public interface IUserService {
    void registerUser(RegistrationForm registrationForm);
    void confirmUserAccount(String conToken);
    void updateUser(RegistrationForm registrationForm, String token);
    Boolean is_admin(String token);
    void addCreditCard(CreditCardForm creditCardForm, String token);
    Long getUserId(String token);
    List<CreditCard> getCreditCards(String token);
    Double getDiscount(Long userId);
}
