package com.ironyard.controllers.rest;

import com.ironyard.data.*;
import com.ironyard.dto.TempCar;
import com.ironyard.dto.TempTrip;
import com.ironyard.repositories.CarRepo;
import com.ironyard.repositories.CarUserRepo;
import com.ironyard.repositories.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osmanidris on 3/23/17.
 */
@RestController
public class RestTrips {
    @Autowired
    TripRepo tripRepo;
    @Autowired
    CarRepo carRepo;
    @Autowired
    CarUserRepo carUserRepo;
    @Autowired
    private JavaMailSender javaMailService;

    @RequestMapping(path = "/open/trips/check", method = RequestMethod.POST)
    public TempTrip checkTrip(@NotNull @RequestBody TempTrip tempTrip) throws Exception{
        Car car = carRepo.findOne(tempTrip.getCarID());
        List<Trip> foundTrip = tripRepo.checkCarAvailability(car, tempTrip.getStartDate(),tempTrip.getEndDate());
        if(foundTrip.size() > 0 || !car.getIsAvailable()){
            tempTrip.setStatus("NotAvailable");
        }else{
            tempTrip.setStatus("Pending");
        }
        return tempTrip;
    }

    @RequestMapping(path = "/secure/trips/book", method = RequestMethod.POST)
    public Trip bookTrip(@RequestBody TempTrip tempTrip) {
        Trip trip = new Trip(tempTrip);
        Car car = carRepo.findOne(tempTrip.getCarID());
        CarUser carUser = carUserRepo.findOne(tempTrip.getUserID());
        List<Trip> foundTrip = tripRepo.checkCarAvailability(car, tempTrip.getStartDate(),tempTrip.getEndDate());
        if(foundTrip.size() > 0 || !car.getIsAvailable()){
            trip.setStatus("NotAvailable");
        }else{
            trip.setRenter(carUser);
            trip.setCar(car);
            trip.setStatus("Pending");
            tripRepo.save(trip);
            String message = "You have pending trip for your confirmation \n"+
                    "Trip details:\n"+
                    "Start date:"+trip.getStartDate().toString()+
                    "\nEnd date:"+trip.getEndDate().toString()+
                    "\nCar:"+trip.getCar().getMake()+","+trip.getCar().getModel()+
                    "\nRequester phone:"+carUser.getPhone()+
                    "\n\n\nBest Regards";
            sendMail("Car Corner", trip.getCar().getOwner(),message);
        }
        return trip;
    }

    @RequestMapping(path = "/secure/trips/{tripid}/confirm", method = RequestMethod.GET)
    public Trip confirmTrip(@PathVariable Long tripid) {
        Trip trip = tripRepo.findOne(tripid);
        trip.setStatus("Confirmed");
        tripRepo.save(trip);
        String message = "Your trip is confirmed \n"+
                "Trip details:\n"+
                "Start date:"+trip.getStartDate().toString()+
                "\nEnd date:"+trip.getEndDate().toString()+
                "\nCar:"+trip.getCar().getMake()+","+trip.getCar().getModel()+
                "\nOwer phone:"+trip.getCar().getOwner().getPhone()+
                "\n\n\nBest Regards";
        sendMail("Car Corner", trip.getRenter(),message);
        return trip;
    }

    @RequestMapping(path = "/secure/trips/{tripid}/reject", method = RequestMethod.GET)
    public Trip rejectTrip(@PathVariable Long tripid) {
        Trip trip = tripRepo.findOne(tripid);
        tripRepo.delete(trip);
        String message = "Sorry, trip is rejected by Car owner.\n"+
                "\n\n\nBest Regards";
        sendMail("Car Corner", trip.getCar().getOwner(),message);
        return trip;
    }

    @RequestMapping(path = "/secure/trips/{tripid}/cancel", method = RequestMethod.GET)
    public Trip cancelTrip(@PathVariable Long tripid) {
        Trip trip = tripRepo.findOne(tripid);
        tripRepo.delete(trip);
        return trip;
    }

    @RequestMapping(path = "/open/trips/{id}", method = RequestMethod.GET)
    public Trip getTrip(@PathVariable Long id) {
        Trip trip = tripRepo.findOne(id);
        return trip;
    }

    @RequestMapping(path = "/open/users/{id}/trips", method = RequestMethod.GET)
    public List<Trip> getUserTrips(@PathVariable Long id) {
        CarUser carUser = carUserRepo.findOne(id);
        List<Trip> trips = tripRepo.findByRenter(carUser);
        return trips;
    }

    @RequestMapping(path = "/open/cars/{id}/trips", method = RequestMethod.GET)
    public List<Trip> getCarTrips(@PathVariable Long id) {
        Car car = carRepo.findOne(id);
        List<Trip> trips = tripRepo.findByCar(car);
        return trips;
    }

    @RequestMapping(path = "/open/cars/{id}/triprequests", method = RequestMethod.GET)
    public List<Trip> getCarTripRequests(@PathVariable Long id) {
        Car car = carRepo.findOne(id);
        List<Trip> trips = tripRepo.getTripRequests(car);
        return trips;
    }

    public void sendMail(String subject, CarUser carUser, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(carUser.getEmail());
        mailMessage.setText("Hello " + carUser.getFirstname() + ",\n" +
                message);
        javaMailService.send(mailMessage);
    }
}
