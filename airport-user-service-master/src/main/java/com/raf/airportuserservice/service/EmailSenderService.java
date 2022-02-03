package com.raf.airportuserservice.service;

import com.raf.airportuserservice.domain.User;
import com.raf.airportuserservice.dto.FlightDto;
import com.raf.airportuserservice.repository.UserRepository;
import com.raf.airportuserservice.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;
    private UserRepository userRepository;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void handleCanceledFlight(String payload) {
        System.out.println(payload);
        String[] parts = payload.split(",");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        User user = userRepository.findById(Long.parseLong(parts[0])).get();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Your flight has been canceled!");
        mailMessage.setFrom("chand312902@gmail.com");
        mailMessage.setText("A flight that you purchased has been canceled, you will recieve a refund shortly!");
        this.sendEmail(mailMessage);

        ResponseEntity<FlightDto> responseEntity = UtilsMethods.getFlightDto("http://localhost:8082/flight/get/" + parts[1]);
        FlightDto flightDto = responseEntity.getBody();
        Long flightDistance = flightDto.getDistance();
        Long newDistance = user.getMiles() - flightDistance;
        userRepository.setMiles(newDistance, user.getId());
    }

    public void addMiles(String userIdFlightId) {
        String[] parts = userIdFlightId.split(",");
        Long userId = Long.parseLong(parts[0]);
        Long flightId = Long.parseLong(parts[1]);
        User user = userRepository.findById(userId).get();
        ResponseEntity<FlightDto> responseEntity = UtilsMethods.getFlightDto("http://localhost:8082/flight/get/" + flightId);
        FlightDto flightDto = responseEntity.getBody();
        Long flightDistance = flightDto.getDistance();
        Long newDistance = user.getMiles() + flightDistance;
        userRepository.setMiles(newDistance, user.getId());
    }
}
