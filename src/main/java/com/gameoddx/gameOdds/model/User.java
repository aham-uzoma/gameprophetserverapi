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

    private String username;

    private String password;

    private String email;

    public User(String _id, String username, String email){
        this._id = _id;
        this.username = username;
        this.email = email;
    }
}
