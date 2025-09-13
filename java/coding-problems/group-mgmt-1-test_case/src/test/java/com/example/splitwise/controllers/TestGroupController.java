package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestGroupController {


    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupAdminRepository groupAdminRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final GroupController groupController;

    @Autowired
    public TestGroupController(GroupRepository groupRepository, UserRepository userRepository, GroupAdminRepository groupAdminRepository, GroupMemberRepository groupMemberRepository, GroupController groupController) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupAdminRepository = groupAdminRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupController = groupController;
    }

    private List<User> admins;

    private List<User> members;

    private Group group;


    @BeforeEach
    public void insertDummy(){

        group = new Group();
        group.setName("Group 1");
        group.setDescription("Group 1 description");
        group = groupRepository.save(group);

        User admin1 = new User();
        admin1.setName("Admin 1");
        admin1.setPhoneNumber("1234567890");
        admin1 = userRepository.save(admin1);

        User admin2 = new User();
        admin2.setName("Admin 2");
        admin2.setPhoneNumber("1234567890");
        admin2 = userRepository.save(admin2);

        admins = List.of(admin1, admin2);

        User member1 = new User();
        member1.setName("Member 1");
        member1.setPhoneNumber("1234567890");
        member1 = userRepository.save(member1);

        User member2 = new User();
        member2.setName("Member 2");
        member2.setPhoneNumber("1234567890");
        member2 = userRepository.save(member2);

        User member3 = new User();
        member3.setName("Member 3");
        member3.setPhoneNumber("1234567890");
        member3 = userRepository.save(member3);

        members = List.of(member1, member2, member3);

        GroupAdmin groupAdmin1 = new GroupAdmin();
        groupAdmin1.setGroup(group);
        groupAdmin1.setAdmin(admin1);
        groupAdminRepository.save(groupAdmin1);

        GroupMember groupMember1 = new GroupMember();
        groupMember1.setGroup(group);
        groupMember1.setUser(member1);
        groupMember1.setAddedBy(admin1);
        groupMemberRepository.save(groupMember1);

    }

    @Test
    public void testAddMember_GroupNotFound(){
        AddMemberRequestDto requestDto =   new AddMemberRequestDto();
        requestDto.setGroupId(100);
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(members.get(0).getId());

        AddMemberResponseDto responseDto = groupController.addMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testAddMember_AdminNotFound(){
        AddMemberRequestDto requestDto =   new AddMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(100);
        requestDto.setMemberId(members.get(0).getId());

        AddMemberResponseDto responseDto = groupController.addMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testAddMember_MemberNotFound(){
        AddMemberRequestDto requestDto =   new AddMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(100);

        AddMemberResponseDto responseDto = groupController.addMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void testAddMember_MemberAlreadyPresent(){
        AddMemberRequestDto requestDto =   new AddMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(members.get(0).getId());

        AddMemberResponseDto responseDto = groupController.addMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void testAddMember_Success(){
        AddMemberRequestDto requestDto =   new AddMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(members.get(1).getId());

        AddMemberResponseDto responseDto = groupController.addMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus(), "Response status should be success");
        assertNotNull(responseDto.getGroupMember(), "Group member cannot be null");
        assertEquals(group.getId(), responseDto.getGroupMember().getGroup().getId(), "Group id should be same");
        assertEquals(members.get(1).getId(), responseDto.getGroupMember().getUser().getId(), "Member id should be same");
        assertEquals(admins.get(0).getId(), responseDto.getGroupMember().getAddedBy().getId(), "Added by id should be same");
    }

    @Test
    public void testRemoveMember_GroupNotFound(){
        RemoveMemberRequestDto requestDto =   new RemoveMemberRequestDto();
        requestDto.setGroupId(100);
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(members.get(0).getId());

        RemoveMemberResponseDto responseDto = groupController.removeMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testRemoveMember_AdminNotFound(){
        RemoveMemberRequestDto requestDto =   new RemoveMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(100);
        requestDto.setMemberId(members.get(0).getId());

        RemoveMemberResponseDto responseDto = groupController.removeMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testRemoveMember_MemberNotFound(){
        RemoveMemberRequestDto requestDto =   new RemoveMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(100);

        RemoveMemberResponseDto responseDto = groupController.removeMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void testRemoveMember_MemberNotPresentInGroup(){
        RemoveMemberRequestDto requestDto =   new RemoveMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(members.get(2).getId());

        RemoveMemberResponseDto responseDto = groupController.removeMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");
    }

    @Test
    public void testRemoveMember_Success(){
        RemoveMemberRequestDto requestDto =   new RemoveMemberRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setAdminId(admins.get(0).getId());
        requestDto.setMemberId(members.get(0).getId());

        RemoveMemberResponseDto responseDto = groupController.removeMember(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus(), "Response status should be success");
    }

    @Test
    public void testFetchMembers_GroupNotFound(){
        FetchMembersRequestDto requestDto =   new FetchMembersRequestDto();
        requestDto.setGroupId(100);
        requestDto.setMemberId(members.get(0).getId());

        FetchMembersResponseDto responseDto = groupController.fetchMembers(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testFetchMembers_MemberNotFound(){
        FetchMembersRequestDto requestDto =   new FetchMembersRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setMemberId(100);

        FetchMembersResponseDto responseDto = groupController.fetchMembers(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testFetchMembers_UserDoesntBelongToMemberOrAdmin(){
        FetchMembersRequestDto requestDto =   new FetchMembersRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setMemberId(admins.get(1).getId());

        FetchMembersResponseDto responseDto = groupController.fetchMembers(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus(), "Response status should be failure");

    }

    @Test
    public void testFetchMembers_Success(){
        FetchMembersRequestDto requestDto =   new FetchMembersRequestDto();
        requestDto.setGroupId(group.getId());
        requestDto.setMemberId(members.get(0).getId());

        FetchMembersResponseDto responseDto = groupController.fetchMembers(requestDto);
        assertNotNull(responseDto, "Response dto cannot be null");
        assertNotNull(responseDto.getResponseStatus(), "Response status cannot be null");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus(), "Response status should be success");
        assertNotNull(responseDto.getMembers(), "Members cannot be null");
        assertEquals(2, responseDto.getMembers().size(), "There should be 2 members");
    }


}
