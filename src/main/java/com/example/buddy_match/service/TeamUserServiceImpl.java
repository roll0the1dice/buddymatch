package com.example.buddy_match.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.buddy_match.exception.BusinessException;
import com.example.buddy_match.exception.ErrorCode;
import com.example.buddy_match.model.atest.BuddyUser;
import com.example.buddy_match.model.atest.Team;
import com.example.buddy_match.model.atest.TeamUser;
import com.example.buddy_match.service.base.TeamUserBaseService;
import com.example.buddy_match.service.base.TeamUserRepository;
import com.example.buddy_match.util.ApiResponse;
import com.example.buddy_match.util.CustomSpecs;
import com.example.buddy_match.util.ThrowUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This is a generated Service for demonstration purposes.
 */
@Service
public class TeamUserServiceImpl extends TeamUserBaseService implements TeamUserService {
    /** This is an example repository. */
    @Resource
    private TeamUserRepository repository;

    @Resource
    private TeamServiceImpl teamServiceImpl;

    public TeamUserServiceImpl() {
        super();
    }

    public TeamUser joinTeam(Long userId, Long teamId) {
        TeamUser teamUser = new TeamUser();
        teamUser.setUserId(userId);
        teamUser.setTeamId(teamId);

        return repository.save(teamUser);
    }

    public Long removeUserTeam(Long userId, Long teamId) {
        CustomSpecs<TeamUser> customSpecs = new CustomSpecs<>();
        if (userId < 0)
            customSpecs._equal("userId", userId);
        else
            customSpecs._equal("userId", userId)._equal("teamId", teamId);

        return repository.delete(customSpecs._generateSpecifications());
    }

    public List<TeamUser> getTeamUserList(Long teamId) {
        CustomSpecs<TeamUser> customSpecs = new CustomSpecs<>();
        customSpecs._equal("teamId", teamId);

        List<TeamUser> teamUserList = repository.findAll(customSpecs._generateSpecifications()).stream().toList();

        return teamUserList;
    }

    public TeamUser userJoinTeam(Long userId, Long teamId) {

        Team team = teamServiceImpl.one(teamId);

        if (team.getHasJoin() != 0) {
            throw new BusinessException("不等重复加入同一个队伍");
        }

        Long id = team.getId();
        team.setHasJoin((byte) 1);
        team.setHasJoinNum((byte) (team.getHasJoinNum() + 1));
        teamServiceImpl.replaceTeam(team, id);

        TeamUser teamUser = joinTeam(userId, id);

        ThrowUtils.throwIf(teamUser==null, ErrorCode.PARAMS_ERROR);

        return teamUser;
    }
}