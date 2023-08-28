package com.example.MyCine.Service;

import com.example.MyCine.Model.Booking;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DataCleanUpService {
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(DataCleanUpService.class);

    @Scheduled(cron = "0 */100 * * * *") // Run the cleanup task every 1 minutes
    public void deleteExpiredData() {
        logger.info("Cleaned up!");
        logger.trace("Cleaned up!");

        Date currentTime = new Date();
        Query query = Query.query(Criteria.where("expirationDate").lt(currentTime));
        mongoTemplate.findAllAndRemove(query, Booking.class);
    }
}
