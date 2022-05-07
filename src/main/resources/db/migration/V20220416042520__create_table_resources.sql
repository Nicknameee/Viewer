create table if not exists resources(
    id serial primary key,
    filename varchar(255) unique,
    filetype varchar,
    size int,
    article_id int references articles(id),
    file_id varchar
)