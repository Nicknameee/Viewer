create table if not exists verification(
    id serial,
    mail varchar unique,
    action_type varchar,
    code varchar unique
)