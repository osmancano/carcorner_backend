package com.ironyard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by osmanidris on 3/21/17.
 */
public class TempCar {
    private Long id;
    private Boolean isAvailable;
    private Long make;
    private Long model;
    private Integer year;
    private String transmission;
    private String description;
    private Double pricePerDay;
    private String address;
    private Long ownerId;
    private String latitude;
    private String longitude;

    public TempCar(){}

    public TempCar(Long make, Long model, Double pricePerDay, Long ownerId){
        this.make = make;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMake() {
        return make;
    }

    public void setMake(Long make) {
        this.make = make;
    }

    public Long getModel() {
        return model;
    }

    public void setModel(Long model) {
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

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitue(String latitue) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
