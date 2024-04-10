package com.example.data1700oblig3balin6546;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Billett {
    private @Setter int billettId;
    private @Setter String film;
    private @Setter int antall;
    private @Setter String fornavn;
    private @Setter String etternavn;
    private @Setter int telefonnr;
    private @Setter String epost;
}