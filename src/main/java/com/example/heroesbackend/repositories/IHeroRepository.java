package com.example.heroesbackend.repositories;

import com.example.heroesbackend.entities.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHeroRepository extends CrudRepository<Hero, Long> {
    List<Hero> getHeroesByNameContainsIgnoreCase(String name);

    Optional<Hero> findHeroByName(String name);

    boolean existsByName(String name);
}
