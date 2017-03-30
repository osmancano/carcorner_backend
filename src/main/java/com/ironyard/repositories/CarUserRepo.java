package com.ironyard.repositories;

import com.ironyard.data.CarUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by osmanidris on 2/10/17.
 */
public interface CarUserRepo extends CrudRepository<CarUser, Long> {
    public CarUser findByEmailAndPassword(String email, String password);
    public CarUser findByEmail(String email);
}
