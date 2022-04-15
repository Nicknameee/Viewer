create table if not exists resources(
    id serial,
    article_id int8,
    filename varchar(255) unique,
    filetype varchar,
    size int
)