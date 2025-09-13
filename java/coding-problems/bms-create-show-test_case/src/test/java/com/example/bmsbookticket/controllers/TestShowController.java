package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.CreateShowRequestDTO;
import com.example.bmsbookticket.dtos.CreateShowResponseDTO;
import com.example.bmsbookticket.dtos.ResponseStatus;
import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import com.example.bmsbookticket.services.ShowService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestShowController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private SeatTypeShowRepository seatTypeShowRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowService showService;
    @Autowired
    private ShowController showController;

    private Movie movie;
    private Screen screen;
    private List<Seat> seats;
    private User user;
    private User admin;
    @BeforeEach
    public void insertDummyData(){
        movie = new Movie();
        movie.setName("Test Movie");
        movie.setDescription("Test Description");
        movie = movieRepository.save(movie);

        screen = new Screen();
        screen.setName("Test Screen");
        screen.setFeatures(List.of(Feature.DOLBY_ATMOS, Feature.DOLBY_VISION, Feature.IMAX, Feature.THREE_D));
        screen.setSeats(seats);
        screen.setStatus(ScreenStatus.OPERATIONAL);
        screen = screenRepository.save(screen);

        seats = new ArrayList<>();
        Seat seat1 = new Seat();
        seat1.setSeatType(SeatType.GOLD);
        seat1.setName("1A");
        seat1.setScreen(screen);
        seat1 = seatsRepository.save(seat1);
        seats.add(seat1);

        Seat seat2 = new Seat();
        seat2.setSeatType(SeatType.GOLD);
        seat2.setName("1B");
        seat2.setScreen(screen);
        seat2 = seatsRepository.save(seat2);
        seats.add(seat2);

        Seat seat3 = new Seat();
        seat3.setSeatType(SeatType.SILVER);
        seat3.setName("2A");
        seat3.setScreen(screen);
        seat3 = seatsRepository.save(seat3);

        Seat seat4 = new Seat();
        seat4.setSeatType(SeatType.SILVER);
        seat4.setName("2B");
        seat4.setScreen(screen);
        seat4 = seatsRepository.save(seat4);

        seats = List.of(seat1, seat2, seat3, seat4);
        screen.setSeats(seats);
        screen = screenRepository.save(screen);


        user = new User();
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setUserType(UserType.CUSTOMER);
        user = userRepository.save(user);

        admin = new User();
        admin.setName("Test Admin");
        admin.setEmail("user@bms.com");
        admin.setUserType(UserType.ADMIN);
        admin = userRepository.save(admin);
    }

    public Date getTime(int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    @AfterEach
    public void cleanUp(){
        showSeatRepository.deleteAll();
        seatTypeShowRepository.deleteAll();
        showRepository.deleteAll();
        seatsRepository.deleteAll();
        screenRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateShow_Success(){
        CreateShowRequestDTO createShowRequestDTO = new CreateShowRequestDTO();
        createShowRequestDTO.setMovieId(movie.getId());
        createShowRequestDTO.setScreenId(screen.getId());
        createShowRequestDTO.setStartTime(getTime(1));
        createShowRequestDTO.setEndTime(getTime(2));
        createShowRequestDTO.setFeatures(List.of(Feature.DOLBY_ATMOS, Feature.IMAX, Feature.THREE_D));
        createShowRequestDTO.setPricingConfig(List.of(Pair.of(SeatType.GOLD, 500.0), Pair.of(SeatType.SILVER, 300.0)));
        createShowRequestDTO.setUserId(admin.getId());
        CreateShowResponseDTO showResponseDTO = showController.createShow(createShowRequestDTO);
        assertNotNull(showResponseDTO, "Create show response cannot be null");
        Show show = showResponseDTO.getShow();
        assertNotNull(show, "Show cannot be null");

        List<ShowSeat> showSeats = showSeatRepository.findAll();
        assertEquals(4, showSeats.size(), "Total 4 show seats should be created");
        showSeats.forEach(showSeat -> assertEquals(SeatStatus.AVAILABLE, showSeat.getStatus(), "Show seat status should be available"));

        List<SeatTypeShow> seatTypeShows = seatTypeShowRepository.findAll();
        assertEquals(2, seatTypeShows.size(), "Total 2 seat type show should be created");

    }

    @Test
    public void testCreateShow_IncorrectDate_Failure(){
        CreateShowRequestDTO createShowRequestDTO = new CreateShowRequestDTO();
        createShowRequestDTO.setMovieId(movie.getId());
        createShowRequestDTO.setScreenId(screen.getId());
        createShowRequestDTO.setStartTime(getTime(-1));
        createShowRequestDTO.setEndTime(getTime(2));
        createShowRequestDTO.setFeatures(List.of(Feature.DOLBY_ATMOS, Feature.IMAX, Feature.THREE_D));
        createShowRequestDTO.setPricingConfig(List.of(Pair.of(SeatType.GOLD, 500.0), Pair.of(SeatType.SILVER, 300.0)));
        createShowRequestDTO.setUserId(admin.getId());
        CreateShowResponseDTO showResponseDTO = showController.createShow(createShowRequestDTO);
        assertNotNull(showResponseDTO, "Create show response cannot be null");
        assertEquals(ResponseStatus.FAILURE, showResponseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(showResponseDTO.getShow(), "Show should be null");

        createShowRequestDTO = new CreateShowRequestDTO();
        createShowRequestDTO.setMovieId(movie.getId());
        createShowRequestDTO.setScreenId(screen.getId());
        createShowRequestDTO.setStartTime(getTime(5));
        createShowRequestDTO.setEndTime(getTime(3));
        createShowRequestDTO.setFeatures(List.of(Feature.DOLBY_ATMOS, Feature.IMAX, Feature.THREE_D));
        createShowRequestDTO.setPricingConfig(List.of(Pair.of(SeatType.GOLD, 500.0), Pair.of(SeatType.SILVER, 300.0)));
        createShowRequestDTO.setUserId(admin.getId());

        showResponseDTO = showController.createShow(createShowRequestDTO);
        assertNotNull(showResponseDTO, "Create show response cannot be null");
        assertEquals(ResponseStatus.FAILURE, showResponseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(showResponseDTO.getShow(), "Show should be null");
    }

    @Test
    public void testCreateShow_UnsupportedFeatures_Failure(){
        screen.setFeatures(List.of(Feature.TWO_D));
        screen = screenRepository.save(screen);

        CreateShowRequestDTO createShowRequestDTO = new CreateShowRequestDTO();
        createShowRequestDTO.setMovieId(movie.getId());
        createShowRequestDTO.setScreenId(screen.getId());
        createShowRequestDTO.setStartTime(getTime(-1));
        createShowRequestDTO.setEndTime(getTime(2));
        createShowRequestDTO.setFeatures(List.of(Feature.DOLBY_ATMOS, Feature.IMAX, Feature.THREE_D));
        createShowRequestDTO.setPricingConfig(List.of(Pair.of(SeatType.GOLD, 500.0), Pair.of(SeatType.SILVER, 300.0)));
        createShowRequestDTO.setUserId(admin.getId());
        CreateShowResponseDTO showResponseDTO = showController.createShow(createShowRequestDTO);
        assertNotNull(showResponseDTO, "Create show response cannot be null");
        assertEquals(ResponseStatus.FAILURE, showResponseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(showResponseDTO.getShow(), "Show should be null");
    }

    @Test
    public void testCreateShow_UnAuthorizedAccess_Failure(){
        CreateShowRequestDTO createShowRequestDTO = new CreateShowRequestDTO();
        createShowRequestDTO.setMovieId(movie.getId());
        createShowRequestDTO.setScreenId(screen.getId());
        createShowRequestDTO.setStartTime(getTime(-1));
        createShowRequestDTO.setEndTime(getTime(2));
        createShowRequestDTO.setFeatures(List.of(Feature.DOLBY_ATMOS, Feature.IMAX, Feature.THREE_D));
        createShowRequestDTO.setPricingConfig(List.of(Pair.of(SeatType.GOLD, 500.0), Pair.of(SeatType.SILVER, 300.0)));
        createShowRequestDTO.setUserId(user.getId());
        CreateShowResponseDTO showResponseDTO = showController.createShow(createShowRequestDTO);
        assertNotNull(showResponseDTO, "Create show response cannot be null");
        assertEquals(ResponseStatus.FAILURE, showResponseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(showResponseDTO.getShow(), "Show should be null");
    }
}
