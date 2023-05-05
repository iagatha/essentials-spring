package com.springboot.essentials.client;

import com.springboot.essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/2", Anime.class);
        log.info(object);

        Anime[] animes= new RestTemplate().getForObject("http://localhost:8080/animes/2", Anime[].class);
        log.info(Arrays.toString(animes));



        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/2", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());



//        Anime kingdone = Anime.builder().name("kingdone").build();
//        Anime kingdoneSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdone, Anime.class);
//        log.info("saved anime {}", kingdoneSaved);



        Anime samurai = Anime.builder().name("samurai").build();
       ResponseEntity<Anime> samuraiSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
               HttpMethod.POST,
               new HttpEntity<>(samurai, createJsonHeader())
            , Anime.class);
       log.info("saved anime {}", samuraiSaved);



        Anime animeToBeUpdated = samuraiSaved.getBody();
        animeToBeUpdated.setName("samurai 2");
        ResponseEntity<Void> samuraiSavedUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader())
                ,Void.class);
        log.info(samuraiSavedUpdated);



        ResponseEntity<Void> samuraiSavedDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
               null
                ,Void.class,
                animeToBeUpdated.getId());
        log.info(samuraiSavedDelete);


    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
