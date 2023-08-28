package com.example.MyCine.Model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dish {
    @Id
    private String dishID;
    private String name;
    private int firstPrice;
    private int lastPrice;
    private boolean available;
}
