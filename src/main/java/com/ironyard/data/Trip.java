package com.ironyard.data;

import com.ironyard.dto.TempTrip;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by osmanidris on 3/15/17.
 */
@Entity
public class Trip {
    @Id
    @GeneratedValue
    private Long id;
    private Date bookingDate;
    private Date startDate;
    private Date endDate;
    private Double totalPrice;
    private String status;
    @ManyToOne
    private CarUser renter;
    @ManyToOne
    private Car car;

    public Trip() {
    }

    public Trip(TempTrip tempTrip){
        this.startDate = tempTrip.getStartDate();
        this.endDate = tempTrip.getEndDate();
        this.totalPrice = tempTrip.getTotalPrice();
        this.bookingDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CarUser getRenter() {
        return renter;
    }

    public void setRenter(CarUser renter) {
        this.renter = renter;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
