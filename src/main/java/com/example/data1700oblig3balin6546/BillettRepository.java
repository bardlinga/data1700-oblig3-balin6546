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

    public void oppdaterBillett(int billettId, Billett billett) {
        String sql = "update billett " +
                "set film = " + billett.getFilm() + " , " +
                "antall = " + billett.getAntall() + " , " +
                "fornavn = '" + billett.getFornavn() + "' , " +
                "etternavn = '" + billett.getEtternavn() + "' , " +
                "telefonnr = " + billett.getTelefonnr() + " , " +
                "epost = '" + billett.getEpost() + "' " +
                "where billettId = " + billettId;
        db.update(sql);
    }

    public List<Billett> hentAlleBilletter() {
        String sql = "select * from billett order by etternavn";
        List<Billett> alleBilletter = db.query(sql, new BeanPropertyRowMapper<>(Billett.class));
        return alleBilletter;
    }

    public Billett hentBillett(int billettId) {
        String sql = "select * from billett where billettId = " + billettId;
        Billett billett = db.queryForObject(sql, new BeanPropertyRowMapper<>(Billett.class));
        return billett;
    }

    public void slettAlleBilletter() {
        String sql = "delete from billett";
        db.update(sql);
    }

    public void slettBillett(int billettId) {
        String sql = "delete from billett where billettId = "+billettId;
        db.update(sql);
    }
}