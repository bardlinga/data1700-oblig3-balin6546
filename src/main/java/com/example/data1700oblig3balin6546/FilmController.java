package com.example.data1700oblig3balin6546;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class FilmController {

    Film placeholder = new Film("","",0);
    Film lordOfTheShrimp = new Film("Lord of the Shrimp", "Fantasy", 2001);
    Film thereWillBeShrimp = new Film("There Will Be Shrimp", "Western",2007);
    Film shrimpAndPrejudice = new Film("Shrimp and Prejudice", "Drama", 2005);
    Film shrimpHardGetShrimped = new Film("Shrimp Hard - Get Shrimped", "Action", 1988);

    @GetMapping("/hentFilmListe")
    public List<Film> hentFilmListe() {
        List<Film> filmListe = new ArrayList<>();
        filmListe.add(placeholder);
        filmListe.add(lordOfTheShrimp);
        filmListe.add(thereWillBeShrimp);
        filmListe.add(shrimpAndPrejudice);
        filmListe.add(shrimpHardGetShrimped);
        return filmListe;
    }
}
