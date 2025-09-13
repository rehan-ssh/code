package com.example.generateinvoices;

import com.example.generateinvoices.controllers.BookingController;
import com.example.generateinvoices.dtos.GenerateInvoiceRequestDto;
import com.example.generateinvoices.dtos.GenerateInvoiceResponseDto;
import com.example.generateinvoices.dtos.ResponseStatus;
import com.example.generateinvoices.models.*;
import com.example.generateinvoices.repositories.BookingRepository;
import com.example.generateinvoices.repositories.CustomerSessionRepository;
import com.example.generateinvoices.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenerateInvoiceTests {
    private BookingController bookingController;
    private BookingRepository bookingRepository;
    private CustomerSessionRepository customerSessionRepository;
    private BookingService bookingService;

    @BeforeEach
    public void setupTest() throws Exception {
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
        Reflections repositoryReflections = new Reflections(CustomerSessionRepository.class.getPackageName(), new SubTypesScanner(false));
        this.customerSessionRepository = createInstance(CustomerSessionRepository.class, repositoryReflections);
        this.bookingRepository = createInstance(BookingRepository.class, repositoryReflections);
    }

    private void initializeBookingService() throws Exception {
        Reflections serviceReflections = new Reflections(BookingService.class.getPackageName(), new SubTypesScanner(false));
        this.bookingService = createInstanceWithArgs(BookingService.class, serviceReflections, Arrays.asList(this.customerSessionRepository, this.bookingRepository));
    }

    private void initializeBookingController() {
        this.bookingController = new BookingController(this.bookingService);
    }

    @Test
    public void testGenerateInvoiceSuccess1() {
        User user = setupUser();
        List<Room> rooms = setupRooms();

        Map<Room, Integer> bookedRooms = new HashMap<>();
        bookedRooms.put(rooms.get(0), 2);
        bookedRooms.put(rooms.get(1), 1);

        CustomerSession customerSession = new CustomerSession();
        customerSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
        customerSession.setUser(user);
        customerSession = customerSessionRepository.save(customerSession);

        Booking booking = new Booking();
        booking.setCustomerSession(customerSession);
        booking.setBookedRooms(bookedRooms);
        booking = bookingRepository.save(booking);

        GenerateInvoiceRequestDto requestDto = new GenerateInvoiceRequestDto();
        requestDto.setUserId(user.getId());
        GenerateInvoiceResponseDto responseDto = bookingController.generateInvoice(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Generate Invoice should be SUCCESS");
        assertNotNull(responseDto.getInvoice(), "Invoice should not be NULL");

        Invoice invoice = responseDto.getInvoice();
        assertEquals(2, invoice.getBookedRooms().size(), "Invoice should have 2 rooms");

        double totalAmount = 0;
        for (Map.Entry<Room, Integer> entry : bookedRooms.entrySet()) {
            totalAmount += rooms.stream().filter(room -> room.getId() == entry.getKey().getId()).findFirst().get().getPrice() * entry.getValue();
        }
        assertEquals(totalAmount * 1.28, invoice.getTotalAmount(), 0.1, "Total amount should be correct");
        assertEquals(totalAmount * 0.18, invoice.getGst(), 0.1, "GST should be correct");
        assertEquals(totalAmount * 0.1, invoice.getServiceCharge(), 0.1, "Service charge should be correct");
    }

    @Test
    public void testGenerateInvoiceSuccess2() {
        User user = setupUser();
        List<Room> rooms = setupRooms();

        CustomerSession customerSession = new CustomerSession();
        customerSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
        customerSession.setUser(user);
        customerSession = customerSessionRepository.save(customerSession);

        Map<Room, Integer> bookedRooms1 = new HashMap<>();
        bookedRooms1.put(rooms.get(0), 2);
        bookedRooms1.put(rooms.get(1), 1);

        Booking booking1 = new Booking();
        booking1.setBookedRooms(bookedRooms1);
        booking1.setCustomerSession(customerSession);
        booking1 = bookingRepository.save(booking1);

        Map<Room, Integer> bookedRooms2 = new HashMap<>();
        bookedRooms2.put(rooms.get(1), 1);
        bookedRooms2.put(rooms.get(2), 1);
        bookedRooms2.put(rooms.get(3), 2);

        Booking booking2 = new Booking();
        booking2.setCustomerSession(customerSession);
        booking2.setBookedRooms(bookedRooms2);
        booking2 = bookingRepository.save(booking2);

        GenerateInvoiceRequestDto requestDto = new GenerateInvoiceRequestDto();
        requestDto.setUserId(user.getId());
        GenerateInvoiceResponseDto responseDto = bookingController.generateInvoice(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Generate Invoice should be SUCCESS");
        assertNotNull(responseDto.getInvoice(), "Invoice should not be NULL");

        Invoice invoice = responseDto.getInvoice();
        assertEquals(4, invoice.getBookedRooms().size(), "Invoice should have 4 rooms");

        double totalAmount = 0;
        for (Map.Entry<Room, Integer> entry : bookedRooms1.entrySet()) {
            totalAmount += rooms.stream().filter(room -> room.getId() == entry.getKey().getId()).findFirst().get().getPrice() * entry.getValue();
        }

        for (Map.Entry<Room, Integer> entry : bookedRooms2.entrySet()) {
            totalAmount += rooms.stream().filter(room -> room.getId() == entry.getKey().getId()).findFirst().get().getPrice() * entry.getValue();
        }
        assertEquals(totalAmount * 1.28, invoice.getTotalAmount(), 0.1, "Total amount should be correct");
        assertEquals(totalAmount * 0.18, invoice.getGst(), 0.1, "GST should be correct");
        assertEquals(totalAmount * 0.1, invoice.getServiceCharge(), 0.1, "Service charge should be correct");
    }

    @Test
    public void testGenerateInvoiceForInvalidCustomerSession() {
        User user = setupUser();
        List<Room> rooms = setupRooms();

        CustomerSession customerSession = new CustomerSession();
        customerSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
        customerSession.setUser(user);
        customerSession = customerSessionRepository.save(customerSession);

        Map<Room, Integer> bookedRooms = new HashMap<>();
        bookedRooms.put(rooms.get(0), 2);
        bookedRooms.put(rooms.get(1), 1);

        Booking booking = new Booking();
        booking.setCustomerSession(customerSession);
        booking.setBookedRooms(bookedRooms);
        booking = bookingRepository.save(booking);

        GenerateInvoiceRequestDto requestDto = new GenerateInvoiceRequestDto();
        requestDto.setUserId(user.getId() + 1);
        GenerateInvoiceResponseDto responseDto = bookingController.generateInvoice(requestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Generate Invoice should be FAILURE");
        assertNull(responseDto.getInvoice(), "Invoice should be NULL");
    }

    public User setupUser(){
        User user = new User();
        user.setName("Test User");
        user.setPhone("1234567890");
        user.setUserType(UserType.CUSTOMER);
        user.setId(1L);
        return user;
    }

    private List<Room> setupRooms() {
        List<Room> rooms = new ArrayList<>();
        Room room = new Room();
        room.setRoomNumber("A1");
        room.setDescription("Room number A1");
        room.setRoomType(RoomType.DELUXE);
        room.setPrice(5000.0);
        room.setId(1L);
        rooms.add(room);

        room = new Room();
        room.setRoomNumber("A2");
        room.setDescription("Room number A2");
        room.setRoomType(RoomType.SUPER_DELUXE);
        room.setPrice(7000.0);
        room.setId(2L);
        rooms.add(room);

        room = new Room();
        room.setRoomNumber("A3");
        room.setDescription("Room number A3");
        room.setRoomType(RoomType.SUITE);
        room.setPrice(10000.0);
        room.setId(3L);
        rooms.add(room);

        room = new Room();
        room.setRoomNumber("B1");
        room.setDescription("Room number B1");
        room.setRoomType(RoomType.DELUXE);
        room.setPrice(4000.0);
        room.setId(4L);
        rooms.add(room);

        room = new Room();
        room.setRoomNumber("B2");
        room.setDescription("Room number B2");
        room.setRoomType(RoomType.SUPER_DELUXE);
        room.setPrice(6000.0);
        room.setId(5L);
        rooms.add(room);

        room = new Room();
        room.setRoomNumber("B3");
        room.setDescription("Room number B3");
        room.setRoomType(RoomType.SUITE);
        room.setPrice(9000.0);
        room.setId(6L);
        rooms.add(room);

        return rooms;
    }
}
