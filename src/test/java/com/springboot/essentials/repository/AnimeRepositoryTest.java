package com.springboot.essentials.repository;

import com.springboot.essentials.domain.Anime;
import com.springboot.essentials.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

import static com.springboot.essentials.util.AnimeCreator.createAnimeToBeSaved;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    

    @Test
    @DisplayName("Save persists anime when sucessful")
    void save_PersistAnime_WhenSucessful(){

        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save update anime when sucessful")
    void save_UpdateAnime_WhenSucessful(){

        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        animeSaved.setName("Overlod");
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete anime when sucessful")
    void delete_RemoveAnime_WhenSucessful(){

        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of anime when sucessful")
    void findByName_ReturnsListOfAnime_WhenSucessful(){

        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);


        String name = animeSaved.getName();
        List<Anime> animes = this.animeRepository.findByName(name);


        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);

    }

    @Test
    @DisplayName("Find by name returns list of anime when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeisNotFound(){


        List<Anime> animes = this.animeRepository.findByName("xaxa");


        Assertions.assertThat(animes).isEmpty();

    }

    @Test
    @DisplayName("Save throw  ConstrainViolationException when name is empty")
    void save_ThrowConstrainViolationException_WhenNameIsEmpty(){

        Anime anime = new Anime();
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);


    }



}