package com.ironyard.controllers.rest;

import com.ironyard.data.*;
import com.ironyard.dto.TempCar;
import com.ironyard.dto.TempComment;
import com.ironyard.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osmanidris on 3/18/17.
 */
@RestController
public class RestCars {
    @Autowired
    CarUserRepo carUserRepo;
    @Autowired
    CarMakeRepo carMakeRepo;
    @Autowired
    CarRepo carRepo;
    @Autowired
    CarCommentRepo carCommentRepo;
    @Autowired
    CarModelRepo carModelRepo;

    @Value("${upload.location}")
    private String uploadLocation;

    @RequestMapping(path = "/open/makes", method = RequestMethod.GET)
    public List<CarMake> getMakes() {
        ArrayList<CarMake> makes = (ArrayList<CarMake>)carMakeRepo.findAll();
        return makes;
    }

    @RequestMapping(path = "/open/makes/{id}/models", method = RequestMethod.GET)
    public List<CarModel> getModels(@PathVariable Long id) {
        CarMake carMake = carMakeRepo.findOne(id);
        List<CarModel> models = carMake.getModels();
        return models;
    }


    @RequestMapping(path = "/secure/cars/add", method = RequestMethod.POST)
    public Car addCar(@RequestBody TempCar tempCar) throws Exception {
        CarUser carUser = carUserRepo.findOne(tempCar.getOwnerId());
        Car car = new Car(tempCar);
        if(carUser != null) {
            car.setMake(carMakeRepo.findOne(tempCar.getMake()).getName());
            car.setModel(carModelRepo.findOne(tempCar.getModel()).getName());
            car.setOwner(carUser);
            carRepo.save(car);
        }
        return car;
    }

    @RequestMapping(path = "/secure/cars/update", method = RequestMethod.POST)
    public Car updateCar(@RequestBody TempCar tempCar) throws Exception {
        System.out.println("Id is:"+tempCar.getIsAvailable());
        Car car = carRepo.findOne(tempCar.getId());
        car.setAddress(tempCar.getAddress());
        car.setPricePerDay(tempCar.getPricePerDay());
        car.setIsAvailable(tempCar.getIsAvailable());
        carRepo.save(car);
        return car;
    }

    @RequestMapping(path = "/open/users/{id}/cars", method = RequestMethod.GET)
    public List<Car> getMyCars(@PathVariable Long id) {
        CarUser carUser = carUserRepo.findOne(id);
        List<Car> myCars = carRepo.findByOwner(carUser);
        return myCars;
    }

    @RequestMapping(path = "/open/cars", method = RequestMethod.GET)
    public List<Car> getCars() {
        ArrayList<Car> cars = (ArrayList<Car>)carRepo.findAll();
        return cars;
    }
    @RequestMapping(path = "/open/cars/{id}", method = RequestMethod.GET)
    public Car getCar(@PathVariable Long id) throws Exception {
        Car car = carRepo.findOne(id);
        return car;
    }
    @RequestMapping(path = "/secure/cars/{id}/comments", method = RequestMethod.POST)
    public Car setCarComment(@PathVariable Long id, @RequestBody TempComment tmpComment) throws Exception {
        Car car = carRepo.findOne(id);
        CarUser carUser = carUserRepo.findOne(tmpComment.getUserId());
        CarComment comment = new CarComment(tmpComment.getRating(),tmpComment.getComment(),carUser);
        car.getCarComments().add(comment);
        carRepo.save(car);
        return car;
    }

    @RequestMapping(path = "/open/cars/{id}/loadImage", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public Car loadCarImage(@PathVariable Long id, @RequestParam MultipartFile carImage) throws Exception {
        String uploadedFileName = null;
        Car car = carRepo.findOne(id);
        if(car != null) {
            if (!carImage.isEmpty()) {
                try {
                    uploadedFileName = System.currentTimeMillis() + "_" + carImage.getOriginalFilename();
                    Files.copy(carImage.getInputStream(), Paths.get(uploadLocation + uploadedFileName));
                    car.setImage(uploadedFileName);
                    carRepo.save(car);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return car;
    }

}
