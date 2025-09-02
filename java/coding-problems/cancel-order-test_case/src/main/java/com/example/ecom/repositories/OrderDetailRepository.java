package com.example.ecom.repositories;

import com.example.ecom.models.Order;
import com.example.ecom.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findAllByOrder(Order order);
}
