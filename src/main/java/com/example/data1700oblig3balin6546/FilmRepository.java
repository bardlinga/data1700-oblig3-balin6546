package com.example.data1700oblig3balin6546;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmRepository {

    @Autowired
    private JdbcTemplate db;

    public List<Film> hentFilmListe() {
        String sql = "select * from film";
        List<Film> filmListe = db.query(sql, new BeanPropertyRowMapper<>(Film.class));
        return filmListe;
    }

    public String hentFilmTittel(int filmId){
        String sql = "select tittel from film where filmId = " + filmId;
        Film film = db.queryForObject(sql, new BeanPropertyRowMapper<>(Film.class));
        return film.getTittel();
    }
}
