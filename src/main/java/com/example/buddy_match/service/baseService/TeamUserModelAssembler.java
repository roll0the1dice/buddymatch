package com.example.buddy_match.service.baseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.buddy_match.contorller.TeamUserController;
import com.example.buddy_match.model.TeamUser;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@Component
public class TeamUserModelAssembler implements RepresentationModelAssembler<TeamUser, EntityModel<TeamUser>> {
    @Override
    public EntityModel<TeamUser> toModel(@NonNull TeamUser teamuser) {
        return EntityModel.of(teamuser,
        linkTo(methodOn(TeamUserController.class).one(teamuser.getId())).withSelfRel(),
        linkTo(methodOn(TeamUserController.class).all()).withRel("teamuser"));
    }
}