package com.octa.developer.jugtours;

import com.octa.developer.jugtours.model.Event;
import com.octa.developer.jugtours.model.Group;
import com.octa.developer.jugtours.repo.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final GroupRepository groupRepo;

    public Initializer(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(name -> groupRepo.save(new Group(name)));

        Group djug = groupRepo.findByName("Denver JUG").orElse(null);

        Event e = Event.builder().title("Full Stack Reactive")
                .description("Reactive with Spring Boot + React")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();

        djug.setEvents(Collections.singleton(e));
        groupRepo.save(djug);

        groupRepo.findAll().forEach(System.out::println);
    }
}
