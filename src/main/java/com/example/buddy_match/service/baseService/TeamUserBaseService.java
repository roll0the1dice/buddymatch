package com.example.buddy_match.service.baseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.buddy_match.contorller.TeamUserController;
import com.example.buddy_match.model.TeamUser;
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
public class TeamUserBaseService {
    /** This is an example repository. */
    private TeamUserRepository repository;

    /** This is an example modelAssembler. */
    private TeamUserModelAssembler assembler;

    public TeamUserBaseService() {
    }

    @Autowired
    public TeamUserBaseService(TeamUserRepository repository, TeamUserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<TeamUser>> all() {
        List<EntityModel<TeamUser>> teamuser = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        return CollectionModel.of(teamuser, linkTo(methodOn(TeamUserController.class).all()).withSelfRel());
    }

    public TeamUser create(@RequestBody TeamUser newTeamUser) {
        return repository.save(newTeamUser);
    }

    public EntityModel<TeamUser> one(@PathVariable Long id) {
        TeamUser teamuser = repository.findById(id)
        .orElseThrow(() -> new TeamUserNotFoundException(id));
        return assembler.toModel(teamuser);
    }

    public ResponseEntity<?> replaceTeamUser(@RequestBody TeamUser newTeamUser, @PathVariable Long id) {
        TeamUser _updateModel = repository.findById(id)
        .map(_newTeamUser -> {
            return repository.save(_newTeamUser);
        })
        .orElseGet(() -> {
            return repository.save(newTeamUser);
        });
        EntityModel<TeamUser> entityModel = assembler.toModel(_updateModel);
        return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
    }

    public ResponseEntity<?> deleteTeamUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}