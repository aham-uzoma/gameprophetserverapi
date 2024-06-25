package com.gameoddx.gameOdds.service;

import com.gameoddx.gameOdds.model.Predictable;
import com.gameoddx.gameOdds.repository.PredictableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.bson.types.ObjectId;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PredictableService {

    @Autowired
    private PredictableRepository predictableRepository;
    private MongoTemplate mongoTemplate;


    //write CRUD Operations in the service repository

//    public List<Predictable> allPredictions(){
//
//        List<Predictable> predictionsData = predictableRepository.findAll();
//        System.out.println("Predictions:"+ predictionsData);
//        return predictionsData;
//    }

    public ResponseEntity<List<Predictable>> allPredictions(){

        List<Predictable> predictionsData = predictableRepository.findAll();
        System.out.println("Predictions:"+ predictionsData);
        return ResponseEntity.ok(predictionsData);
    }

    public List<Predictable> createNewPredictions(List<Predictable> predictable) {

        System.out.println("predictable:" + predictable);
        predictable.forEach(prediction -> {
            prediction.setTimeStamp(new Date());
            prediction.setResult("Ongoing");
        });
        return predictableRepository.saveAll(predictable);
    }

    public List<Predictable> upDatePredictionsResults(List<Predictable>resultsRequest){
        //get existing document from db by id
        //populate new value from request to existing object/entity/document
        System.out.println("All resultsRequest " + resultsRequest + " from Request.");

        List<String>stringIds = resultsRequest
                .stream()
                .map(Predictable::get_id)
                .collect(Collectors.toList());

        List<ObjectId> ids = stringIds.stream()
                .map(ObjectId::new) // Convert String ID to ObjectId
                .collect(Collectors.toList());

        System.out.println("All Ids " + ids + " from Request.");

        Map<String, Predictable> existingMapFromDb = predictableRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(p -> (String) p.get_id(),predictable->predictable));

        for(Predictable predictable: resultsRequest){
          Predictable existingPrediction = existingMapFromDb.get(predictable.get_id());
           if(existingPrediction != null){
               Predictable updatedPrediction = new Predictable(
                   existingPrediction.get_id(),
                       existingPrediction.getGame(),
                       existingPrediction.getOdds(),
                       existingPrediction.getVip(),
                       existingPrediction.getRegular(),
                       predictable.getResult(),
                       existingPrediction.getTimeStamp()
               );
               predictableRepository.save(updatedPrediction);
           } else {
               System.out.println("Prediction with ID " + predictable.get_id() + " not found for update.");
           }

        }

        return null;
    }


//    public Map<LocalDate, List<Predictable>> getPredictionsGroupedByTimeStamp() {
//
//        List<Predictable> allPredictions = predictableRepository.findAll();
//
//        Map<LocalDate, List<Predictable>> groupedPredictions = new HashMap<>();
//
//        for(Predictable prediction : allPredictions){
//            LocalDate predictionDay = prediction.getTimeStamp().toLocalDate;
//
//            List<Predictable> predictionsForDay = groupedPredictions.get(predictionDay);
//
//            if(predictionsForDay == null){
//                predictionsForDay = new ArrayList<>();
//                groupedPredictions.put(predictionDay, predictionsForDay);
//            }
//            predictionsForDay.add(prediction);
//        }
//        return  groupedPredictions;
//    }
public Map<Date, List<Predictable>> getPredictionsGroupedByDay() {
    List<Predictable> allPredictions = predictableRepository.findAll();
    Map<Date, List<Predictable>> groupedPredictions = new HashMap<>();

    for (Predictable prediction : allPredictions) {
        Date timestamp = prediction.getTimeStamp();
        // Truncate the timestamp to the date (remove time portion)
        Date dateOnly = new Date(timestamp.getTime() / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000));

        List<Predictable> predictionsAtDate = groupedPredictions.get(dateOnly);
        if (predictionsAtDate == null) {
            predictionsAtDate = new ArrayList<>();
            groupedPredictions.put(dateOnly, predictionsAtDate);
        }
        predictionsAtDate.add(prediction);
    }
    return groupedPredictions;
}

}
