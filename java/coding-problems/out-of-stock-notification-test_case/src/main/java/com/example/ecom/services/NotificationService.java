package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.Notification;

public interface NotificationService {

    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException;

    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException;
}
