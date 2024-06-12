package com.example.HairSalon.Service;

import com.example.HairSalon.Dto.LoginDto;
import com.example.HairSalon.Dto.NewUserDto;
import com.example.HairSalon.Entity.User;
import com.example.HairSalon.Logging.LoginMessage;
import com.example.HairSalon.Repository.UserRepo;
import com.example.HairSalon.mail.MailService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.transaction.Transactional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public String signUp(NewUserDto newUser) throws Exception {
        if (newUser == null) {
            throw new Exception("Trying to create a null user!!!");
        }
        else if (newUser.getUsername() == null) {
            throw new Exception("Username is null !!");
        }
        else if (newUser.getEmail() == null) {
            throw new Exception("Email is null !!");
        } else if (newUser.getFullName() == null) {
            throw new Exception("FirstName is null !!");
        } else if (newUser.getPassword() == null) {
            throw new Exception("Password is null !!");
        } else if (!isValidEmailAddress(newUser.getEmail())) {
            throw new Exception("Enter valid email");
        } else if (newUser.getPassword().length() < 6) {
            throw new Exception("Password must contain at least 6 characters ");
        } else {
            String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
            Pattern pattern = Pattern.compile(regex);
            User existingUser = this.userRepo.getUserByUsername(newUser.getUsername());
            if (existingUser != null) {
                throw new Exception("There is already a user registered with the username provided !!!");
            } else {
                existingUser = this.userRepo.getUserByEmail(newUser.getEmail());
                if (existingUser != null) {
                    throw new Exception("There is already a user registered with the email provided !!!");
                } else {
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    String rawPassword = newUser.getPassword();
                    String encryptedPassword = bCryptPasswordEncoder.encode(rawPassword);
                    User user = new User();
                    user.setUsername(newUser.getUsername());
                    user.setPassword(encryptedPassword);
                    user.setEmail(newUser.getEmail());
                    user.setRole("Customer");
                    user.setFullName(newUser.getFullName());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setSalonBranch(newUser.getSalonBranch());
                    user.setSalonCity(newUser.getSalonCity());
                    user.setPinCode(newUser.getPinCode());
                    userRepo.save(user);

                    mailService.emailAboutCustomer(user);

                    return "Registered successfully!";
                }
            }
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException var3) {
            result = false;
        }

        return result;
    }

    public LoginMessage login(LoginDto loginDto) {
        String msg = "";
        User user = this.userRepo.getUserByUsername(loginDto.getUsername());
        if (user != null) {
            String password = loginDto.getPassword();
            String enCoderdPassword = user.getPassword();
            Boolean isTrue = this.passwordEncoder.matches(password, enCoderdPassword);
            if (isTrue) {
                User userOptional = this.userRepo.getUserByUsername(loginDto.getUsername());
                return userOptional != null ? new LoginMessage("Login Success", true) : new LoginMessage("Login Failed", false);
            } else {
                return new LoginMessage("Password Not Match", false);
            }
        } else {
            return new LoginMessage("UserName not match", false);
        }
    }
}