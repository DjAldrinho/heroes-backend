package com.example.heroesbackend.services;

import com.example.heroesbackend.entities.Hero;
import com.example.heroesbackend.exceptions.ResourceNotFoundException;
import com.example.heroesbackend.repositories.IHeroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HeroServiceImpl implements IHeroService {


    private final IHeroRepository repository;


    public HeroServiceImpl(IHeroRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Hero> getHeroes() {
        return (List<Hero>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Hero> getHeroById(Long id) {
        return this.repository.findById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Hero> findHeroByName(String name) {
        return this.repository.findHeroByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hero> findHeroesByName(String name) {
        return this.repository.getHeroesByNameContainsIgnoreCase(name);
    }

    @Override
    @Transactional()
    public Hero saveHero(Hero hero) {
        return this.repository.save(hero);
    }

    @Override
    @Transactional()
    public void updateHero(Long id, Hero updateHero) {

        Hero hero = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), "id", "Hero"));
        hero.setAlterEgo(updateHero.getAlterEgo());
        hero.setName(updateHero.getName());
        hero.setPublisher(updateHero.getPublisher());
        hero.setUrlAvatar(updateHero.getUrlAvatar());
        this.repository.save(hero);
    }

    @Override
    @Transactional()
    public void deleteHero(Long id) {

        this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), "id", "Hero"));

        this.repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsHeroById(Long id) {
        return this.repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsHeroByName(String name) {
        return this.repository.existsByName(name);
    }
}
