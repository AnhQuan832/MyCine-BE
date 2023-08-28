package com.example.MyCine.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {
    private String tableID;
    private String name;
    private int numOfSeat;
    private boolean available;
}
