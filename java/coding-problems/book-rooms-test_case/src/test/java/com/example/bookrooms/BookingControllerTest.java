package com.example.bookrooms;

import com.example.bookrooms.controllers.BookingController;
import com.example.bookrooms.dtos.MakeBookingRequestDto;
import com.example.bookrooms.dtos.MakeBookingResponseDto;
import com.example.bookrooms.dtos.ResponseStatus;
import com.example.bookrooms.models.*;
import com.example.bookrooms.repositories.BookingRepository;
import com.example.bookrooms.repositories.CustomerSessionRepository;
import com.example.bookrooms.repositories.RoomRepository;
import com.example.bookrooms.repositories.UserRepository;
import com.example.bookrooms.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingControllerTest {
    private BookingController bookingController;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private CustomerSessionRepository customerSessionRepository;
    private BookingService bookingService;

    @BeforeEach
    public void setupTest() throws Exception {
        initializeComponents();
    }

    private void initializeComponents() throws Exception {
        initializeRepositories();
        initializeBookingService();
        initializeBookingController();
    }

    private <T> T createInstance(Class<T> interfaceClass, Reflections reflections) throws Exception {
        Set<Class<? extends T>> implementations = reflections.getSubTypesOf(interfaceClass);
        if (implementations.isEmpty()) {
            throw new Exception("No implementation for " + interfaceClass.getSimpleName() + " found");
        }

        Class<? extends T> implementationClass = implementations.iterator().next();
        Constructor<? extends T> constructor = implementationClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private <T> T createInstanceWithArgs(Class<T> interfaceClass, Reflections reflections, List<Object> dependencies) throws Exception {
        Set<Class<? extends T>> implementations = reflections.getSubTypesOf(interfaceClass);
        if (implementations.isEmpty()) {
            throw new Exception("No implementation for " + interfaceClass.getSimpleName() + " found");
        }
        Class<? extends T> implementationClass = implementations.iterator().next();
        Constructor<?>[] constructors = implementationClass.getConstructors();
        Constructor<?> constructor = Arrays.stream(constructors)
                .filter(constructor1 -> constructor1.getParameterCount() == dependencies.size())
                .findFirst().orElseThrow(() -> new Exception("No constructor with " + dependencies.size() + " arguments found"));
        constructor.setAccessible(true);
        Object[] args = new Object[constructor.getParameterCount()];
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            for (Object dependency : dependencies) {
                if (constructor.getParameterTypes()[i].isInstance(dependency)) {
                    args[i] = dependency;
                    break;
                }
            }
        }
        return (T) constructor.newInstance(args);
    }

    private void initializeRepositories() throws Exception {
        Reflections repositoryReflections = new Reflections(UserRepository.class.getPackageName(), new SubTypesScanner(false));
        this.roomRepository = createInstance(RoomRepository.class, repositoryReflections);
        this.userRepository = createInstance(UserRepository.class, repositoryReflections);
        this.customerSessionRepository = createInstance(CustomerSessionRepository.class, repositoryReflections);
        this.bookingRepository = createInstance(BookingRepository.class, repositoryReflections);
    }

    private void initializeBookingService() throws Exception {
        Reflections serviceReflections = new Reflections(BookingService.class.getPackageName(), new SubTypesScanner(false));
        this.bookingService = createInstanceWithArgs(BookingService.class, serviceReflections, Arrays.asList(this.roomRepository, this.userRepository, this.customerSessionRepository, this.bookingRepository));
    }

    private void initializeBookingController() {
        this.bookingController = new BookingController(this.bookingService);
    }

    @Test
    public void testBookRoomSuccess() {
        User user = setupUser();
        List<Room> rooms = setupRooms();

        MakeBookingRequestDto requestDto = new MakeBookingRequestDto();
        requestDto.setUserId(user.getId());
        Map<Long, Integer> bookedRooms = new HashMap<>();
        bookedRooms.put(rooms.get(0).getId(), 2);
        bookedRooms.put(rooms.get(1).getId(), 1);
        requestDto.setBookedRooms(bookedRooms);

        MakeBookingResponseDto responseDto = bookingController.makeBooking(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Make booking should be SUCCESSFUL");
        assertNotNull(responseDto.getBooking(), "Booking shouldn't be NULL");

        Booking booking = responseDto.getBooking();
        assertEquals(2, booking.getBookedRooms().size(), "Booking should have 2 rooms");

        booking.getBookedRooms().forEach((room, qty) -> {
            if (room.getId() == rooms.get(0).getId()) {
                assertEquals(2, qty, "Room number A1 should have been booked 2 times");
            } else if (room.getId() == rooms.get(1).getId()) {
                assertEquals(1, qty, "Room number A2 should have been booked 1 times");
            } else {
                fail();
            }
        } );
    }

    @Test
    public void testBookRoomUserDoesntExists(){
        User user = setupUser();
        List<Room> rooms = setupRooms();

        MakeBookingRequestDto requestDto = new MakeBookingRequestDto();
        requestDto.setUserId(user.getId() + 1);
        Map<Long, Integer> bookedRooms = new HashMap<>();
        bookedRooms.put(rooms.get(0).getId(), 2);
        bookedRooms.put(rooms.get(1).getId(), 1);
        requestDto.setBookedRooms(bookedRooms);

        MakeBookingResponseDto responseDto = bookingController.makeBooking(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Make booking should be FAILURE");
        assertNull(responseDto.getBooking(), "Booking should be NULL");
    }

    @Test
    public void testBookRoomInvalidRoom(){
        User user = setupUser();
        List<Room> rooms = setupRooms();

        MakeBookingRequestDto requestDto = new MakeBookingRequestDto();
        requestDto.setUserId(user.getId());
        Map<Long, Integer> bookedRooms = new HashMap<>();
        bookedRooms.put(rooms.get(0).getId() + 100, 2);
        bookedRooms.put(rooms.get(1).getId() + 200, 1);
        requestDto.setBookedRooms(bookedRooms);

        MakeBookingResponseDto responseDto = bookingController.makeBooking(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Make booking should be FAILURE");
        assertNull(responseDto.getBooking(), "Booking should be NULL");
    }

    private User setupUser(){
        User user = new User();
        user.setName("Test User");
        user.setPhone("123456789");
        user.setUserType(UserType.CUSTOMER);
        return userRepository.save(user);
    }

    private List<Room> setupRooms() {
        List<Room> rooms = new ArrayList<>();
        Room room = new Room();
        room.setRoomNumber("A1");
        room.setDescription("Room number A1");
        room.setRoomType(RoomType.DELUXE);
        room.setPrice(5000.0);
        room.setId(1L);
        rooms.add(roomRepository.save(room));

        room = new Room();
        room.setRoomNumber("A2");
        room.setDescription("Room number A2");
        room.setRoomType(RoomType.SUPER_DELUXE);
        room.setPrice(7000.0);
        room.setId(2L);
        rooms.add(roomRepository.save(room));

        room = new Room();
        room.setRoomNumber("A3");
        room.setDescription("Room number A3");
        room.setRoomType(RoomType.SUITE);
        room.setPrice(10000.0);
        room.setId(3L);
        rooms.add(roomRepository.save(room));

        room = new Room();
        room.setRoomNumber("B1");
        room.setDescription("Room number B1");
        room.setRoomType(RoomType.DELUXE);
        room.setPrice(4000.0);
        room.setId(4L);
        rooms.add(roomRepository.save(room));

        room = new Room();
        room.setRoomNumber("B2");
        room.setDescription("Room number B2");
        room.setRoomType(RoomType.SUPER_DELUXE);
        room.setPrice(6000.0);
        room.setId(5L);
        rooms.add(roomRepository.save(room));

        room = new Room();
        room.setRoomNumber("B3");
        room.setDescription("Room number B3");
        room.setRoomType(RoomType.SUITE);
        room.setPrice(9000.0);
        room.setId(6L);
        rooms.add(roomRepository.save(room));

        return rooms;
    }
}
