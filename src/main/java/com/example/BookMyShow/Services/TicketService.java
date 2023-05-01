package com.example.BookMyShow.Services;

import com.example.BookMyShow.Convertors.TicketConvertor;
import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.Entities.ShowSeatEntity;
import com.example.BookMyShow.Entities.TicketEntity;
import com.example.BookMyShow.Entities.UserEntity;
import com.example.BookMyShow.EntryDtos.TicketEntryDto;
import com.example.BookMyShow.Enums.TicketStatus;
import com.example.BookMyShow.Repository.ShowRepository;
import com.example.BookMyShow.Repository.TicketRepository;
import com.example.BookMyShow.Repository.UserRepository;
import com.example.BookMyShow.ResponseDto.TicketDetailsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JavaMailSender emailSender;

    public String bookTicket(TicketEntryDto ticketEntryDto) throws Exception {
        int showId = ticketEntryDto.getShowId();
        int userId = ticketEntryDto.getUserId();
        List<String> requestedSeats = ticketEntryDto.getRequestedSeats();

        ShowEntity showEntity = showRepository.findById(showId).get();

        List<ShowSeatEntity> listOfSeatsForThisShow = showEntity.getShowSeatEntityList();

        String bookedSeats;
        int totalAmount;
        try{
            Pair<String,Integer> pair = bookTheSeats(requestedSeats, listOfSeatsForThisShow);
            bookedSeats = pair.getFirst();
            totalAmount = pair.getSecond();
        } catch (Exception e){
            return "Seat is already booked";
        }

        UserEntity userEntity = userRepository.findById(userId).get();
        String movieName = showEntity.getMovieEntity().getMovieName();
        String theaterName = showEntity.getTheaterEntity().getName();
        String ticketId = UUID.randomUUID().toString();
        LocalDate localDate = showEntity.getShowDate();
        LocalTime localTime = showEntity.getShowTime();

        TicketEntity ticketEntity = TicketEntity.builder().ticketId(ticketId).movieName(movieName)
                .showEntity(showEntity).theaterName(theaterName).bookedSeats(bookedSeats)
                .showDate(localDate).showTime(localTime).userEntity(userEntity)
                .totalAmount(totalAmount).status(TicketStatus.CONFIRMED).build();

        TicketEntity updatedTicketEntity = ticketRepository.save(ticketEntity);
        userEntity.getTicketEntityList().add(updatedTicketEntity);
        userRepository.save(userEntity);
        showEntity.getTicketEntityList().add(updatedTicketEntity);
        showRepository.save(showEntity);

        //Email
        String response="Hi "+userEntity.getName()+
                "\n"+
                "\n This email is to confirm your tickets bookings "+
                "\n Please refer below details so you don't miss the show "+
                "\n"+
                "\n Movie Name : "+showEntity.getMovieEntity().getMovieName()+
                "\n Theater Name : "+showEntity.getTheaterEntity().getName()+
                "\n Booked Seats : "+bookedSeats+
                "\n Ticket id : "+ticketEntity.getTicketId()+
                "\n Show Date : "+showEntity.getShowDate()+
                "\n Show Time :"+ showEntity.getShowTime();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendspringrocks@gmail.com");
        message.setTo("akashhajared11@gmail.com");
        message.setSubject("Booking Confirmation Email");
        message.setText(response);
        emailSender.send(message);

       return response;
        //return "Tickets Booked : " + bookedSeats;
    }

    private Pair<String,Integer> bookTheSeats(List<String> requestedSeats,
                                              List<ShowSeatEntity> listOfSeatsForThisShow)
            throws Exception {

        for(ShowSeatEntity showSeatEntity : listOfSeatsForThisShow) {
            String seatNo = showSeatEntity.getSeatNo();
            if (requestedSeats.contains(seatNo)) {
                if (showSeatEntity.isBooked()) {
                    throw new Exception();
                }
            }
        }

        StringBuilder bookedSeats = new StringBuilder();
        int totalAmount = 0;
        for(ShowSeatEntity showSeatEntity : listOfSeatsForThisShow){
            String seatNo = showSeatEntity.getSeatNo();
            if (requestedSeats.contains(seatNo)){
                totalAmount += showSeatEntity.getPrice();
                if(bookedSeats.length()==0){
                    bookedSeats.append(seatNo);
                }
                else {
                    bookedSeats.append(", ").append(seatNo);
                }
                showSeatEntity.setBooked(true);
            }
        }
        return Pair.of(bookedSeats.toString(),totalAmount);
    }

    public String cancelTicket(int ticketId) throws Exception{
        TicketEntity ticketEntity = ticketRepository.findById(ticketId).get();

        String bookedSeats = ticketEntity.getBookedSeats();
        String[] bookedSeatsArr = bookedSeats.split(", ");

        ShowEntity showEntity = ticketEntity.getShowEntity();
        List<ShowSeatEntity> showSeatEntityList = showEntity.getShowSeatEntityList();

        cancelBookingOfSeats(bookedSeatsArr,showSeatEntityList);

        bookedSeats = Arrays.toString(bookedSeatsArr);
        ticketEntity.setBookedSeats(bookedSeats);

        ticketEntity.setStatus(TicketStatus.CANCELLED);
        ticketEntity.setBookedSeats(null);

        showRepository.save(showEntity);

        //email
        String response="Hi "+ticketEntity.getUserEntity().getName()+
        "\n"+
        "\nThis is to conform your booking cancellation "+
        "\nTicket id : "+ticketEntity.getTicketId()+
        "\nCancellation Seats : "+ticketEntity.getTicketId()+
        "\nTotal Refundable amount : "+ticketEntity.getTotalAmount()+
        "\n Note : Amount will be refunded in your bank account within 7 workings days";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendspringrocks@gmail.com");
        message.setTo("akashhajared11@gmail.com");
        message.setSubject("Cancel Ticket's Confirmation Email");
        message.setText(response);
        emailSender.send(message);

        return "Ticket is cancelled details given below"+
                "\n"+response;

    }

    private void cancelBookingOfSeats(String[] bookedSeatsArr, List<ShowSeatEntity> showSeatEntityList) throws Exception{
        for(ShowSeatEntity showSeatEntity : showSeatEntityList){
            String seatNo = showSeatEntity.getSeatNo();
            if(Arrays.asList(bookedSeatsArr).contains(seatNo)){
                showSeatEntity.setBooked(false);
            }
        }
    }

    public TicketDetailsResponseDto getDetails(int ticketId) throws Exception{
        TicketEntity ticketEntity = ticketRepository.findById(ticketId).get();
        return TicketConvertor.convertEntityToDto(ticketEntity);
    }
}
