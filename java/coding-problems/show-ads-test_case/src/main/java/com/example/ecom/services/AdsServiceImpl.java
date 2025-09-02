package com.example.ecom.services;

import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Advertisement;
import com.example.ecom.models.Preference;
import com.example.ecom.models.User;
import com.example.ecom.repositories.AdvertisementRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdsServiceImpl implements AdsService{

    private UserRepository userRepository;
    private AdvertisementRepository advertisementRepository;

    @Autowired
    public AdsServiceImpl(UserRepository userRepository, AdvertisementRepository advertisementRepository) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public Advertisement getAdvertisementForUser(int userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        List<Preference> preferences = user.getPreferences();
        if(preferences.isEmpty()){
            return getRandomAd(advertisementRepository.findAll());
        }
        List<Advertisement> advertisements = advertisementRepository.findAdvertisementsByPreferenceIn(preferences);
        return getRandomAd(advertisements);
    }

    private Advertisement getRandomAd(List<Advertisement> advertisements){
        int randomIndex = (int) (Math.random() * advertisements.size());
        return advertisements.get(randomIndex);
    }
}
