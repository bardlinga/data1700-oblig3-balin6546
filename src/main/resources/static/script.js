// movie list and info functions --------------------------------------------------------------------------------------

$(document).ready(function () {
    hentAlleFilmer();
    printBillettArray();
    registerTestUser();
});

function login(){
    const credentials = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    }
    $.get("/login", credentials, function(loggetInn){
        if(loggetInn){
            printBillettArray();
            $("#brukernavn").val("");
            $("#passord").val("");
            alert("login successful");
        } else {
            alert("login failed");
        }
    });
}

function logout(){
    $.get("/logout", function(loggetUt){
        if(loggetUt){
            printBillettArray();
            alert("logout successful");
        } else {
            alert("logout failed");
        }
    })
}

function registerTestUser() {
    const testUser = {
        brukernavn : "test",
        passord : "test123"
    }
    $.post("/registerTestUser", testUser, function () {
        console.log("testUser created");
    })
        .fail(function(jqXHR){
            const json = $.parseJSON(jqXHR.responseText);
            console.log(json.message);
        });
}

function hentAlleFilmer() {
    $.get("/hentFilmListe", function (filmer) {
        opprettFilmDropdown(filmer);
    });
}

function opprettFilmDropdown(filmer) {
    let ut = "<select id=\"filmSelect\" name=\"film\">";
    for (const film of filmer) {
        ut += "<option value='" + film.filmId + "'>" + film.tittel + "</option>";
    }
    ut += "</select>"
    $("#film").html(ut);
}

function hentFilmTittel(filmId) {
    return $.get("/hentFilmTittelById?filmId=" + filmId, function (filmTittel) {
        return filmTittel;
    })
}

// error message functions --------------------------------------------------------------------------------------------

const feilmelding = {
    film: "Du må velge en film",
    antall: "Du må velge et antall",
    navn: "Du må skrive inn et navn, kan kun inneholde bokstaver",
    telefonnr: "Du må skrive inn et telefonnummer, kan kun inneholde tall",
    epost: "Du må skrive inn en gyldig epost-adresse"
}

function toggleFeilmelding(id, inputGyldig, feilmelding) {
    if (!inputGyldig) {
        $('#' + id + 'UgyldigMelding').html(feilmelding);
    } else {
        $('#' + id + 'UgyldigMelding').html("");
    }
}

// input validation functions ---------------------------------------------------------------------

const regExp = {
    film: /[^ ]/,
    antall: /^[1-9][0-9]?$/,
    navn: /^[^0-9]+$/,
    telefonnr: /^[0-9]+$/,
    epost: /^([a-å]?[0-9]?)+@([a-å]?[0-9]?)+.[a-å]+/ //probably horrible regex
}

function validerInput(id, regExp, feilmelding) {
    let input;
    if(id === "film"){
        input = $('#filmSelect').find("option:selected").text();
    } else {
        input = document.getElementById(id).value;
    }
    let inputGyldig = regExp.test(input);
    toggleFeilmelding(id, inputGyldig, feilmelding);
    return inputGyldig;
}

$(document).ready(function () {
    $('#film').change(function () {
        validerInput("film", regExp.film, feilmelding.film)
    });
    $('#antall').change(function () {
        validerInput("antall", regExp.antall, feilmelding.antall)
    });
    $('#fornavn').change(function () {
        validerInput("fornavn", regExp.navn, feilmelding.navn)
    });
    $('#etternavn').change(function () {
        validerInput("etternavn", regExp.navn, feilmelding.navn)
    });
    $('#telefonnr').change(function () {
        validerInput("telefonnr", regExp.telefonnr, feilmelding.telefonnr)
    });
    $('#epost').change(function () {
        validerInput("epost", regExp.epost, feilmelding.epost)
    });

    $('#fyllSkjemaKnapp').click(function () {
        fyllSkjema()
    });
});

function validerSkjema() {
    let inputSjekkArray = [
        validerInput("film", regExp.film, feilmelding.film),
        validerInput("antall", regExp.antall, feilmelding.antall),
        validerInput("fornavn", regExp.navn, feilmelding.navn),
        validerInput("etternavn", regExp.navn, feilmelding.navn),
        validerInput("telefonnr", regExp.telefonnr, feilmelding.telefonnr),
        validerInput("epost", regExp.epost, feilmelding.epost)
    ]
    return !inputSjekkArray.includes(false);
}

