package com.gameoddx.gameOdds.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@Document(collection = "predictables")
@NoArgsConstructor


public class Predictable {

    @Id
    private String _id;

    private String game;

    private String odds;

    private String vip;

    private String regular;

    private String result;

    private Date timeStamp;

    public Predictable(String _id, String game, String odds, String vip, String regular, String result, Date timeStamp) {
        this._id = _id;
        this.game = game;
        this.odds = odds;
        this.vip = vip;
        this.regular = regular;
        this.result = result;
        this.timeStamp= timeStamp;
    }
}
