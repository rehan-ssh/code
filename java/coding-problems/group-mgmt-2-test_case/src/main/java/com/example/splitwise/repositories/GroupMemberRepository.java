package com.example.splitwise.repositories;

import com.example.splitwise.models.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    void deleteByGroupId(Long groupId);
}
