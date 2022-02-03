package com.raf.airportuserservice.forms;

public class CreditCardForm {
    private String firstName;
    private String lastName;
    private Long cardNum;
    private Integer securityNum;

    public CreditCardForm() {
    }

    public CreditCardForm(String firstName, String lastName, Long cardNum, Integer securityNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNum = cardNum;
        this.securityNum = securityNum;
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
}
