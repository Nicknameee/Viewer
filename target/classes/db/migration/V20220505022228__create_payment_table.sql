create table if not exists payment(
    id serial,
    card varchar unique,
    iban varchar,
    bank varchar,
    receiver varchar
)