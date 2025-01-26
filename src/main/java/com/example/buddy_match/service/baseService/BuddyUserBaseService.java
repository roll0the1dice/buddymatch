package com.example.buddy_match.service.baseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.buddy_match.contorller.BuddyUserController;
import com.example.buddy_match.model.BuddyUser;
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
public class BuddyUserBaseService {
    /** This is an example repository. */
    private BuddyUserRepository repository;

    /** This is an example modelAssembler. */
    private BuddyUserModelAssembler assembler;

    public BuddyUserBaseService() {
    }

    @Autowired
    public BuddyUserBaseService(BuddyUserRepository repository, BuddyUserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<BuddyUser>> all() {
        List<EntityModel<BuddyUser>> buddyuser = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        return CollectionModel.of(buddyuser, linkTo(methodOn(BuddyUserController.class).all()).withSelfRel());
    }

    public BuddyUser create(@RequestBody BuddyUser newBuddyUser) {
        return repository.save(newBuddyUser);
    }

    public EntityModel<BuddyUser> one(@PathVariable Long id) {
        BuddyUser buddyuser = repository.findById(id)
        .orElseThrow(() -> new BuddyUserNotFoundException(id));
        return assembler.toModel(buddyuser);
    }

    public ResponseEntity<?> replaceBuddyUser(@RequestBody BuddyUser newBuddyUser, @PathVariable Long id) {
        BuddyUser _updateModel = repository.findById(id)
        .map(_newBuddyUser -> {
            return repository.save(_newBuddyUser);
        })
        .orElseGet(() -> {
            return repository.save(newBuddyUser);
        });
        EntityModel<BuddyUser> entityModel = assembler.toModel(_updateModel);
        return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
    }

    public ResponseEntity<?> deleteBuddyUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}