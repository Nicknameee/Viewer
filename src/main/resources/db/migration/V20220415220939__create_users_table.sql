create table if not exists users(
    id serial,
    username varchar unique,
    mail varchar unique,
    password varchar,
    role varchar,
    status varchar,
    login_time timestamp,
    logout_time timestamp
);