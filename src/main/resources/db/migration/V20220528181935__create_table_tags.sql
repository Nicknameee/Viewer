create table if not exists tags(
    id serial primary key,
    tag varchar,
    article_id int references articles(id)
)