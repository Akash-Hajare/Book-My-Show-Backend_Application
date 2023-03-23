package com.example.BookMyShow.Entities;

import com.example.BookMyShow.Enums.ShowType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "shows")
public class ShowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    LocalDate showDate;

    LocalTime showTime;

    @Enumerated(value = EnumType.STRING)
    private ShowType showType;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    //This is child wrt to theater and movie both

    @ManyToOne
    @JoinColumn
    private TheaterEntity theaterEntity;//

    @ManyToOne
    @JoinColumn
    private MovieEntity movieEntity;//

    @OneToMany(mappedBy = "showEntity", cascade = CascadeType.ALL)
    List<TicketEntity> ticketEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "showEntity", cascade = CascadeType.ALL)
    List<ShowSeatEntity> showSeatEntityList = new ArrayList<>();//
}
