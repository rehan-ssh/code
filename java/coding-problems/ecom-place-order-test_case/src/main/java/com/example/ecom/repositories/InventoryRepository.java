package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="500")})

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAllByProductIdIn(List<Integer> ids);

}
