package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Entities.TheaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<TheaterEntity, Integer> {
    boolean existsByName(String name);
}
