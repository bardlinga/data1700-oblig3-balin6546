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

    @PostMapping("/oppdaterBillett")
    public void oppdaterBillett(@RequestParam("billettId") int billettId, Billett innBillett) {
        rep.oppdaterBillett(billettId, innBillett);
    }

    @GetMapping("/hentAlleBilletter")
    public List<Billett> hentAlleBilletter() {
        return rep.hentAlleBilletter();
    }

    @GetMapping("/hentBillett")
    public Billett hentBillett(int billettId) {
        return rep.hentBillett(billettId);
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
