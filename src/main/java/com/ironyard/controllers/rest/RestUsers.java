package com.ironyard.controllers.rest;

import com.ironyard.data.Car;
import com.ironyard.data.CarComment;
import com.ironyard.data.CarToken;
import com.ironyard.data.CarUser;
import com.ironyard.dto.TempCar;
import com.ironyard.dto.TempUser;
import com.ironyard.repositories.CarRepo;
import com.ironyard.repositories.CarUserRepo;
import com.ironyard.security.TokenMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by wailm.yousif on 2/21/17.
 */

@RestController
public class RestUsers
{
    @Autowired
    CarUserRepo carUserRepo;
    @Autowired
    CarRepo carRepo;

    @Value("${upload.location}")
    private String uploadLocation;

    @RequestMapping(path = "/open/users/login", method = RequestMethod.POST)
    public CarToken login(@RequestBody  TempUser tempUser) throws Exception {
        CarUser carUser = carUserRepo.findByEmailAndPassword(tempUser.getEmail(), tempUser.getPassword());
        if (carUser != null)
        {
            TokenMaster tm = new TokenMaster();
            String tokenString = tm.generateToken(carUser);
            CarToken chatToken = new CarToken(tokenString, carUser);
            return chatToken;
        }
        else
        {
            throw new Exception("Invalid credentials");
        }
    }

    @RequestMapping(path = "/open/users/logout", method = RequestMethod.GET)
    public CarUser logout() throws Exception {
        CarUser carUser = new CarUser();
        return carUser;
    }

    @RequestMapping(path = "/open/unauthorized", method = RequestMethod.GET)
    public ResponseEntity<?> unAuthorized() throws Exception {
        CarUser carUser = new CarUser();
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(path = "/open/users/{id}", method = RequestMethod.GET)
    public CarUser getUser(@PathVariable Long id) throws Exception {
        CarUser carUser = carUserRepo.findOne(id);
        return carUser;
    }

    @RequestMapping(path = "/open/users/register", method = RequestMethod.POST)
    public CarUser createUser(@RequestBody TempUser tempUser) throws Exception {
        CarUser found = carUserRepo.findByEmail(tempUser.getEmail());
        CarUser carUser = null;
        if(found == null) {
            carUser = new CarUser(tempUser);
            carUserRepo.save(carUser);
            return carUser;
        }else{
            throw new Exception("Already used email");
        }
    }

    @RequestMapping(path = "/open/users/{id}/loadImage", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public CarUser loadUserImage(@PathVariable Long id, @RequestParam MultipartFile userImage) throws Exception {
        String uploadedFileName = null;
        CarUser carUser = carUserRepo.findOne(id);
        if(carUser != null) {
            if (!userImage.isEmpty()) {
                try {
                    uploadedFileName = System.currentTimeMillis() + "_" + userImage.getOriginalFilename();
                    Files.copy(userImage.getInputStream(), Paths.get(uploadLocation + uploadedFileName));
                    carUser.setImage(uploadedFileName);
                    carUserRepo.save(carUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return carUser;
    }
}