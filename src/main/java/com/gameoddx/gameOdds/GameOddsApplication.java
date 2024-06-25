package com.gameoddx.gameOdds;

import com.gameoddx.gameOdds.model.Predictable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GameOddsApplication {

	public static final Logger logger = LoggerFactory.getLogger(GameOddsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GameOddsApplication.class, args);
	}

}
