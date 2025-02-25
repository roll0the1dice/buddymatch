package com.example.buddy_match.service.base;

import com.example.buddy_match.exception.TeamUserNotFoundException;
import com.example.buddy_match.model.atest.TeamUser;
import com.example.buddy_match.util.CustomPageImpl;
import com.example.buddy_match.util.ObjectAssigner;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This is a generated BaseService for demonstration purposes.
 */
@Service
public class TeamUserBaseService {
    /** This is an example repository. */
    @Resource
    private TeamUserRepository repository;

    public TeamUserBaseService() {
    }

    public CustomPageImpl<TeamUser> all(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TeamUser> teamuserPage = repository.findAll(pageable).toList();
        return new CustomPageImpl<TeamUser>(teamuserPage, pageable, (long)(teamuserPage.size()));
    }

    public TeamUser create(@RequestBody TeamUser newTeamUser) {
        return repository.save(newTeamUser);
    }

    public TeamUser one(@PathVariable Long id) {
        TeamUser teamuser = repository.findById(id)
        .orElseThrow(() -> new TeamUserNotFoundException(id));
        return teamuser;
    }

    public TeamUser replaceTeamUser(@RequestBody TeamUser newTeamUser, @PathVariable Long id) {
        TeamUser _updateModel = repository.findById(id)
        .map(_newTeamUser -> {
            ObjectAssigner.assignNonNullValues(newTeamUser, _newTeamUser);
            return repository.save(_newTeamUser);
        })
        .orElseThrow(() -> new TeamUserNotFoundException(id));
        return _updateModel;
    }

    public Boolean deleteTeamUser(@PathVariable Long id) {
        repository.deleteById(id);
        return true;
    }
}