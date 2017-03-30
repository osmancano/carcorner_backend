package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ironyard.dto.TempCar;
import com.ironyard.repositories.CarMakeRepo;
import com.ironyard.repositories.CarModelRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

/**
 * Created by osmanidris on 2/10/17.
 */
@Entity
public class Car {
    @Id @GeneratedValue
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private String transmission;
    private String description;
    private Double pricePerDay;
    private String image;
    private String address;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CarComment> carComments;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL    )
    private CarUser owner;
    private Boolean isAvailable;
    private String latitude;
    private String longitude;

    public Car(){
    }
    public Car(TempCar tempCar){
        this.year = tempCar.getYear();
        this.transmission = tempCar.getTransmission();
        this.description = tempCar.getDescription();
        this.pricePerDay = tempCar.getPricePerDay();
        this.address = tempCar.getAddress();
        this.latitude = tempCar.getLatitude();
        this.longitude = tempCar.getLongitude();
        this.isAvailable = true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CarComment> getCarComments() {
        return carComments;
    }

    public void setCarComments(List<CarComment> carComments) {
        this.carComments = carComments;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public CarUser getOwner() {
        return owner;
    }

    public void setOwner(CarUser owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getLatitue() {
        return latitude;
    }

    public void setLatitue(String latitue) {
        this.latitude = latitue;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
