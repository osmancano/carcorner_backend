package com.ironyard.controllers.rest;

/**
 * Created by osmanidris on 3/6/17.
 */


import com.ironyard.data.Trip;
import com.ironyard.repositories.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class ScheduledTask {
    @Autowired
    TripRepo tripRepo;

    @Scheduled(fixedRate = 60000)
    public void markTripAsFinished() {
        Iterable<Trip> trips = tripRepo.findAll();
        System.out.println("inside the markTripAsFinished function");
        for (Trip trip : trips) {
            Date now = new Date();
            if(now.after(trip.getEndDate()) && !trip.getStatus().equals("Completed")) {
                trip.setStatus("Completed");
                tripRepo.save(trip);
            }
        }
    }

}