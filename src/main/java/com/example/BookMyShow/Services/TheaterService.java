package com.example.BookMyShow.Services;

import com.example.BookMyShow.Convertors.TheaterConvertors;
import com.example.BookMyShow.Entities.TheaterEntity;
import com.example.BookMyShow.Entities.TheaterSeatEntity;
import com.example.BookMyShow.EntryDtos.TheaterEntryDto;
import com.example.BookMyShow.Enums.SeatType;
import com.example.BookMyShow.Repository.TheaterRepository;
import com.example.BookMyShow.Repository.TheaterSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TheaterService {

    @Autowired
    TheaterSeatRepository theaterSeatRepository;

    @Autowired
    TheaterRepository theaterRepository;

    public String addTheater(TheaterEntryDto theaterEntryDto) throws Exception{
        /*
         * 1. create theaterSeats
         * 2. I need to save theater : I need theaterEntity
         * 3. Always set the attributes before saving
         * */

        //Some validations required here :-
        if(theaterEntryDto.getName() == null || theaterEntryDto.getLocation() == null){
            throw new Exception("Name and location should be valid");
        }

        boolean theaterExists = theaterRepository.existsByName(theaterEntryDto.getName());
        if(theaterExists){
            return "Theater already exists";
        }
        TheaterEntity theaterEntity = TheaterConvertors.convertDtoToEntity(theaterEntryDto);
        List<TheaterSeatEntity> theaterSeatEntityList = createTheaterSeats(theaterEntryDto, theaterEntity);
        theaterEntity.setTheaterSeatEntityList(theaterSeatEntityList);
        theaterRepository.save(theaterEntity);
        return "Theater added successfully";
    }

    private List<TheaterSeatEntity> createTheaterSeats(TheaterEntryDto theaterEntryDto, TheaterEntity theaterEntity) throws Exception{
        int noClassicSeats = theaterEntryDto.getClassicSeatsCount();
        int noPremiumSeats = theaterEntryDto.getPremiumSeatsCount();

        List<TheaterSeatEntity> theaterSeatEntityList = new ArrayList<>();

        //Create the classic Seats
        for(int count = 1; count<=noClassicSeats; count++){

            //We need to make a new TheaterSeatEntity

            TheaterSeatEntity theaterSeatEntity = TheaterSeatEntity.builder()
                    .seatType(SeatType.CLASSIC).seatNo(count+"C").theaterEntity(theaterEntity).build();

            theaterSeatEntityList.add(theaterSeatEntity);
        }

        //Create the premium Seats
        for(int count = 1; count<=noPremiumSeats; count++){
            TheaterSeatEntity theaterSeatEntity = TheaterSeatEntity.builder()
                    .seatType(SeatType.PREMIUM).seatNo(count+"P").theaterEntity(theaterEntity).build();

            theaterSeatEntityList.add(theaterSeatEntity);
        }

        return theaterSeatEntityList;
    }

    public String removeTheater(int theaterId) throws Exception{
        theaterRepository.deleteById(theaterId);
        return "Theater removed successfully";
    }

    public Map<String,String> theaterWithUniqueLocations() throws Exception{
        Map<String,String> theaterWithUniqueLocations = new HashMap<>();
        List<TheaterEntity> theaterEntityList = theaterRepository.findAll();
        for(TheaterEntity theaterEntity : theaterEntityList){
            String location = theaterEntity.getLocation();
            if(!theaterWithUniqueLocations.containsKey(location)){
                String name = theaterEntity.getName();
                theaterWithUniqueLocations.put(location,name);
            }
        }
        return theaterWithUniqueLocations;
    }

}
