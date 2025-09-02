package com.scaler.parking_lot.controllers;

import com.scaler.parking_lot.dtos.*;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.*;
import com.scaler.parking_lot.services.InvoiceService;
import com.scaler.parking_lot.services.TicketService;
import com.scaler.parking_lot.strategies.assignment.SpotAssignmentStrategy;
import com.scaler.parking_lot.strategies.pricing.PricingStrategyFactory;
import com.scaler.parking_lot.strategies.pricing.WeekdayPricingStrategy;
import com.scaler.parking_lot.strategies.pricing.WeekendPricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TestControllers {

    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private SpotAssignmentStrategy spotAssignmentStrategy;
    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;
    private TicketService ticketService;
    private TicketController ticketController;
    private InvoiceService invoiceService;

    private InvoiceRepository invoiceRepository;
    private PricingStrategyFactory pricingStrategyFactory;

    private InvoiceController invoiceController;

    private SlabRepository slabRepository;

    @BeforeEach
    public void setupTest() throws Exception {
        initializeComponents();
    }

    private void initializeComponents() throws Exception {
        initializeRepositories();
        initializeStrategies();
        initializeServices();
        initializeControllers();
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
        Reflections repositoryReflections = new Reflections(TicketRepository.class.getPackageName(), new SubTypesScanner(false));
        this.gateRepository = createInstance(GateRepository.class, repositoryReflections);
        this.vehicleRepository = createInstance(VehicleRepository.class, repositoryReflections);
        this.parkingLotRepository = createInstance(ParkingLotRepository.class, repositoryReflections);
        this.ticketRepository = createInstance(TicketRepository.class, repositoryReflections);
        this.invoiceRepository = createInstance(InvoiceRepository.class, repositoryReflections);
        this.slabRepository = createInstance(SlabRepository.class, repositoryReflections);
    }

    private void initializeStrategies() throws Exception {
        Reflections strategyReflections = new Reflections(SpotAssignmentStrategy.class.getPackageName(), new SubTypesScanner(false));
        this.spotAssignmentStrategy = createInstance(SpotAssignmentStrategy.class, strategyReflections);
        this.pricingStrategyFactory = new PricingStrategyFactory(this.slabRepository);
    }

    private void initializeServices() throws Exception {
        Reflections serviceReflections = new Reflections(TicketService.class.getPackageName(), new SubTypesScanner(false));
        this.ticketService = createInstanceWithArgs(TicketService.class, serviceReflections, Arrays.asList(this.gateRepository, this.vehicleRepository, this.spotAssignmentStrategy, this.parkingLotRepository, this.ticketRepository));
        this.invoiceService = createInstanceWithArgs(InvoiceService.class, serviceReflections, Arrays.asList(this.ticketRepository, this.pricingStrategyFactory, this.gateRepository, this.invoiceRepository));
    }

    private void initializeControllers() {
        this.ticketController = new TicketController(this.ticketService);
        this.invoiceController = new InvoiceController(this.invoiceService);
    }

    @Test
    public void testIssueTicketWithNoAdditionalServices(){
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        GenerateTicketRequestDto requestDto = new GenerateTicketRequestDto();
        requestDto.setGateId(1);
        requestDto.setRegistrationNumber("KA-01-HH-1234");
        requestDto.setVehicleType("CAR");
        GenerateTicketResponseDto responseDto = ticketController.generateTicket(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Response status should be success");
        assertNotNull(responseDto.getTicket(), "Ticket should not be null");
        assertTrue(responseDto.getTicket().getAdditionalServices() == null || responseDto.getTicket().getAdditionalServices().isEmpty(), "Additional services should be empty");
    }

    @Test
    public void testIssueTicketWith1AdditionalService(){
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        GenerateTicketRequestDto requestDto = new GenerateTicketRequestDto();
        requestDto.setGateId(1);
        requestDto.setRegistrationNumber("KA-01-HH-1234");
        requestDto.setVehicleType("CAR");
        requestDto.setAdditionalServices(List.of("CAR_WASH"));
        GenerateTicketResponseDto responseDto = ticketController.generateTicket(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Response status should be success");
        assertNotNull(responseDto.getTicket(), "Ticket should not be null");
        assertEquals(responseDto.getTicket().getAdditionalServices().size(), 1, "Additional services size should be 1");
        assertEquals(responseDto.getTicket().getAdditionalServices().get(0), AdditionalService.CAR_WASH, "Additional service should be CAR_WASH");
    }

    @Test
    public void testIssueTicketWithMultipleAdditionalServices(){
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        GenerateTicketRequestDto requestDto = new GenerateTicketRequestDto();
        requestDto.setGateId(1);
        requestDto.setRegistrationNumber("KA-01-HH-1234");
        requestDto.setVehicleType("CAR");
        requestDto.setAdditionalServices(List.of("CAR_WASH", "CAR_DETAILING"));
        GenerateTicketResponseDto responseDto = ticketController.generateTicket(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Response status should be success");
        assertNotNull(responseDto.getTicket(), "Ticket should not be null");
        assertEquals(responseDto.getTicket().getAdditionalServices().size(), 2, "Additional services size should be 2");
        assertTrue(responseDto.getTicket().getAdditionalServices().stream().anyMatch(additionalService -> additionalService.equals(AdditionalService.CAR_WASH)), "Additional service should be CAR_WASH");
        assertTrue(responseDto.getTicket().getAdditionalServices().stream().anyMatch(additionalService -> additionalService.equals(AdditionalService.CAR_DETAILING)), "Additional service should be CAR_DETAILING");
    }

    @Test
    public void testIssueTicketWithInvalidAdditionalService(){
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        GenerateTicketRequestDto requestDto = new GenerateTicketRequestDto();
        requestDto.setGateId(1);
        requestDto.setRegistrationNumber("KA-01-HH-1234");
        requestDto.setVehicleType("CAR");
        requestDto.setAdditionalServices(List.of("CAR_DEEP_CLEANING"));
        GenerateTicketResponseDto responseDto = ticketController.generateTicket(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be failure");
        assertNull(responseDto.getTicket(), "Ticket should be null");
    }

    @Test
    public void testIssueTicketWithAdditionalServiceNotSupportedByVehicle(){
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        GenerateTicketRequestDto requestDto = new GenerateTicketRequestDto();
        requestDto.setGateId(1);
        requestDto.setRegistrationNumber("KA-01-HH-1234");
        requestDto.setVehicleType("CAR");
        requestDto.setAdditionalServices(List.of("BIKE_WASH", "CAR_DETAILING"));
        GenerateTicketResponseDto responseDto = ticketController.generateTicket(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be failure");
        assertNull(responseDto.getTicket(), "Ticket should be null");
    }

    @Test
    public void testGenerateInvoice_WithNoAdditionalServices_Weekday() throws Exception {
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("KA-01-HH-1234");
        vehicle.setVehicleType(VehicleType.CAR);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(3, 30));
        ticket.setParkingSpot(new ParkingSpot());
        Gate gate = gateRepository.findById(1L).get();
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(new ArrayList<>());
        ticket = ticketRepository.save(ticket);

        PricingStrategyFactory mockedPricingStrategy = Mockito.mock(PricingStrategyFactory.class);
        when(mockedPricingStrategy.getPricingStrategy(Mockito.any(Date.class))).thenReturn(new WeekdayPricingStrategy());
        this.invoiceService = createInstanceWithArgs(InvoiceService.class, new Reflections(TicketService.class.getPackageName(), new SubTypesScanner(false)), Arrays.asList(this.ticketRepository, mockedPricingStrategy, this.gateRepository, this.invoiceRepository));
        this.invoiceController = new InvoiceController(this.invoiceService);

        GenerateInvoiceRequestDto invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        GenerateInvoiceResponseDto generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(generateInvoiceResponseDto.getStatus(), ResponseStatus.SUCCESS, "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals(generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), 0, "Additional services size should be 0");
        assertEquals(generateInvoiceResponseDto.getInvoice().getAmount(), 40, "Amount should be 40");

    }

    @Test
    public void testGenerateInvoice_WithNoAdditionalServices_Weekend() throws Exception {
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        setupSlabs();
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("KA-01-HH-1234");
        vehicle.setVehicleType(VehicleType.CAR);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(1, 30));
        ticket.setParkingSpot(new ParkingSpot());
        Gate gate = gateRepository.findById(1L).get();
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(new ArrayList<>());
        ticket = ticketRepository.save(ticket);

        PricingStrategyFactory mockedPricingStrategy = Mockito.mock(PricingStrategyFactory.class);
        when(mockedPricingStrategy.getPricingStrategy(Mockito.any(Date.class))).thenReturn(new WeekendPricingStrategy(this.slabRepository));
        this.invoiceService = createInstanceWithArgs(InvoiceService.class, new Reflections(TicketService.class.getPackageName(), new SubTypesScanner(false)), Arrays.asList(this.ticketRepository, mockedPricingStrategy, this.gateRepository, this.invoiceRepository));
        this.invoiceController = new InvoiceController(this.invoiceService);

        GenerateInvoiceRequestDto invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        GenerateInvoiceResponseDto generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(generateInvoiceResponseDto.getStatus(), ResponseStatus.SUCCESS, "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals(generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), 0, "Additional services size should be 0");
        assertEquals(40, generateInvoiceResponseDto.getInvoice().getAmount(), "Amount should be 40");

        //2nd slab
        ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(2, 30));
        ticket.setParkingSpot(new ParkingSpot());
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(new ArrayList<>());
        ticket = ticketRepository.save(ticket);

        invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(ResponseStatus.SUCCESS, generateInvoiceResponseDto.getStatus(), "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals( 0, generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), "Additional services size should be 0");
        assertEquals(70, generateInvoiceResponseDto.getInvoice().getAmount(), "Amount should be 70");

        //3rd slab
        ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(9, 55));
        ticket.setParkingSpot(new ParkingSpot());
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(new ArrayList<>());
        ticket = ticketRepository.save(ticket);

        invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(ResponseStatus.SUCCESS, generateInvoiceResponseDto.getStatus(), "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals( 0, generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), "Additional services size should be 0");
        assertEquals(380, generateInvoiceResponseDto.getInvoice().getAmount(), "Amount should be 400");
    }

    @Test
    public void testGenerateInvoice_WithAdditionalServices_Weekend() throws Exception {
        insertDummyData(1, Map.of(VehicleType.CAR, 1), 1, 1, "Bangalore");
        setupSlabs();
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("KA-01-HH-1234");
        vehicle.setVehicleType(VehicleType.CAR);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(1, 30));
        ticket.setParkingSpot(new ParkingSpot());
        Gate gate = gateRepository.findById(1L).get();
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(List.of(AdditionalService.CAR_WASH, AdditionalService.CAR_DETAILING));
        ticket = ticketRepository.save(ticket);

        PricingStrategyFactory mockedPricingStrategy = Mockito.mock(PricingStrategyFactory.class);
        when(mockedPricingStrategy.getPricingStrategy(Mockito.any(Date.class))).thenReturn(new WeekendPricingStrategy(this.slabRepository));
        this.invoiceService = createInstanceWithArgs(InvoiceService.class, new Reflections(TicketService.class.getPackageName(), new SubTypesScanner(false)), Arrays.asList(this.ticketRepository, mockedPricingStrategy, this.gateRepository, this.invoiceRepository));
        this.invoiceController = new InvoiceController(this.invoiceService);

        GenerateInvoiceRequestDto invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        GenerateInvoiceResponseDto generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(generateInvoiceResponseDto.getStatus(), ResponseStatus.SUCCESS, "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals(generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), 2, "Additional services size should be 2");
        assertEquals(740, generateInvoiceResponseDto.getInvoice().getAmount(), "Amount should be 740");

        //2nd slab
        ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(2, 30));
        ticket.setParkingSpot(new ParkingSpot());
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(List.of(AdditionalService.CAR_WASH, AdditionalService.CAR_DETAILING));
        ticket = ticketRepository.save(ticket);

        invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(ResponseStatus.SUCCESS, generateInvoiceResponseDto.getStatus(), "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals(generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), 2, "Additional services size should be 2");
        assertEquals(770, generateInvoiceResponseDto.getInvoice().getAmount(), "Amount should be 770");

        //3rd slab
        ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(subtractHoursFromDate(9, 55));
        ticket.setParkingSpot(new ParkingSpot());
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setAdditionalServices(List.of(AdditionalService.CAR_WASH, AdditionalService.CAR_DETAILING));
        ticket = ticketRepository.save(ticket);

        invoiceRequestDto = new GenerateInvoiceRequestDto();
        invoiceRequestDto.setTicketId(ticket.getId());
        invoiceRequestDto.setGateId(1L);
        generateInvoiceResponseDto = invoiceController.createInvoice(invoiceRequestDto);
        assertEquals(ResponseStatus.SUCCESS, generateInvoiceResponseDto.getStatus(), "Response status should be success");
        assertNotNull(generateInvoiceResponseDto.getInvoice(), "Invoice should not be null");
        assertEquals(generateInvoiceResponseDto.getInvoice().getTicket().getAdditionalServices().size(), 2, "Additional services size should be 2");
        assertEquals(1080, generateInvoiceResponseDto.getInvoice().getAmount(), "Amount should be 1100");
    }

    private void setupSlabs() {
        // Slab 1: 0-2 hours
        Slab slab1 = new Slab();
        slab1.setStartHour(0);
        slab1.setEndHour(2);
        slab1.setPrice(20); // Rs. 20 per hour
        slab1.setVehicleType(VehicleType.CAR);

        // Slab 2: 2-4 hours
        Slab slab2 = new Slab();
        slab2.setStartHour(2);
        slab2.setEndHour(4);
        slab2.setPrice(30); // Rs. 30 per hour
        slab2.setVehicleType(VehicleType.CAR);

        // Slab 3: 4-6 hours
        Slab slab3 = new Slab();
        slab3.setStartHour(4);
        slab3.setEndHour(6);
        slab3.setPrice(40); // Rs. 40 per hour
        slab3.setVehicleType(VehicleType.CAR);

        // Slab 4: 6+ hours
        Slab slab4 = new Slab();
        slab4.setStartHour(6);
        slab4.setEndHour(-1); // -1 indicates no upper limit
        slab4.setPrice(50); // Rs. 50 per hour
        slab4.setVehicleType(VehicleType.CAR);

        // Save slabs to the repository
        this.slabRepository.save(slab1);
        this.slabRepository.save(slab2);
        this.slabRepository.save(slab3);
        this.slabRepository.save(slab4);
    }

    public static Date subtractHoursFromDate(int hours, int mins) {
        // Getting the current date and time
        Date currentDate = new Date();

        // Creating a Calendar instance and setting it to the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Subtracting the specified number of hours from the current date and time
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        calendar.add(Calendar.MINUTE, -mins);

        // Getting the modified date and time
        Date newDate = calendar.getTime();

        return newDate;
    }

    public void insertDummyData(int numOfFloors, Map<VehicleType, Integer> numOfSpotsPerVehicleTypePerFloor, int numOfEntryGates, int numOfExitGates, String address) {
        ParkingLot parkingLot = setupParkingLot(numOfFloors, numOfSpotsPerVehicleTypePerFloor, numOfEntryGates, numOfExitGates, address);
        parkingLotRepository.save(parkingLot);
        parkingLot.getGates().forEach(gate -> gateRepository.save(gate));
    }
    public ParkingLot setupParkingLot(int numOfFloors, Map<VehicleType, Integer> numOfSpotsPerVehicleTypePerFloor, int numOfEntryGates, int numOfExitGates, String address){
        int parkingSpotId = 1;
        int parkingFloorId = 1;
        List<ParkingFloor> parkingFloors = new ArrayList<>();
        for(int i=0; i<numOfFloors; i++){
            List<ParkingSpot> spots = new ArrayList<>();
            for(Map.Entry<VehicleType, Integer> entry: numOfSpotsPerVehicleTypePerFloor.entrySet()){
                for(int j=0; j<entry.getValue(); j++){
                    parkingSpotId++;
                    ParkingSpot parkingSpot = new ParkingSpot();
                    parkingSpot.setId(parkingSpotId);
                    parkingSpot.setSupportedVehicleType(entry.getKey());
                    parkingSpot.setStatus(ParkingSpotStatus.AVAILABLE);
                    parkingSpot.setNumber(parkingSpotId);
                    spots.add(parkingSpot);
                }
            }
            ParkingFloor parkingFloor = new ParkingFloor();
            parkingFloor.setId(parkingFloorId++);
            parkingFloor.setSpots(spots);
            parkingFloor.setFloorNumber(parkingFloorId-1);
            parkingFloor.setStatus(FloorStatus.OPERATIONAL);
            parkingFloors.add(parkingFloor);
        }
        List<Gate> gates = new ArrayList<>();
        int parkingAttendantId = 1;
        for(int i=0; i<numOfEntryGates; i++){
            ParkingAttendant parkingAttendant = new ParkingAttendant();
            parkingAttendant.setId(parkingAttendantId);
            parkingAttendant.setName(String.valueOf(parkingAttendantId));
            parkingAttendant.setEmail(parkingAttendantId+"@gmail.com");
            Gate gate = new Gate();
            gate.setId(parkingAttendantId);
            gate.setName(String.valueOf(parkingAttendantId));
            gate.setType(GateType.ENTRY);
            gate.setParkingAttendant(parkingAttendant);
            gates.add(gate);
            parkingAttendantId++;
        }
        for(int i=0; i<numOfExitGates; i++){
            ParkingAttendant parkingAttendant = new ParkingAttendant();
            parkingAttendant.setId(parkingAttendantId);
            parkingAttendant.setName(String.valueOf(parkingAttendantId));
            parkingAttendant.setEmail(parkingAttendantId+"@gmail.com");
            Gate gate = new Gate();
            gate.setId(parkingAttendantId);
            gate.setName(String.valueOf(parkingAttendantId));
            gate.setType(GateType.EXIT);
            gate.setParkingAttendant(parkingAttendant);
            gates.add(gate);
            parkingAttendantId++;
        }
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1);
        parkingLot.setParkingFloors(parkingFloors);
        parkingLot.setGates(gates);
        parkingLot.setName("Test Parking Lot");
        parkingLot.setAddress(address);
        return parkingLot;
    }
}
