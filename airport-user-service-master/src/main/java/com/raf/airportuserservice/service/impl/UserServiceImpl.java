package com.raf.airportuserservice.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.raf.airportuserservice.security.SecurityConstants.SECRET;
import static com.raf.airportuserservice.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
    private AdminRepository adminRepository;
    private CreditCardRepository creditCardRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ConfirmationTokenRepository confirmationTokenRepository,
                           EmailSenderService emailSenderService, BCryptPasswordEncoder encoder,
                           AdminRepository adminRepository, CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.adminRepository = adminRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public void registerUser(RegistrationForm registrationForm) {
        User user = new User(registrationForm.getFirstName(), registrationForm.getLastName(),
                registrationForm.getEmail(), encoder.encode(registrationForm.getPassword()),
                registrationForm.getPassportNum());
        userRepository.saveAndFlush(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.saveAndFlush(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("chand312902@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8081/confirm-account?token="+confirmationToken.getConToken());
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public void confirmUserAccount(String conToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConToken(conToken);
        User user = userRepository.findByEmail(token.getUser().getEmail());
        userRepository.save(user);
    }

    @Override
    public void updateUser(RegistrationForm registrationForm, String token) {
        String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
        User user = userRepository.findByEmail(email);
        if (registrationForm.getEmail() != null) {
            user.setEmail(registrationForm.getEmail());
            //verifikacija emaila
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.saveAndFlush(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("chand312902@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8081/confirm-account?token="+confirmationToken.getConToken());
            emailSenderService.sendEmail(mailMessage);
        }
        if (registrationForm.getFirstName() != null) {
            user.setFirstName(registrationForm.getFirstName());
        }
        if (registrationForm.getLastName() != null) {
            user.setLastName(registrationForm.getLastName());
        }
        if (registrationForm.getPassword() != null) {
            user.setPassword(encoder.encode(registrationForm.getPassword()));
        }
        if (registrationForm.getPassportNum() != null) {
            user.setPassportNum(registrationForm.getPassportNum());
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public Boolean is_admin(String token) {
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

        Admin admin = adminRepository.findByUsername(username);
        if (admin != null)
            return true;
        else
            return false;
    }

    @Override
    public void addCreditCard(CreditCardForm creditCardForm, String token) {
        String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
        User user = userRepository.findByEmail(email);
        CreditCard creditCard = new CreditCard(creditCardForm.getFirstName(), creditCardForm.getLastName(),
                creditCardForm.getCardNum(), creditCardForm.getSecurityNum());
        creditCard.setUser(user);
        creditCardRepository.saveAndFlush(creditCard);
    }

    @Override
    public Long getUserId(String token) {
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
        User user = userRepository.findByEmail(username);
        return user.getId();
    }

    @Override
    public List<CreditCard> getCreditCards(String token) {
        return creditCardRepository.findByUserId(this.getUserId(token));
    }

    @Override
    public Double getDiscount(Long userId) {
        int miles = userRepository.findById(userId).get().getMiles();
        if(miles < 1000)
            return 1.0;
        else if (miles > 10000)
            return 0.8;
        else
            return 0.9;
    }
}
