package com.example.buddy_match.service.baseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.buddy_match.contorller.TeamController;
import com.example.buddy_match.model.Team;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@Component
public class TeamModelAssembler implements RepresentationModelAssembler<Team, EntityModel<Team>> {
    @Override
    public EntityModel<Team> toModel(@NonNull Team team) {
        return EntityModel.of(team,
        linkTo(methodOn(TeamController.class).one(team.getId())).withSelfRel(),
        linkTo(methodOn(TeamController.class).all()).withRel("team"));
    }
}