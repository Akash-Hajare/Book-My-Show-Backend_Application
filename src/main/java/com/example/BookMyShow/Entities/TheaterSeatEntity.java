package com.example.BookMyShow.Entities;

import com.example.BookMyShow.Enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "theater_seats")
public class TheaterSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    private String seatNo;

    @ManyToOne
    @JoinColumn
    private TheaterEntity theaterEntity;


}

