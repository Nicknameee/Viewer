create table if not exists articles(
    id serial primary key,
    name varchar unique,
    text varchar(5000),
    secret varchar,
    folder_name varchar,
    folder_id varchar
)