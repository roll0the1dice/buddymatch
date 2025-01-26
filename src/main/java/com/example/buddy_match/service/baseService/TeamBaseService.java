package com.example.buddy_match.service.baseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.buddy_match.contorller.TeamController;
import com.example.buddy_match.model.Team;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TeamRepository repository;

    /** This is an example modelAssembler. */
    private TeamModelAssembler assembler;

    public TeamBaseService() {
    }

    @Autowired
    public TeamBaseService(TeamRepository repository, TeamModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Team>> all() {
        List<EntityModel<Team>> team = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        return CollectionModel.of(team, linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    public Team create(@RequestBody Team newTeam) {
        return repository.save(newTeam);
    }

    public EntityModel<Team> one(@PathVariable Long id) {
        Team team = repository.findById(id)
        .orElseThrow(() -> new TeamNotFoundException(id));
        return assembler.toModel(team);
    }

    public ResponseEntity<?> replaceTeam(@RequestBody Team newTeam, @PathVariable Long id) {
        Team _updateModel = repository.findById(id)
        .map(_newTeam -> {
            newTeam.setId(_newTeam.getId());
            return repository.save(newTeam);
        })
        .orElseGet(() -> {
            return repository.save(newTeam);
        });
        EntityModel<Team> entityModel = assembler.toModel(_updateModel);
        return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
    }

    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}