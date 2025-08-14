package com.example.ProductDemo.Putaway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PutawayRepo extends JpaRepository<Putaway, Integer> {

}
