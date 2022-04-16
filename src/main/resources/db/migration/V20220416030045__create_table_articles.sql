create table if not exists articles(
    name varchar unique primary key,
    text varchar(5000)
)