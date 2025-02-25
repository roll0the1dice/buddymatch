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
import com.example.buddy_match.model.atest.Team;
import com.example.buddy_match.service.base.TeamBaseService;
import com.example.buddy_match.service.base.TeamRepository;
import com.example.buddy_match.util.CustomPageImpl;
import com.example.buddy_match.util.CustomSpecs;

import jakarta.annotation.Resource;

/**
 * This is a generated Service for demonstration purposes.
 */
@Service
public class TeamServiceImpl extends TeamBaseService implements TeamService {
    /** This is an example repository. */
    @Resource
    private TeamRepository repository;

    public TeamServiceImpl() {
        super();
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

        return new CustomPageImpl<Team>(pageTeamList, pageable, (long)pageTeamList.size());
    }
}