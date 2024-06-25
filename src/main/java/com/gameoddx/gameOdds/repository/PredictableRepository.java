package com.gameoddx.gameOdds.repository;

import com.gameoddx.gameOdds.model.Predictable;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PredictableRepository extends MongoRepository<Predictable, ObjectId> {
    List<Predictable> findBytimeStamp(Date timestamp);

}
