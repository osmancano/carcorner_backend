package com.ironyard.repositories;

import com.ironyard.data.Car;
import com.ironyard.data.CarUser;
import com.ironyard.data.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sun.security.pkcs11.wrapper.CK_C_INITIALIZE_ARGS;

import java.util.Date;
import java.util.List;

/**
 * Created by osmanidris on 3/23/17.
 */
public interface TripRepo extends CrudRepository<Trip,Long>{
    @Query("select t from Trip t where (t.car = :car and (:sDate between  t.startDate and t.endDate)) " +
            "or (t.car = :car and (:eDate between t.startDate and t.endDate))")
    public List<Trip> checkCarAvailability(@Param("car") Car car, @Param("sDate") Date sDate , @Param("eDate") Date eDate);
    public List<Trip> findByRenter(CarUser carUser);
    public List<Trip> findByCar(Car car);
    @Query("select t from Trip t where (t.car = :car and (t.status = 'Pending' or t.status = 'Confirmed')) ")
    public List<Trip> getTripRequests(@Param("car") Car car);
}
