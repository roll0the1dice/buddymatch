package com.example.buddy_match.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.buddy_match.model.BuddyUser;
import com.example.buddy_match.model.TeamUser;
import com.example.buddy_match.service.baseService.TeamUserBaseService;
import com.example.buddy_match.service.baseService.TeamUserModelAssembler;
import com.example.buddy_match.service.baseService.TeamUserRepository;
import com.example.buddy_match.util.CustomSpecs;

/**
 * This is a generated Service for demonstration purposes.
 */
@Service
public class TeamUserServiceImpl extends TeamUserBaseService implements TeamUserService {
    /** This is an example repository. */
    private TeamUserRepository repository;

    /** This is an example modelAssembler. */
    private TeamUserModelAssembler assembler;

    public TeamUserServiceImpl() {
        super();
    }

    @Autowired
    public TeamUserServiceImpl(TeamUserRepository repository, TeamUserModelAssembler assembler) {
        super(repository,assembler);
        this.repository = repository;
        this.assembler = assembler;
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

        List<TeamUser>  teamUserList = repository.findAll(customSpecs._generateSpecifications()).stream().toList();

        return teamUserList;
    }
}