package com.example.BookMyShow.Entities;

import com.example.BookMyShow.Enums.Genre;
import com.example.BookMyShow.Enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String movieName;

    private double ratings;

    private int duration;

    @Enumerated(value = EnumType.STRING)
    private Language language;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "movieEntity", cascade = CascadeType.ALL)
    List<ShowEntity> showEntityList = new ArrayList<>();

}
