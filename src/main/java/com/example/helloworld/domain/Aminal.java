package com.example.helloworld.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aminal {
    private String name;
    private int averageLifeSpan;
    private int numberLegs;
    private Habitat habitat;

}
