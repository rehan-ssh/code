package com.example.ecom.controllers;

import com.example.ecom.dtos.GetAdvertisementForUserRequestDto;
import com.example.ecom.dtos.GetAdvertisementForUserResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.Advertisement;
import com.example.ecom.models.Preference;
import com.example.ecom.models.User;
import com.example.ecom.repositories.AdvertisementRepository;
import com.example.ecom.repositories.PreferencesRepository;
import com.example.ecom.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestAdsController {

    @Autowired
    private AdsController adsController;
    @Autowired
    private PreferencesRepository preferencesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdvertisementRepository advertisementRepository;

    private User user1;

    @BeforeEach
    public void insertDummyData(){

        Preference preference1 = new Preference();
        preference1.setCategory("Electronics");
        preference1.setDescription("Electronics");
        preference1.setCreatedAt(new Date());
        preference1 = preferencesRepository.save(preference1);

        Preference preference2 = new Preference();
        preference2.setCategory("Furniture");
        preference2.setDescription("Furniture");
        preference2.setCreatedAt(new Date());
        preference2 = preferencesRepository.save(preference2);

        Preference preference3 = new Preference();
        preference3.setCategory("Clothing");
        preference3.setDescription("Clothing");
        preference3.setCreatedAt(new Date());
        preference3 = preferencesRepository.save(preference3);

        Advertisement advertisement1 = new Advertisement();
        advertisement1.setPreference(preference1);
        advertisement1.setName("iPhone 15");
        advertisement1.setDescription("Newphoria.");
        advertisement1 = advertisementRepository.save(advertisement1);

        Advertisement advertisement2 = new Advertisement();
        advertisement2.setPreference(preference2);
        advertisement2.setName("Wakefit Smart Mattress");
        advertisement2.setDescription("Sleep like a baby.");
        advertisement2 = advertisementRepository.save(advertisement2);

        Advertisement advertisement3 = new Advertisement();
        advertisement3.setPreference(preference3);
        advertisement3.setName("Levis Jeans");
        advertisement3.setDescription("Live Unbuttoned.");
        advertisement3 = advertisementRepository.save(advertisement3);

        user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john@gmail.com");
        user1.setPreferences(Arrays.asList(preference1, preference2));
        user1 = userRepository.save(user1);

    }

    @Test
    public void testGetAdsForUser_UserNotFoundException(){
        GetAdvertisementForUserRequestDto requestDto = new GetAdvertisementForUserRequestDto();
        requestDto.setUserId(100);
        GetAdvertisementForUserResponseDto responseDto = adsController.getAdvertisementForUser(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be FAILURE");
    }

    @Test
    public void testGetAdsForUser(){
        GetAdvertisementForUserRequestDto requestDto = new GetAdvertisementForUserRequestDto();
        requestDto.setUserId(user1.getId());
        GetAdvertisementForUserResponseDto responseDto = adsController.getAdvertisementForUser(requestDto);
        assertNotNull(responseDto, "Response dto should not be null");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus(), "Response status should be SUCCESS");
        assertNotNull(responseDto.getAdvertisement(), "Advertisement should not be null");
        List<String> preferences = user1.getPreferences().stream().map(Preference::getCategory).collect(Collectors.toList());
        Advertisement advertisement = responseDto.getAdvertisement();
        assertTrue(preferences.contains(advertisement.getPreference().getCategory()), "Advertisement should be based on user's preferences");
    }
}
