drop table if exists billett;

create table billett
(
    billettId int auto_increment not null,
    film      varchar(255) not null,
    antall    int          not null,
    fornavn   varchar(255) not null,
    etternavn varchar(255) not null,
    telefonnr int          not null,
    epost     varchar(255) not null,
    primary key (billettId)
);