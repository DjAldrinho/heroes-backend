package com.example.heroesbackend.controllers;

import com.example.heroesbackend.dto.inputs.HeroDto;
import com.example.heroesbackend.dto.outputs.ResponseDto;
import com.example.heroesbackend.entities.Hero;
import com.example.heroesbackend.exceptions.ResourceExistsException;
import com.example.heroesbackend.exceptions.ResourceNotFoundException;
import com.example.heroesbackend.services.IHeroService;
import com.example.heroesbackend.utils.ValidationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final IHeroService service;
    private final ModelMapper modelMapper;


    public HeroController(IHeroService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> findAllHeroes() {

        List<HeroDto> heroes = this.service.getHeroes()
                .stream().map((hero) -> modelMapper.map(hero, HeroDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseDto(heroes, "Heroes loaded"));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchHeroes(@RequestParam("query") String query) {

        List<HeroDto> heroes = this.service.findHeroesByName(query).stream()
                .map((hero -> modelMapper.map(hero, HeroDto.class))).collect(Collectors.toList());


        if (heroes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(null, "Heroes not found"));
        }

        return ResponseEntity.ok(new ResponseDto(heroes, "Heroes founded"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getHero(@PathVariable("id") Long id) {

        Hero hero = this.service.getHeroById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), "id", "Hero"));

        return ResponseEntity.ok(new ResponseDto(hero, "Hero loaded"));
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createHero(@Valid @RequestBody HeroDto heroDto) {

        boolean existsHero = this.service.existsHeroByName(heroDto.getName());

        if (existsHero) {
            throw new ResourceExistsException(heroDto.getName(), "Hero");
        }

        Hero newHero = this.service.saveHero(modelMapper.map(heroDto, Hero.class));

        ValidationResponse response = new ValidationResponse("Hero created", HttpStatus.CREATED);

        response.addData("hero", newHero);

        return response.getResponse();

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateHero(@PathVariable("id") Long id,
                                                  @Valid @RequestBody HeroDto heroDto) {

        boolean existsHero = this.service.existsHeroByName(heroDto.getName());

        if (existsHero) {
            Hero hero = this.service.findHeroByName(heroDto.getName()).get();

            if (!hero.getId().equals(id)) {
                throw new ResourceExistsException(heroDto.getName(), "Hero");
            }
        }

        Hero heroClass = modelMapper.map(heroDto, Hero.class);

        this.service.updateHero(id, heroClass);

        return ResponseEntity.ok(new ResponseDto(heroDto, "Hero updated"));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteHero(@PathVariable("id") Long id) {
        this.service.deleteHero(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDto(null, "Hero deleted"));

    }
}
