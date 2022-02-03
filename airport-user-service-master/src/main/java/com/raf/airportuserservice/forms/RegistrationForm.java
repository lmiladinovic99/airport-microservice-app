package com.raf.airportuserservice.forms;

public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passportNum;

    public RegistrationForm() {
    }

    public RegistrationForm(String firstName, String lastName, String email, String password, String passportNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passportNum = passportNum;
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
}
