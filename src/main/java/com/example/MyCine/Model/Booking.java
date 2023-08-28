package com.example.MyCine.Model;

import com.example.MyCine.DTO.BookingDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "booking")
public class Booking {
    @Id
    private String bookingID;
    @DBRef
    private User user;
    @DBRef
    private Table table;
    private Date bookingTime;
    private boolean completed;

    public Booking(User user, Table table, long bookingTime) {
        this.user = user;
        this.table = table;
        this.bookingTime = new Date(bookingTime);
        this.completed = false;
    }
}
