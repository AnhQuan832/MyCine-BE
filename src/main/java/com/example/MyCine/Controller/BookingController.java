package com.example.MyCine.Controller;

import com.example.MyCine.DTO.BookingDTO;
import com.example.MyCine.DTO.UserRegisterDTO;
import com.example.MyCine.Service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public Object processBooking(@RequestBody BookingDTO request){
        return bookingService.processBooking(request);
    }

    @GetMapping
    public Object getAllBooking(){
        return  bookingService.getAllBooking();
    }

    @DeleteMapping("/{bookingID}")
    public Object deleteBooking(@PathVariable String bookingID){
        return bookingService.deleteBooking(bookingID);
    }

}
