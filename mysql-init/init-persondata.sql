create table person_data
    (id bigint, date_of_death date, affiliation varchar(255), birth_date date, city varchar(255), first_name varchar(255), last_name varchar(255), number varchar(255), ssin varchar(255), street varchar(255), primary key (id));

create index idx_last_name on person_data (last_name);
create index idx_first_name on person_data (first_name);
create index idx_names on person_data(last_name,first_name);