// ticket creation functions -------------------------------------------------------------------------------------------

function lagNyBillett() {
    return {
        film: $('#filmSelect').val(),
        antall: $('#antall').val(),
        fornavn: $('#fornavn').val(),
        etternavn: $('#etternavn').val(),
        telefonnr: $('#telefonnr').val(),
        epost: $('#epost').val()
    };
}

function kjopBillett() {
    if (validerSkjema()) {
        let billett = lagNyBillett();
        $.post("/lagreBillett", billett, function () {
            printBillettArray();
        });
        document.getElementById('bestillingsskjema').reset();
    }
}

// ticket edit functions -----------------------------------------------------------------------------------------------

function endreBillett(billettId) {
    $.ajax({
        url : "/hentBillett",
        type : "GET",
        data: { billettId: billettId },
        success : function (data) {
            $('#filmSelect').val(data.film);
            $('#antall').val(data.antall);
            $('#fornavn').val(data.fornavn);
            $('#etternavn').val(data.etternavn);
            $('#telefonnr').val(data.telefonnr);
            $('#epost').val(data.epost);
            validerSkjema();

            document.getElementById('kjopKnapp').setAttribute('disabled','');
            $('#oppdaterKnapp').html(
                "<button onClick='lagreEndretBillett("+data.billettId+")'>Lagre endret billett</button>" +
                "<button onClick='avbrytBillettEndring()'>Avbryt billettendring</button>");
        }
    })
}

function lagreEndretBillett(billettId) {
    if (validerSkjema()) {
        let billett = lagNyBillett();
        $.post("/oppdaterBillett?billettId=" + billettId, billett, function () {
            printBillettArray();
        });
        document.getElementById('bestillingsskjema').reset();
        document.getElementById('kjopKnapp').removeAttribute("disabled");
        $('#oppdaterKnapp').html("<br>");
    }
}

function avbrytBillettEndring() {
    document.getElementById('bestillingsskjema').reset();
    document.getElementById('kjopKnapp').removeAttribute("disabled");
    $('#oppdaterKnapp').html("<br>");
}

// ticket delete functions ---------------------------------------------------------------------------------------------

function slettAlleBilletter() {
    $.post("slettAlleBilletter", function (){
        printBillettArray();
    });
}

function slettBillett(billettId) {
    $.ajax({
        url : "/slettBillett",
        type : "DELETE",
        data: { billettId: billettId },
        success : function (){
            printBillettArray();
        }
    })
}

// ticket display functions --------------------------------------------------------------------------------------------

function printBillettArray() {
    $.get("/hentAlleBilletter", async function (billettArray) {
        let printTable = (
            "<tr>" +
            "<th>Film</th><th>Antall</th>" +
            "<th>Navn</th><th>Etternavn</th>" +
            "<th>Telefonnr</th><th>Epost</th>" +
            "<th>Valg</th>" +
            "</tr>"
        );
        for (const i of billettArray) {
            printTable += (
                "<tr>" +
                "<td>" + await hentFilmTittel(i.film) + "</td><td>" + i.antall + "</td>" +
                "<td>" + i.fornavn + "</td><td>" + i.etternavn + "</td>" +
                "<td>" + i.telefonnr + "</td><td>" + i.epost + "</td>" +
                "<td>" +
                "<button onClick='endreBillett("+ i.billettId +")'>Endre Billett</button>" +
                "<button onClick='slettBillett("+ i.billettId +")'>Slett Billett</button>" +
                "</td>" +
                "</tr>"
            );
        }
        $('#billettListe').html(printTable);
    })
        .fail(function (){
            $('#billettListe').html("<span style='color:red'>Du må være logget inn for å se billettlisten</span>");
        });
}

// dummy info fill function --------------------------------------------------------------------------------------------

function fyllSkjema() {
    $('#filmSelect').val(2);
    $('#antall').val("1");
    $('#fornavn').val("abc");
    $('#etternavn').val("def");
    $('#telefonnr').val("123");
    $('#epost').val("abc@def");
    validerSkjema();
}