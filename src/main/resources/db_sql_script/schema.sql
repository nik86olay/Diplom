create schema Data;

create table Data.users_data
(
    login    varchar(255) not null,
    password varchar(255) not null,
    primary key (login)
);

create table Data.files_data
(
    filename      varchar(255)   not null,
    date          TIMESTAMPTZ(6) not null,
    file_content  bytea          not null,
    size          bigint         not null,
    user_username varchar(255),
    primary key (filename)
);
