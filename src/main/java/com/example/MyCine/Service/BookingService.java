package com.example.MyCine.Service;

import com.example.MyCine.DTO.BookingDTO;
import com.example.MyCine.DTO.ResponseMessageDTO;
import com.example.MyCine.DTO.TableDTO;
import com.example.MyCine.Model.Booking;
import com.example.MyCine.Model.Table;
import com.example.MyCine.Model.User;
import com.example.MyCine.Repository.BookingRepository;
import com.example.MyCine.Repository.TableRepository;
import com.example.MyCine.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.text.TabExpander;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;
    private final MongoOperations mongoOperations;

    private final TableService tableService;
    private final Logger logger = LoggerFactory.getLogger(BookingService.class);


    public ResponseEntity<Object> processBooking(BookingDTO bookingDTO){
        try{
            User user = userRepository.findUserByUserID(bookingDTO.getUserID());
            Table table = tableRepository.findTableByTableID(bookingDTO.getTableID());
            Booking booking = new Booking(user,table,bookingDTO.getBookingTime());

            logger.trace("Booking info: " + booking);
            bookingRepository.save(booking);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                    .message("Error occurs when adding to database")
                    .build());
        }

        logger.trace("Successfully register booking: " + bookingDTO.getUserID());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessageDTO.builder()
                .message("New booking created")
                .build());
    }

    public ResponseEntity<Object> deleteBooking(String bookingID){
        try{
            logger.info("BookingID info: " + bookingID);

            Booking booking = bookingRepository.findByBookingID(bookingID);
            logger.info("Booking info: " + booking);
            bookingRepository.delete(booking);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                    .message("Incorrect information")
                    .build());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessageDTO.builder()
                .message("Delete booking successfully")
                .build());
    }


    public List<Booking> getAllBooking(){
        return bookingRepository.findAll().stream().toList();
    }

    public void setBookingStatus(String bookingID, boolean status){
        Query query = new Query(Criteria.where("bookingID").is(bookingID));
        Update update = Update.update("completed", status);
        mongoOperations.updateMulti(query, update, Booking.class);
    }
    @Scheduled(fixedRate = 60000) // Kiểm tra mỗi 1 phút
    public void updateTableAvailability() {
        LocalDateTime localNow = LocalDateTime.now();
        LocalDateTime thresholdTime = localNow.minusMinutes(15);
        Date thresholdDateTime = Date.from(thresholdTime.toInstant(ZoneOffset.UTC));

        Query query = new Query(Criteria.where("bookingTime").lte(thresholdDateTime).and("completed").is(false));
        List<Booking> bookings = bookingRepository.findByBookingTimeLessThanEqualAndCompletedFalse(thresholdDateTime);
        for(Booking booking : bookings){
            tableService.updateStatus(booking.getTable().getTableID(),false);
            logger.info("update table: " + booking.getTable().getTableID());
        }
    }

}
