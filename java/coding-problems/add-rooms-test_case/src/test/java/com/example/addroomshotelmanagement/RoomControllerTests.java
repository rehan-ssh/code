package com.example.addroomshotelmanagement;

import com.example.addroomshotelmanagement.controllers.RoomController;
import com.example.addroomshotelmanagement.dtos.AddRoomRequestDto;
import com.example.addroomshotelmanagement.dtos.AddRoomResponseDto;
import com.example.addroomshotelmanagement.dtos.ResponseStatus;
import com.example.addroomshotelmanagement.models.User;
import com.example.addroomshotelmanagement.models.UserType;
import com.example.addroomshotelmanagement.repositories.RoomRepository;
import com.example.addroomshotelmanagement.repositories.UserRepository;
import com.example.addroomshotelmanagement.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomControllerTests {
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private RoomService roomService;
    private RoomController roomController;

    @BeforeEach
    public void setupTest() throws Exception {
        initializeComponents();
    }

    private void initializeComponents() throws Exception {
        initializeRepositories();
        initializeRoomService();
        initializeRoomController();
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
    }

    private void initializeRoomService() throws Exception {
        Reflections serviceReflections = new Reflections(RoomService.class.getPackageName(), new SubTypesScanner(false));
        this.roomService = createInstanceWithArgs(RoomService.class, serviceReflections, Arrays.asList(this.roomRepository, this.userRepository));
    }

    private void initializeRoomController() {
        this.roomController = new RoomController(this.roomService);
    }

    @Test
    public void testAddRoomSuccess() {
        User adminUser = new User();
        adminUser.setUserType(UserType.ADMIN);
        adminUser.setName("admin");
        adminUser.setPassword("admin");
        adminUser.setPhone("1234567890");
        adminUser = userRepository.save(adminUser);

        AddRoomRequestDto requestDto = new AddRoomRequestDto();
        requestDto.setUserId(adminUser.getId());
        requestDto.setName("A1");
        requestDto.setDescription("Room number A1");
        requestDto.setPrice(5000.0);
        requestDto.setRoomType("DELUXE");
        AddRoomResponseDto responseDto = roomController.addRoom(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS, "AddRoomResponseDto status should be SUCCESS");
        assertNotNull(responseDto.getRoom(), "AddRoomResponseDto room should not be NULL");
    }

    @Test
    public void testAddRoomByCustomer() {
        User customerUser = new User();
        customerUser.setUserType(UserType.CUSTOMER);
        customerUser.setName("customer");
        customerUser.setPassword("customer");
        customerUser.setPhone("1234567890");
        customerUser = userRepository.save(customerUser);

        AddRoomRequestDto requestDto = new AddRoomRequestDto();
        requestDto.setUserId(customerUser.getId());
        requestDto.setName("A2");
        requestDto.setDescription("Room number A2");
        requestDto.setPrice(7000.0);
        requestDto.setRoomType("SUPER_DELUXE");
        AddRoomResponseDto responseDto = roomController.addRoom(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "AddRoomResponseDto status should be FAILURE as User is not an Admin");
        assertNull(responseDto.getRoom(), "AddRoomResponseDto room should be NULL");
    }


    @Test
    public void testAddRoomByNonExistingUser() {
        User adminUser = new User();
        adminUser.setUserType(UserType.ADMIN);
        adminUser.setName("admin");
        adminUser.setPassword("admin");
        adminUser.setPhone("1234567890");
        adminUser = userRepository.save(adminUser);

        AddRoomRequestDto requestDto = new AddRoomRequestDto();
        requestDto.setUserId(adminUser.getId() + 10);
        requestDto.setName("A1");
        requestDto.setDescription("Room number A1");
        requestDto.setPrice(5000.0);
        requestDto.setRoomType("DELUXE");
        AddRoomResponseDto responseDto = roomController.addRoom(requestDto);
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "AddRoomResponseDto status should be FAILURE as User is not an Admin");
        assertNull(responseDto.getRoom(), "AddRoomResponseDto room should be NULL");
    }
}
