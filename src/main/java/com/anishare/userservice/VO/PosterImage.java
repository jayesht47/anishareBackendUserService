package com.anishare.userservice.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PosterImage {

    private String tiny;
    private String small;
    private String medium;
    private String large;
    private String orignal;

}
