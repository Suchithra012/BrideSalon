package com.example.HairSalon.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false, unique = true)
    @Size(min = 2, message = "userName should have at least 2 characters!")
    @NotBlank(message = "username cannot be blank")
    @NotNull(message = "username cannot be null")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 6, message = "password should have at least 6 characters!")
    @NotBlank(message = "password cannot be blank")
    @NotNull(message = "password cannot be null")
    private String password;

    @Column(name = "full_name")
    @NotBlank(message = "full name cannot be blank")
    @NotNull(message = "full name cannot be null")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "email cannot be null")
    private String email;

    private String role;
    private String salonName;
    private String salonBranch;
    private String salonCity;
    private String pinCode;

}
