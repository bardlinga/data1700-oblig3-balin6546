package com.example.data1700oblig3balin6546;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController

public class BillettController {

    @Autowired
    BillettRepository rep;

    @Autowired
    BrukerController bruker;

    @PostMapping("/lagreBillett")
    public void lagreBillett(Billett innBillett) {
        rep.lagreBillett(innBillett);
    }

    @PostMapping("/oppdaterBillett")
    public void oppdaterBillett(@RequestParam("billettId") int billettId, Billett innBillett) {
        rep.oppdaterBillett(billettId, innBillett);
    }

    @GetMapping("/hentAlleBilletter")
    public List<Billett> hentAlleBilletter(HttpServletResponse response) throws IOException {
        if(bruker.checkIfLoggedIn()) {
            return rep.hentAlleBilletter();
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }
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
