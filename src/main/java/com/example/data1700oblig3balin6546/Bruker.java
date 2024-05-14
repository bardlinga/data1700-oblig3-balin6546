package com.example.data1700oblig3balin6546;

public class Bruker {
    private String brukernavn;
    private String passord;

    public Bruker(int brukerId, String brukernavn, String passord) {
        this.brukernavn = brukernavn;
        this.passord = passord;
    }

    public Bruker(){}


    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public String getPassord() {
        return passord;
    }
}
