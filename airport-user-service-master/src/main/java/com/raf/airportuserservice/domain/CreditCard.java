package com.raf.airportuserservice.domain;

import javax.persistence.*;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String firstName;
    private String lastName;
    private Long cardNum;
    private Integer securityNum;

    public CreditCard() {
    }

    public CreditCard(String firstName, String lastName, Long cardNum, Integer securityNum) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNum = cardNum;
        this.securityNum = securityNum;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getCardNum() {
        return cardNum;
    }

    public Integer getSecurityNum() {
        return securityNum;
    }

    public User getUser() {
        return user;
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

    public void setCardNum(Long cardNum) {
        this.cardNum = cardNum;
    }

    public void setSecurityNum(Integer securityNum) {
        this.securityNum = securityNum;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.cardNum + " " + this.securityNum;
    }
}
