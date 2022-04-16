create table if not exists resources(
    filename varchar(255) primary key,
    filetype varchar,
    size int,
    article_name varchar references articles(name)
)