package com.anishare.userservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anime {

    private Long animeId;
    private String animeName;
    private String animeDesc;
    private int userRating;
    private int malRating;
    private PosterImage posterImage;
}
