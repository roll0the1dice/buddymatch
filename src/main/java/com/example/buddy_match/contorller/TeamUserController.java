package com.example.buddy_match.contorller;


import com.example.buddy_match.exception.BusinessException;
import com.example.buddy_match.exception.ErrorCode;
import com.example.buddy_match.model.atest.BuddyUser;
import com.example.buddy_match.model.atest.Team;
import com.example.buddy_match.model.atest.TeamUser;
import com.example.buddy_match.service.BuddyUserServiceImpl;
import com.example.buddy_match.service.TeamServiceImpl;
import com.example.buddy_match.service.TeamUserServiceImpl;
import com.example.buddy_match.util.ApiResponse;
import com.example.buddy_match.util.CustomPageImpl;
import com.example.buddy_match.util.ThrowUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * This is a generated interface for demonstration purposes.
 */
@RestController
@RequestMapping("/teamuser")
public class TeamUserController {
    /** This is an example service. */
    @Resource
    private TeamUserServiceImpl service;

    @Resource
    private TeamServiceImpl teamServiceImpl;

    @Resource
    private BuddyUserServiceImpl buddyUserServiceImpl;

    public TeamUserController(TeamUserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<CustomPageImpl<TeamUser>>> all(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(service.all(page,size));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TeamUser>> create(@RequestBody TeamUser newTeamUser) {
        return ApiResponse.success(service.create(newTeamUser));
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<ApiResponse<TeamUser>> one(@PathVariable Long id) {
        return ApiResponse.success(service.one(id));
    }

    @PutMapping("/replaceTeamUser/{id}")
    public ResponseEntity<ApiResponse<TeamUser>> replaceTeamUser(@RequestBody TeamUser newTeamUser, @PathVariable Long id) {
        return ApiResponse.success(service.replaceTeamUser(newTeamUser,id));
    }

    @DeleteMapping("/deleteTeamUser/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteTeamUser(@PathVariable Long id) {
        return ApiResponse.success(service.deleteTeamUser(id));
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<ApiResponse<TeamUser>> userJoinTeam(@PathVariable Long id, HttpServletRequest request) {
        // TODO: process POST request
        BuddyUser buddyUser = buddyUserServiceImpl.getCurrent(request);
        if (buddyUser == null)
            throw new BusinessException("用户未登录");

        Team team = teamServiceImpl.one(id);

        if (team.getHasJoin() != 0)
            throw new BusinessException("不等重复加入同一个队伍");

        team.setHasJoin((byte) 1);
        team.setHasJoinNum((byte) (team.getHasJoinNum() + 1));
        teamServiceImpl.replaceTeam(team, id);

        return ApiResponse.success(service.joinTeam(buddyUser.getUserId(), id));
    }

    @PostMapping("/leave/{id}")
    public ResponseEntity<ApiResponse<String>> userLeaveTeam(@PathVariable Long id, HttpServletRequest request) {
        // TODO: process POST request
        BuddyUser buddyUser = buddyUserServiceImpl.getCurrent(request);
        if (buddyUser == null)
            throw new BusinessException("用户未登录");

        Team team = teamServiceImpl.one(id);

        if (team == null)
            throw new BusinessException("队伍不存在");

        if (team.getHasJoin() == 0 || team.getHasJoinNum() == 0)
            throw new BusinessException("未知错误");

        if ((team.getHasJoinNum() - 1 <= 1)) {
            team.setHasJoin((byte) 0);
            team.setHasJoinNum((byte) (1));
            long res = service.removeUserTeam(buddyUser.getUserId(), id);
            if (res == 0) {
                throw new BusinessException("无法退出未加入的队伍");
            }

            teamServiceImpl.replaceTeam(team, id);
            return ApiResponse.success("退出队伍成功");
        } else {
            team.setHasJoinNum((byte) (team.getHasJoinNum() - 1));
        }

        teamServiceImpl.replaceTeam(team, id);

        TeamUser  teamUser = service.joinTeam(buddyUser.getUserId(), id);

        ThrowUtils.throwIf(teamUser == null, ErrorCode.OPERATION_ERROR);

        return ApiResponse.success("退出队伍成功");
    }

    @DeleteMapping("/discard/{id}")
    public ResponseEntity<ApiResponse<Long>> userDiscardTeam(@PathVariable Long id, HttpServletRequest request) {
        // TODO: process POST request
        BuddyUser buddyUser = buddyUserServiceImpl.getCurrent(request);
        if (buddyUser == null)
            throw new BusinessException("用户未登录");

        Team team = teamServiceImpl.one(id);

        if (team == null) {
            throw new BusinessException("队伍不存在");
        }

        if (!team.getUserId().equals(buddyUser.getId())) {
            throw new BusinessException("你无法解散别人的队伍");
        }

        teamServiceImpl.deleteTeam(id);

        return ApiResponse.success(service.removeUserTeam(-1L, id));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<List<BuddyUser>>> findUserForTheTeam(@PathVariable Long id, HttpServletRequest request) {
        BuddyUser buddyUser = buddyUserServiceImpl.getCurrent(request);
        if (buddyUser == null)
            throw new BusinessException("用户未登录");

        List<TeamUser> teamUserList = service.getTeamUserList(id);
        List<BuddyUser> buddyUsers = new ArrayList<>();

        for (TeamUser teamUser : teamUserList) {
            BuddyUser user = buddyUserServiceImpl.getUserByUserId(teamUser.getUserId()).get();
            buddyUsers.add(user);   
        }

        return ApiResponse.success(buddyUsers);
    }
    

}