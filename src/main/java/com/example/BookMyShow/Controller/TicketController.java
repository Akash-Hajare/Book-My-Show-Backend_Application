package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.TicketEntryDto;
import com.example.BookMyShow.ResponseDto.TicketDetailsResponseDto;
import com.example.BookMyShow.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/booking_ticket") //http://localhost:8080/tickets/booking_ticket
    public ResponseEntity<String> bookTicket(@RequestBody TicketEntryDto ticketEntryDto)  {
        try{
            String response = ticketService.bookTicket(ticketEntryDto);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception Occur while booking ticket",HttpStatus.BAD_REQUEST);
        }
    }

    /*
        //Copy-paste the following in postman
        {
            "showId" : 2,
            "userId" : 1,
            "requestedSeats" : ["16P","13C"]
        }
    */

    @GetMapping("/get-ticket-details") //http://localhost:8080/tickets/get-ticket-details?ticketId=<id here>
    public ResponseEntity getDetails(@RequestParam("ticketId") int ticketId){
        try{
            TicketDetailsResponseDto ticketDetailsResponseDto = ticketService.getDetails(ticketId);
            return new ResponseEntity<>(ticketDetailsResponseDto,HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>("Excption occur while getting ticket details",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cancel_ticket") //http://localhost:8080/tickets/cancel_ticket?ticketId=<id here>
    public ResponseEntity<String> cancelTicket(@RequestParam("ticketId") int ticketId){
        try{
            String response = ticketService.cancelTicket(ticketId);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception occur while Cancling Tickits",HttpStatus.BAD_REQUEST);
        }
    }
}

