package com.example.BookMyShow.EntryDtos;

import com.example.BookMyShow.Enums.Genre;
import com.example.BookMyShow.Enums.Language;
import lombok.Data;

import javax.persistence.Column;

@Data
public class MovieEntryDto {
    @Column(unique = true, nullable = false)
    private String movieName;

    private double ratings;

    private int duration;

    private Language language;

    private Genre genre;

}

