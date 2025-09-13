package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupAdminRepository groupAdminRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, GroupAdminRepository groupAdminRepository, GroupMemberRepository groupMemberRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupAdminRepository = groupAdminRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    @Override
    public Group createGroup(String groupName, String description, long userId) throws InvalidUserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("The user trying to access the group is not a valid user"));

        Group group = new Group();
        group.setName(groupName);
        group.setCreatedAt(new Date());
        group.setDescription(description);
        group = groupRepository.save(group);

        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setGroup(group);
        groupAdmin.setAdmin(user);
        groupAdmin.setAddedBy(user);
        groupAdminRepository.save(groupAdmin);
        return group;
    }

    @Override
    @Transactional
    public void deleteGroup(long groupId, long userId) throws InvalidGroupException, UnAuthorizedAccessException, InvalidUserException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Group not found"));

        userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("The user trying to access the group is not a valid user"));

        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroupIdAndAdminId(groupId, userId);

        if(groupAdminOptional.isEmpty()){
            throw new UnAuthorizedAccessException("The given user is not an admin of the group");
        }

        groupAdminRepository.deleteByGroupId(group.getId());
        groupMemberRepository.deleteByGroupId(group.getId());
        groupRepository.delete(group);
    }
}
