$(document).ready(function(){
    hentAlleFilmer();
});
function hentAlleFilmer() {
    $.get("/hentFilmListe", function(filmer) {
        populerFilmDropdown(filmer);
    });
}
function populerFilmDropdown(filmer){
    let ut = "";
    for(const film of filmer){
        ut+="<option value='"+film.tittel+"'>"+film.tittel+"</option>";
    }
    $("#film").html(ut);
}

// error message functions ------------------------------------------------------------------------

const feilmelding = {
    film: "Du må velge en film",
    antall: "Du må velge et antall",
    navn: "Du må skrive inn et navn, kan kun inneholde bokstaver",
    telefonnr: "Du må skrive inn et telefonnummer, kan kun inneholde tall",
    epost: "Du må skrive inn en gyldig epost-adresse"
}

function toggleFeilmelding(id, ugyldigInput, feilmelding) {
    if (ugyldigInput){
        $('#'+id+'UgyldigMelding').html(feilmelding);
    } else {
        $('#'+id+'UgyldigMelding').html("");
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
    let input = document.getElementById(id).value;
    let inputGyldig = regExp.test(input);
    toggleFeilmelding(id, !inputGyldig, feilmelding);
    return inputGyldig;
}

$(document).ready(function () {
    $('#film').change(function() {validerInput("film", regExp.film, feilmelding.film)});
    $('#antall').change(function() {validerInput("antall", regExp.antall, feilmelding.antall)});
    $('#fornavn').change(function(){validerInput("fornavn", regExp.navn, feilmelding.navn)});
    $('#etternavn').change(function(){validerInput("etternavn", regExp.navn, feilmelding.navn)});
    $('#telefonnr').change(function(){validerInput("telefonnr", regExp.telefonnr, feilmelding.telefonnr)});
    $('#epost').change(function(){validerInput("epost", regExp.epost, feilmelding.epost)});

    $('#fyllSkjemaKnapp').click(function(){fyllSkjema()});
});

// ticket creation functions ----------------------------------------------------------

function lagNyBillett(){
    return {
        film: $('#film').val(),
        antall: $('#antall').val(),
        fornavn: $('#fornavn').val(),
        etternavn: $('#etternavn').val(),
        telefonnr: $('#telefonnr').val(),
        epost: $('#epost').val()
    };
}

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

function kjopBillett(){
    if (validerSkjema()){
        let billett = lagNyBillett();
        $.post("/lagreBillett", billett, function(){
            printBillettArray()
            ;}
        );
        document.getElementById('bestillingsskjema').reset();
    }
}

function slettAlleBilletter() {
    $.post("/slettAlleBilletter");
    printBillettArray();
}

// ticket array display functions -----------------------------------------------------------------

function printBillettArray() {
    $.get("/hentAlleBilletter", function(billettArray){
        let printTable = (
            "<tr>" +
            "<th>Film</th><th>Antall</th>" +
            "<th>Navn</th><th>Etternavn</th>" +
            "<th>Telefonnr</th><th>Epost</th>" +
            "</tr>"
        );
        for (const i of billettArray) {
            printTable += (
                "<tr>" +
                "<td>"+i.film+"</td><td>"+i.antall+"</td>" +
                "<td>"+i.fornavn+"</td><td>"+i.etternavn+"</td>" +
                "<td>"+i.telefonnr+"</td><td>"+i.epost+"</td>" +
                "</tr>"
            );
        }
        $('#billettListe').html(printTable);
    })
}

function fyllSkjema(){
    $('#film').val("Lord of the Shrimp");
    $('#antall').val("1");
    $('#fornavn').val("abc");
    $('#etternavn').val("def");
    $('#telefonnr').val("123");
    $('#epost').val("abc@def");
}