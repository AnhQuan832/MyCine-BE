package com.example.MyCine.Repository;

import com.example.MyCine.Model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends MongoRepository<Table, String> {
    Table findTableByTableID(String tableID);
}
