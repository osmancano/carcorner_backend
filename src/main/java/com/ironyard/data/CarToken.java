package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import io.swagger.models.auth.In;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wailm.yousif on 2/21/17.
 */

@Entity
public class CarToken
{
    @Id
    @GeneratedValue
    private long id;

    private String token;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId=true)
    private CarUser carUser;

    public CarToken() { }

    public CarToken(String token, CarUser chatUser) {
        this.token = token;
        this.carUser = chatUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CarUser getCarUser() {
        return carUser;
    }

    public void setCarUser(CarUser carUser) {
        this.carUser = carUser;
    }
}