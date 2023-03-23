package com.example.BookMyShow.Convertors;

import com.example.BookMyShow.Entities.TicketEntity;
import com.example.BookMyShow.ResponseDto.TicketDetailsResponseDto;

public class TicketConvertor {
    public static TicketDetailsResponseDto convertEntityToDto(TicketEntity ticketEntity){
        return TicketDetailsResponseDto.builder()
                .id(ticketEntity.getId())
                .ticketId(ticketEntity.getTicketId())
                .theaterName(ticketEntity.getTheaterName())
                .totalAmount(ticketEntity.getTotalAmount())
                .movieName(ticketEntity.getMovieName())
                .showEntityId(ticketEntity.getShowEntity().getId())
                .bookedSeats(ticketEntity.getBookedSeats())
                .showDate(ticketEntity.getShowDate())
                .showTime(ticketEntity.getShowTime())
                .userEntityId(ticketEntity.getUserEntity().getId())
                .status(ticketEntity.getStatus())
                .build();
    }
}
