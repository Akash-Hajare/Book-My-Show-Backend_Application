package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    @Query(value = "SELECT SUM(total_amount) as totalSum FROM tickets WHERE movie_name = :movieName",
            nativeQuery = true)
    int getTotalCollectionOfMovie(String movieName);
}