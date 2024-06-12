package com.example.HairSalon.Controller;

import com.example.HairSalon.Dto.LoginDto;
import com.example.HairSalon.Dto.NewUserDto;
import com.example.HairSalon.Logging.LoginMessage;
import com.example.HairSalon.Service.UserService;
import com.example.HairSalon.Dto.LoginDto;
import com.example.HairSalon.Dto.NewUserDto;
import com.example.HairSalon.Logging.LoginMessage;
import com.example.HairSalon.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationCtrl {
    @Autowired
    private UserService userService;

    public AuthenticationCtrl() {
    }

    @PostMapping({"/register"})
    public ResponseEntity<String> register(@RequestBody NewUserDto newUser) {
        try {
            return new ResponseEntity(this.userService.signUp(newUser), HttpStatus.CREATED);
        } catch (Exception var3) {
            Exception e = var3;
            return new ResponseEntity("Registration Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"/login"})
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        LoginMessage loginMessage = this.userService.login(loginDto);
        return ResponseEntity.ok(loginMessage);
    }
}
