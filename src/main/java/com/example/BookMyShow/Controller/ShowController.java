package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.ShowEntryDto;
import com.example.BookMyShow.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    ShowService showService;

    @PostMapping("/add") //http://localhost:8080/show/add
    public ResponseEntity<String> addShow(@RequestBody ShowEntryDto showEntryDto){
        try{
            String response = showService.addShow(showEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception occurs.. Please try again",HttpStatus.BAD_REQUEST);
        }
    }

    /*
        //Copy-paste the following in postman
        {
            "showDate" : "2023-02-19",
            "showTime": "16:00:00.000",
            "showType" : "_2D",
            "movieId" : 1,
            "theaterId" : 1,
            "classicSeatPrice" : 25,
            "premiumSeatPrice" : 35
        }
    */

    @DeleteMapping("/remove") //http://localhost:8080/show/remove?showId=<id here>
    public ResponseEntity<String> removeShow(@RequestParam("showId") int showId){
        try{
            String response = showService.removeShow(showId);
            if(response.equals("CANCELED")){
                //Email will be sent to the users that show has been cancelled,
                //you will be refunded your money back
            }
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Exception occur",HttpStatus.BAD_REQUEST);
        }
    }
}
