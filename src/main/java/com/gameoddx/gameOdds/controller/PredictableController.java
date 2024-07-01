package com.gameoddx.gameOdds.controller;

import com.gameoddx.gameOdds.model.Predictable;
import com.gameoddx.gameOdds.service.PredictableService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.gameoddx.gameOdds.GameOddsApplication.logger;
//import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/predict")
public class PredictableController {
    @Autowired
    private PredictableService predictableService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Predictable>> getAllPredictions() {
        return predictableService.allPredictions();
    }

    @GetMapping("/test")
    public  String home(){
        return "Hello Home!";
    }

//    @GetMapping("/")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Predictable> getAllPredictions(){
//        return predictableService.allPredictions();
//    }

    @GetMapping("/grouped-by-timestamp") // Endpoint for grouped predictions
    public ResponseEntity<Map<Date, List<Predictable>>> getAllPredictionsByTimeStamp() {
        Map<Date, List<Predictable>> groupedPredictions = predictableService.getPredictionsGroupedByDay();
        return ResponseEntity.ok(groupedPredictions);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Predictable> createPredictions(@RequestBody List<Predictable> predictable) {
        System.out.println("Req:"+ predictable);
        return predictableService.createNewPredictions(predictable);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Predictable> updatePredictionsResults(@RequestBody List<Predictable> predictabless){
        String requestBodyString = predictabless.toString();
        logger.info("Incoming Request Body: {}", requestBodyString);
        return predictableService.upDatePredictionsResults(predictabless);
    }
}
