package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

   Optional<Inventory> findByProduct(Product product);

}
