package com.octa.developer.jugtours.web;


import com.octa.developer.jugtours.model.Group;
import com.octa.developer.jugtours.repo.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private final GroupRepository repo;

    public GroupController(GroupRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/groups")
    public Collection<Group> groups() {
        return repo.findAll();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<Group> groupOpt = repo.findById(id);

        return groupOpt.map(g -> ResponseEntity.ok().body(g))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
        log.info("Request to create a Group: {}", group);
        Group savedGroup = repo.save(group);

        return ResponseEntity
                .created(new URI("/api/group/" + savedGroup.getId()))
                .body(savedGroup);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> update(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = repo.save(group);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        repo.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
