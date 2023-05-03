package com.springboot.essentials.service;

import com.springboot.essentials.domain.Anime;
import com.springboot.essentials.exception.BadRequestException;
import com.springboot.essentials.mapper.AnimeMapper;
import com.springboot.essentials.repository.AnimeRepository;
import com.springboot.essentials.request.AnimePostRequestBody;
import com.springboot.essentials.request.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;
    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));

    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.deleteById(id);
    }


    public void update(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
       Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
       anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }


}
