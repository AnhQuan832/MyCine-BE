package com.example.MyCine.Service;

import com.example.MyCine.DTO.ResponseMessageDTO;
import com.example.MyCine.DTO.TableDTO;
import com.example.MyCine.Model.Table;
import com.example.MyCine.Repository.TableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableService {

    private final TableRepository tableRepository;
    private final MongoOperations mongoOperations;
    private final Logger logger = LoggerFactory.getLogger(TableService.class);

    public List<Table> getALlTable (){
        return tableRepository.findAll().stream().toList();
    }

    public ResponseEntity<Object> addTable(TableDTO tableDTO){
        try{
            Table table = new Table(tableDTO);
            tableRepository.insert(table);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                    .message("Error occurs when adding to database")
                    .build());
        }
        logger.trace("Successfully register user: " + tableDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessageDTO.builder()
                .message("New table created")
                .build());
    }



    public void updateStatus(String tableID, boolean status){
        Query query = new Query(Criteria.where("tableID").is(tableID));
        Update update = Update.update("available", status);
        mongoOperations.updateMulti(query, update, Table.class);
    }
}
