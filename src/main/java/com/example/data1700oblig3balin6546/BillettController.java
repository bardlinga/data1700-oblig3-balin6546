package com.example.data1700oblig3balin6546;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class BillettController {

    public final List<Billett> alleBilletter = new ArrayList<>();

    @PostMapping("/lagreBillett")
    public void lagreBillett(Billett innBillett) {
        alleBilletter.add(innBillett);
    }

    @GetMapping("/hentAlleBilletter")
    public List<Billett> hentAlle() {
        return alleBilletter;
    }

    @PostMapping("/slettAlleBilletter")
    public void slettAlleBilletter() {
        alleBilletter.clear();
    }
}
