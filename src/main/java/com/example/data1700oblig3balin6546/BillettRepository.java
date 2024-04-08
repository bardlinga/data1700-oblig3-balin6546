package com.example.data1700oblig3balin6546;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillettRepository {
    @Autowired
    private JdbcTemplate db;
}