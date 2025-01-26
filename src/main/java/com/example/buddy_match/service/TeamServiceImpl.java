package com.example.buddy_match.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.buddy_match.constant.UserConstant;
import com.example.buddy_match.dto.CustomPageImpl;
import com.example.buddy_match.model.BuddyUser;
import com.example.buddy_match.model.Team;
import com.example.buddy_match.service.baseService.TeamBaseService;
import com.example.buddy_match.service.baseService.TeamModelAssembler;
import com.example.buddy_match.service.baseService.TeamRepository;
import com.example.buddy_match.util.CustomSpecs;

/**
 * This is a generated Service for demonstration purposes.
 */
@Service
public class TeamServiceImpl extends TeamBaseService implements TeamService {
    /** This is an example repository. */
    private TeamRepository repository;

    /** This is an example modelAssembler. */
    private TeamModelAssembler assembler;

    public TeamServiceImpl() {
        super();
    }

    @Autowired
    public TeamServiceImpl(TeamRepository repository, TeamModelAssembler assembler) {
        super(repository,assembler);
        this.repository = repository;
        this.assembler = assembler;
    }

    public CustomPageImpl<Team> getTeamByPage(int page, int size, int teamStatus) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        CustomSpecs<Team> customSpecs = new CustomSpecs<Team>();
        if (teamStatus == 0) {
            customSpecs._equal("teamStatus", teamStatus);
        } else {
            customSpecs._equal("teamStatus", 1)._or()._equal("teamStatus", 2);
        }

        List<Team> pageTeamList = repository.findAll(customSpecs._generateSpecifications(), pageable).toList();

        return new CustomPageImpl<Team>(pageTeamList, pageable, pageTeamList.size());
    }
}