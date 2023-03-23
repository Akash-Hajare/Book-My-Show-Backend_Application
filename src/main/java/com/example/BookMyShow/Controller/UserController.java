package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.UserEntryDto;
import com.example.BookMyShow.ResponseDto.TicketDetailsResponseDto;
import com.example.BookMyShow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add") //http://localhost:8080/users/add
    public ResponseEntity<String> addUser(@RequestBody UserEntryDto userEntryDto) {
        try {
            String response = userService.addUser(userEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e){
            String result = "User could not be added";
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
     /*
        //Copy-paste the following in postman
        {
            "name" : "Akash",
            "age" : 25,
            "email" : "akashhajare88@gmail.com",
            "mobNo" : "8948607977",
            "address" : "Pune , Maharashtra"
        }
    */

    @DeleteMapping("/remove") //http://localhost:8080/users/remove?userId=<id here>
    public ResponseEntity<String> removeUser(@RequestParam("userId") int userId){
        try{
            String response = userService.removeUser(userId);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception occur while removing user",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-tickets") //http://localhost:8080/users/all-tickets?userId=<id here>
    public ResponseEntity allTickets(@RequestParam("userId") int userId){
        //This will return all tickets booked by user till now...and this includes even cancelled tickets also
        try{
            List<TicketDetailsResponseDto> ticketDetailsResponseDtoList = userService.allTickets(userId);
            return new ResponseEntity<>(ticketDetailsResponseDtoList,HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception occur. Please Try Again",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-address/{userId}") //http://localhost:8080/users/update-address/<userId>
    public ResponseEntity<String> updateUserAddress(@PathVariable int userId, @RequestBody String address){
        try{
            String response = userService.updateUserAddress(userId,address);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception Occur while Updating users",HttpStatus.BAD_REQUEST);
        }
    }
}
