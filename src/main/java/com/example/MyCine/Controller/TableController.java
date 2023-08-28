package com.example.MyCine.Controller;

import com.example.MyCine.DTO.TableDTO;
import com.example.MyCine.DTO.UserRegisterDTO;
import com.example.MyCine.Model.Table;
import com.example.MyCine.Service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1/table")
public class TableController {
    private final TableService tableService;
    @GetMapping("")
    public List<Table> getAllTable(){
        return tableService.getALlTable();
    }

    @MessageMapping("/updateTable")
    @SendTo("/table")
    public TableDTO updateTable(@Payload TableDTO tableDTO){
        tableService.updateStatus(tableDTO.getTableID(), tableDTO.isAvailable());
        return tableDTO;
    }
    @PostMapping("/addNewTable")
    public Object addNewTable(@RequestBody TableDTO request){
        return tableService.addTable(request);
    }
}
