package com.example.buddy_match.service.base;

import com.example.buddy_match.exception.BuddyUserNotFoundException;
import com.example.buddy_match.model.atest.BuddyUser;
import com.example.buddy_match.util.CustomPageImpl;
import com.example.buddy_match.util.ObjectAssigner;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This is a generated BaseService for demonstration purposes.
 */
@Service
public class BuddyUserBaseService {
    /** This is an example repository. */
    @Resource
    private BuddyUserRepository repository;

    public BuddyUserBaseService() {
    }

    public CustomPageImpl<BuddyUser> all(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<BuddyUser> buddyuserPage = repository.findAll(pageable).toList();
        return new CustomPageImpl<BuddyUser>(buddyuserPage, pageable, (long)(buddyuserPage.size()));
    }

    public BuddyUser create(@RequestBody BuddyUser newBuddyUser) {
        return repository.save(newBuddyUser);
    }

    public BuddyUser one(@PathVariable Long id) {
        BuddyUser buddyuser = repository.findById(id)
        .orElseThrow(() -> new BuddyUserNotFoundException(id));
        return buddyuser;
    }

    public BuddyUser replaceBuddyUser(@RequestBody BuddyUser newBuddyUser, @PathVariable Long id) {
        BuddyUser _updateModel = repository.findById(id)
        .map(_newBuddyUser -> {
            ObjectAssigner.assignNonNullValues(newBuddyUser, _newBuddyUser);
            return repository.save(_newBuddyUser);
        })
        .orElseThrow(() -> new BuddyUserNotFoundException(id));
        return _updateModel;
    }

    public Boolean deleteBuddyUser(@PathVariable Long id) {
        repository.deleteById(id);
        return true;
    }
}