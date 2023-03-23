package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    boolean existsByMovieName(String movieName);
}