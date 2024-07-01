package com.gameoddx.gameOdds.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@Document(collection = "predictables")
@NoArgsConstructor

public class User {

    @Id

    private String _id;

    private String name;

    private String password;

    private String email;

    private UserRole role;

    private RegistrationSource registrationSource;

    private Date timeStamp;


    public User(String _id, String name, String email, UserRole role, RegistrationSource registrationSource,Date timeStamp){
        this._id = _id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.registrationSource=registrationSource;
        this.timeStamp= timeStamp;
    }
}
