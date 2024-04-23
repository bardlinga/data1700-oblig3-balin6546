package com.example.data1700oblig3balin6546;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class FilmController {

    @Autowired
    FilmRepository rep;

    @GetMapping("/hentFilmListe")
    public List<Film> hentFilmListe() {
        return rep.hentFilmListe();
    }

    @GetMapping("/hentFilmTittelById")
    public String hentFilmTittel(@RequestParam("filmId") int filmId) {
        return rep.hentFilmTittel(filmId);
    }
}
