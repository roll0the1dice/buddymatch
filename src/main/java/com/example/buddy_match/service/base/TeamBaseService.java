package com.example.buddy_match.service.base;

import com.example.buddy_match.exception.TeamNotFoundException;
import com.example.buddy_match.model.atest.Team;
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
public class TeamBaseService {
    /** This is an example repository. */
    @Resource
    private TeamRepository repository;

    public TeamBaseService() {
    }

    public CustomPageImpl<Team> all(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Team> teamPage = repository.findAll(pageable).toList();
        return new CustomPageImpl<Team>(teamPage, pageable, (long)(teamPage.size()));
    }

    public Team create(@RequestBody Team newTeam) {
        return repository.save(newTeam);
    }

    public Team one(@PathVariable Long id) {
        Team team = repository.findById(id)
        .orElseThrow(() -> new TeamNotFoundException(id));
        return team;
    }

    public Team replaceTeam(@RequestBody Team newTeam, @PathVariable Long id) {
        Team _updateModel = repository.findById(id)
        .map(_newTeam -> {
            ObjectAssigner.assignNonNullValues(newTeam, _newTeam);
            return repository.save(_newTeam);
        })
        .orElseThrow(() -> new TeamNotFoundException(id));
        return _updateModel;
    }

    public Boolean deleteTeam(@PathVariable Long id) {
        repository.deleteById(id);
        return true;
    }

    public void flush() {
        repository.flush();  // 强制将数据写入数据库
    }
}