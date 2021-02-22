CREATE SCHEMA person;
CREATE SEQUENCE person.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
CREATE SEQUENCE hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
CREATE table person.person (person_id uuid not null, email_id varchar(255),age varchar(255), favourite_colour varchar(255), first_name varchar(255), last_name varchar(255), created_by varchar(255),creation_date timestamp,last_modified_by varchar(255),last_modified_date timestamp,action varchar(10), primary key (person_id));