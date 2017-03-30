package com.ironyard.repositories;

import com.ironyard.data.Car;
import com.ironyard.data.CarUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by osmanidris on 2/10/17.
 */
public interface CarRepo extends CrudRepository<Car, Long>{
    public List<Car> findByOwner(CarUser carUser);
}
