package com.springboot.essentials.util;

import com.springboot.essentials.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Majine no Ippo")
                .build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Majine no Ippo")
                .Id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime(){
        return Anime.builder()
                .name("Majine no Ippo")
                .Id(1L)
                .build();
    }
}
