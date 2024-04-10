package com.example.data1700oblig3balin6546;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillettRepository {

    @Autowired
    private JdbcTemplate db;

    public void lagreBillett(Billett billett) {
        String sql = "insert into billett (film, antall, fornavn, etternavn, telefonnr, epost) values(?,?,?,?,?,?)";
        db.update(sql,
                billett.getFilm(), billett.getAntall(),
                billett.getFornavn(), billett.getEtternavn(),
                billett.getTelefonnr(), billett.getEpost()
        );
    }

    public List<Billett> hentAlleBilletter() {
        String sql = "select * from billett";
        List<Billett> alleBilletter = db.query(sql, new BeanPropertyRowMapper<>(Billett.class));
        return alleBilletter;
    }

    public void slettAlleBilletter() {
        String sql = "delete from billett";
        db.update(sql);
    }
}