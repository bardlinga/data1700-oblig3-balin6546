package com.example.data1700oblig3balin6546;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class BillettController {

    @Autowired
    BillettRepository rep;

    @PostMapping("/lagreBillett")
    public void lagreBillett(Billett innBillett) {
        rep.lagreBillett(innBillett);
    }

    @GetMapping("/hentAlleBilletter")
    public List<Billett> hentAlleBilletter() {
        return rep.hentAlleBilletter();
    }

    @PostMapping("/slettAlleBilletter")
    public void slettAlleBilletter() {
        rep.slettAlleBilletter();
    }

    @DeleteMapping("/slettBillett")
    public void slettBillett(int billettId) {
        rep.slettBillett(billettId);
    }
}
