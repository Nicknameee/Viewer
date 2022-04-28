create table if not exists articles(
    id serial,
    name varchar unique primary key,
    text varchar(5000)
)