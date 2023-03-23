package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.MovieEntryDto;
import com.example.BookMyShow.ResponseDto.MovieCollectionResponseDto;
import com.example.BookMyShow.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;


    @PostMapping("/add") //http://localhost:8080/movies/add
    public ResponseEntity<String> addMovie(@RequestBody MovieEntryDto movieEntryDto){
        try {
            String result = movieService.addMovie(movieEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e){
            String response = "Movie not added";
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /*
        //Copy-paste the following in postman
        {
           "movieName" : "Pathan",
           "ratings" : 7.6,
           "duration" : 3,
           "language" : "HINDI",
           "genre" : "ACTION"
        }
    */

    @DeleteMapping("/remove") //http://localhost:8080/movies/remove?movieId=<id here>
    public ResponseEntity<String> removeMovie(@RequestParam("movieId") int movieId){
        try{
            String response = movieService.removeMovie(movieId);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Exception occures while removing movie",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-show-time") //http://localhost:8080/movies/get-show-time?movie-id=<id here>&theater-id=<id here>
    public ResponseEntity<List<Pair<LocalDate, LocalTime>>> getShowTime(@RequestParam("movie-id") int movieId,
                                                                        @RequestParam("theater-id") int theaterId){
        try{
            List<Pair<LocalDate,LocalTime>> pairList = movieService.getShowTime(movieId,theaterId);
            return new ResponseEntity<>(pairList,HttpStatus.FOUND);
        }catch (Exception e){
            List<Pair<LocalDate,LocalTime>> pairList= new ArrayList<>();
            return new ResponseEntity<>(pairList,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/movie-with-max-shows") //http://localhost:8080/movies/movie-with-max-shows
    public ResponseEntity movieWithMaxShows(){
        try{
            Pair<Integer, String> pair = movieService.movieWithMaxShows();
            return new ResponseEntity<>(pair,HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception occur ",HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/movie-with-max-collection") //http://localhost:8080/movies/movie-with-max-collection
    public ResponseEntity movieWithMaxCollection() {
        try{
            MovieCollectionResponseDto movieCollectionResponseDto = movieService.movieWithMaxCollection();
            return new ResponseEntity<>(movieCollectionResponseDto,HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>("Error occur",HttpStatus.REQUEST_TIMEOUT);
        }
    }

    @GetMapping("/all-movies-total-collection") //http://localhost:8080/movies/all-movies-total-collection
    public ResponseEntity  allMoviesTotalCollection(){
        try{
            Map<String,Integer> moviesAndTheirCollections = movieService.allMoviesTotalCollection();
            return new ResponseEntity<>(moviesAndTheirCollections,HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>("Exception occur",HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @GetMapping("/collection/{movie}") //http://localhost:8080/movies/collection/<movie>
    public ResponseEntity<Integer> totalCollectionOfMovie(@PathVariable("movie") String movieName){
        try{
            int collection = movieService.totalCollectionOfMovie(movieName);
            return new ResponseEntity<>(collection,HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
        }
    }
}