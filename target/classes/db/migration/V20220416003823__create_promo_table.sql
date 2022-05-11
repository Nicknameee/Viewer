create table if not exists promo(
    id serial,
    code varchar unique,
    type varchar
)