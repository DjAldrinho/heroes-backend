package com.example.heroesbackend.services;

import com.example.heroesbackend.entities.Hero;

import java.util.List;
import java.util.Optional;


public interface IHeroService {

    List<Hero> getHeroes();

    Optional<Hero> getHeroById(Long id);

    Optional<Hero> findHeroByName(String name);

    List<Hero> findHeroesByName(String name);

    Hero saveHero(Hero hero);

    void updateHero(Long id, Hero hero);

    void deleteHero(Long id);

    boolean existsHeroById(Long id);

    boolean existsHeroByName(String name);

}
