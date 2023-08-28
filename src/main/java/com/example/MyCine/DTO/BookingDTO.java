package com.example.MyCine.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private String userID;
    private String tableID;
    private long bookingTime;

}
