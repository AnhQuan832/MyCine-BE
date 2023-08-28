package com.example.MyCine.Model;

import com.example.MyCine.DTO.TableDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Table {
    @Id
    private String tableID;
    private String name;
    private int numOfSeat;
    private boolean available;

    public Table(TableDTO tableDTO){
        this.name = tableDTO.getName();
        this.numOfSeat = tableDTO.getNumOfSeat();
        this.available = true;
    }
}




