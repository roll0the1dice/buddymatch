package com.example.buddy_match.service.baseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.buddy_match.contorller.BuddyUserController;
import com.example.buddy_match.model.BuddyUser;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@Component
public class BuddyUserModelAssembler implements RepresentationModelAssembler<BuddyUser, EntityModel<BuddyUser>> {
    @Override
    public EntityModel<BuddyUser> toModel(@NonNull BuddyUser buddyuser) {
        return EntityModel.of(buddyuser,
        linkTo(methodOn(BuddyUserController.class).one(buddyuser.getId())).withSelfRel(),
        linkTo(methodOn(BuddyUserController.class).all()).withRel("buddyuser"));
    }
}