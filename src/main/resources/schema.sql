    create table users_data
    (
        login    varchar(255) not null,
        password varchar(255) not null,
        primary key (login)
    );

insert into users_data (login, password)
values ('user1@gmail.com', '$2a$10$h7QmnlSEXLF2eQS4lIlRX.FXGZ0U8tpcLYi8IPkgLG/sqQiDA/HlG');