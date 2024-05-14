package com.example.data1700oblig3balin6546;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BrukerRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(BrukerRepository.class);

    public boolean registerBruker(Bruker bruker){
        String sql = "insert into bruker (brukernavn, passord) values (?, ?)";
        try {
            db.update(sql, bruker.getBrukernavn(), bruker.getPassord());
            return true;
        } catch (Exception e) {
            logger.error("Error: "+e.getCause());
            return false;
        }
    }

    public boolean sjekkBrukernavnOgPassord(Bruker innBruker){
        String sql = "select * from bruker where brukernavn = ?";
        Bruker registrertBruker = db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Bruker.class),
                innBruker.getBrukernavn());
        if (BCrypt.checkpw(innBruker.getPassord(), registrertBruker.getPassord())){
            logger.info("Password is correct");
            return true;
        }
        logger.info("Password is not correct");
        return false;
    }
}
