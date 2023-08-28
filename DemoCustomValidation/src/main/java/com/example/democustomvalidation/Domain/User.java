package com.example.democustomvalidation.Domain;
import com.example.democustomvalidation.Interface.StrongPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String username;

    @StrongPassword
    private String password;

}