package com.example.splitwise.repositories;

import com.example.splitwise.models.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Long> {

    Optional<GroupAdmin> findByGroupIdAndAdminId(Long groupId, Long adminId);

    List<GroupAdmin> findAllByGroupId(Long groupId);

}
