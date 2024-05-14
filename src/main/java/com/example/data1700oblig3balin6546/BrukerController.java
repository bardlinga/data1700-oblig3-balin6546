package com.example.data1700oblig3balin6546;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController

public class BrukerController {

    @Autowired
    BrukerRepository rep;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public boolean login(Bruker bruker){
        if(rep.sjekkBrukernavnOgPassord(bruker)){
            session.setAttribute("Innlogget", bruker);
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    public boolean logout(){
        try{
            session.removeAttribute("Innlogget");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfLoggedIn(){
        return session.getAttribute("Innlogget") != null;
    }

    @PostMapping("/registerTestUser")
    public void registerTestUser(Bruker testUser, HttpServletResponse response) throws IOException {
        String kryptertPassord = BCrypt.hashpw(testUser.getPassord(),BCrypt.gensalt(10));
        testUser.setPassord(kryptertPassord);
        if (!rep.registerBruker(testUser)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "testUser already exists");
        }
    }


}
