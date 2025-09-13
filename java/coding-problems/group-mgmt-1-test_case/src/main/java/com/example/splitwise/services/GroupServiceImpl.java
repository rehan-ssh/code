package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public GroupMember addMember(long groupId, long adminUserId, long memberToBeAddedUserId) throws InvalidGroupException, InvalidUserException, UnAuthorizedAccessException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Group not found"));

        GroupAdmin groupAdmin = groupAdminRepository.findByGroupIdAndAdminId(groupId, adminUserId).orElseThrow(() -> new UnAuthorizedAccessException("The given user is not an admin of the group"));
        User memberToBeAdded = userRepository.findById(memberToBeAddedUserId).orElseThrow(() -> new InvalidUserException("The given user does not exist"));
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByGroupIdAndUserId(groupId, memberToBeAddedUserId);
        if(groupMemberOptional.isPresent()){
            throw new InvalidUserException("The given user is already a member of the group");
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setUser(memberToBeAdded);
        groupMember.setAddedBy(groupAdmin.getAdmin());
        groupMember.setAddedAt(new Date());
        return groupMemberRepository.save(groupMember);

    }

    @Override
    public void removeMember(long groupId, long adminUserId, long memberToBeRemovedId) throws InvalidGroupException, UnAuthorizedAccessException, InvalidUserException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Group not found"));

        GroupAdmin groupAdmin = groupAdminRepository.findByGroupIdAndAdminId(groupId, adminUserId).orElseThrow(() -> new UnAuthorizedAccessException("The given user is not an admin of the group"));

        User memberToBeRemoved = userRepository.findById(memberToBeRemovedId).orElseThrow(() -> new InvalidUserException("The given user is not a valid user"));

        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByGroupIdAndUserId(groupId, memberToBeRemovedId);

        if(groupMemberOptional.isEmpty()){
            throw new InvalidUserException("The given user is not a member of the group");
        }

        groupMemberRepository.delete(groupMemberOptional.get());

    }

    @Override
    public List<User> fetchAllMembers(long groupId, long userId) throws InvalidGroupException, UnAuthorizedAccessException, InvalidUserException {

        groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Group not found"));

        userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("The user trying to access the group is not a valid user"));

        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroupIdAndAdminId(groupId, userId);
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);

        if(groupAdminOptional.isEmpty() && groupMemberOptional.isEmpty()){
            throw new UnAuthorizedAccessException("The given user is not a member of the group");
        }

        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(groupId);

        List<GroupAdmin> groupAdmins = groupAdminRepository.findAllByGroupId(groupId);

        Set<User> users = new HashSet<>();
        for(GroupMember groupMember : groupMembers){
            users.add(groupMember.getUser());
        }
        for(GroupAdmin groupAdmin1 : groupAdmins){
            users.add(groupAdmin1.getAdmin());
        }

        return users.stream().toList();

    }
}
