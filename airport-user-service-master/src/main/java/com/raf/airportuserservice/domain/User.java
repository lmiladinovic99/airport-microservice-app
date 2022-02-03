package com.raf.airportuserservice.domain;

import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passportNum;
    private int miles;

    public User() {

    }

    public User(String firstName, String lastName, String email, String password, String passportNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passportNum = passportNum;
        this.miles = 0;
    }

    public String getRank() {
        if (miles > 10000) {
            return "gold";
        }
        else if (miles > 1000) {
            return "silver";
        }
        else {
            return "bronze";
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public int getMiles() {
        return miles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passportNum='" + passportNum + '\'' +
                ", miles=" + miles +
                '}';
    }
}
