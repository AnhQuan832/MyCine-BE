package com.example.MyCine.Repository;

import com.example.MyCine.Model.Booking;
import com.example.MyCine.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking,String> {

    Booking findByBookingID(String bookingID);
    List<Booking> findByBookingTimeBefore(Date bookingTime);
    List<Booking> findByBookingTimeLessThanEqualAndCompletedFalse(Date thresholdDateTime);

}
