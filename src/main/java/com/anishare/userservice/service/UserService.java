package com.anishare.userservice.service;

import com.anishare.userservice.DTO.AnimeListDTO;
import com.anishare.userservice.VO.Anime;
import com.anishare.userservice.entity.User;
import com.anishare.userservice.repository.UserRepo;
import com.anishare.userservice.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    UserValidation userValidation;

    public UserService() {
    }

    public User findUserByUsername(String userName) {

        User user = userRepo.findByUserName(userName);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }

    public User findUserByUserId(Long userId) {
        User user = userRepo.findByUserId(userId);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public User saveUser(User user) {

        if (!userValidation.checkForDuplicateUserName(user.getUsername()))
            return userRepo.save(user);
        else
            return null;

    }

    public List<Anime> getUserAnimeList(Long userId) {

        User user = findUserByUserId(userId);

        if (user == null) return null;

        String[] userAnimeList = user.getAnimeListID().split(",");

        log.info(String.valueOf(userAnimeList.length));

        if (userAnimeList.length == 0) return null;

        List<Anime> userAList = new ArrayList<>();

        for (String animeId : userAnimeList) {
            Anime anime = restTemplate.getForObject("http://ANIME-SERVICE/anime/" + animeId, Anime.class);
            userAList.add(anime);
        }

        return userAList;

    }

    public void saveUserList(AnimeListDTO animeList, Long userId) {

        log.info("Received AnimeList + " + animeList.toString());
        User user = findUserByUserId(userId);
        user.setAnimeListID(animeList.getAnimeList());
        log.info("User Object after update" + user);
        saveUser(user);

    }
}
