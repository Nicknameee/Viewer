alter table users add column language varchar(32);
update users set language = 'EN';