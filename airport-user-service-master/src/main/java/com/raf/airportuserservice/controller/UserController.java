package com.raf.airportuserservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.raf.airportuserservice.domain.Admin;
import com.raf.airportuserservice.domain.ConfirmationToken;
import com.raf.airportuserservice.domain.CreditCard;
import com.raf.airportuserservice.domain.User;
import com.raf.airportuserservice.forms.CreditCardForm;
import com.raf.airportuserservice.forms.RegistrationForm;
import com.raf.airportuserservice.repository.AdminRepository;
import com.raf.airportuserservice.repository.ConfirmationTokenRepository;
import com.raf.airportuserservice.repository.CreditCardRepository;
import com.raf.airportuserservice.repository.UserRepository;
import com.raf.airportuserservice.service.EmailSenderService;
import com.raf.airportuserservice.service.IUserService;
import com.raf.airportuserservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.raf.airportuserservice.security.SecurityConstants.*;

@RestController
//@RequestMapping("")
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationForm registrationForm) {
        try{
            userService.registerUser(registrationForm);

            return new ResponseEntity<String>("sucess register", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token")String conToken) {

        try{
            userService.confirmUserAccount(conToken);
            return new ResponseEntity<>("AccountVerified", HttpStatus.ACCEPTED);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody RegistrationForm registrationForm,
                                             @RequestHeader(value = HEADER_STRING) String token){
        try {
            userService.updateUser(registrationForm, token);
            return new ResponseEntity<String>("success update", HttpStatus.ACCEPTED);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("is_admin")
    public ResponseEntity<Boolean> isAdmin(@RequestHeader (value = HEADER_STRING) String token) {
        try {
            if (userService.is_admin(token)) {
                return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
            }
            else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("add_creditCard")
    public ResponseEntity<String> addCreditCard(@RequestBody CreditCardForm creditCardForm, @RequestHeader(value = HEADER_STRING) String token) {
        try{
            userService.addCreditCard(creditCardForm, token);
            return new ResponseEntity<>("success add credit card", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get_userId")
    public ResponseEntity<Long> getUserId(@RequestHeader(value = HEADER_STRING) String token) {
        Long userId = userService.getUserId(token);
        return new ResponseEntity(userId, HttpStatus.OK);
    }

    @GetMapping("get_creditCards")
    public ResponseEntity<List<CreditCard>> getCreditCards(@RequestHeader(value = HEADER_STRING) String token) {
        try{
            List<CreditCard> creditCardList = userService.getCreditCards(token);
            return new ResponseEntity(creditCardList, HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("discount/{userId}")
    public ResponseEntity<Double> getDiscount(@PathVariable Long userId) {
        try {
            Double discount = userService.getDiscount(userId);
            return new ResponseEntity(discount, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}