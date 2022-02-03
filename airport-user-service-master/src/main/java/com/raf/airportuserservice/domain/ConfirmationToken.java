package com.raf.airportuserservice.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conToken;

    @OneToOne
    private User user;

    public ConfirmationToken() {

    }

    public ConfirmationToken(User user) {
        this.user = user;
        conToken = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public String getConToken() {
        return conToken;
    }

    public User getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConToken(String confirmationToken) {
        this.conToken = confirmationToken;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
