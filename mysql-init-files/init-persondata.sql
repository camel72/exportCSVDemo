create table person_data
(
    id            bigint,
    date_of_death date,
    affiliation   varchar(255),
    birth_date    date,
    city          varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    number        varchar(255),
    ssin          varchar(255),
    street        varchar(255),
    primary key (id)
);

create index idx_last_name on person_data (last_name);
create index idx_first_name on person_data (first_name);
create index idx_birth_date on person_data (birth_date);
create index idx_street_number on person_data (street, number);
create index idx_names on person_data (last_name, first_name);
create index idx_names_birth_date on person_data (last_name, first_name, birth_date);

create table user
(
    id            bigint,
    name          varchar(255),
    email_address varchar(255),
    primary key (id)
);

create index idx_name on user (name);

insert into user (id, name, email_address)
VALUES (1, 'Kristof Camelbeke', 'kristof.camelbeke@gmail.com');