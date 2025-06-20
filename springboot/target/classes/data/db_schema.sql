CREATE USER 'lcgblog'@'%' IDENTIFIED BY 'lcgblog123';
GRANT ALL PRIVILEGES ON pizza_shop.* TO 'lcgblog'@'%';

select host,user from user;
show databases;


create table register_user
(
    id              bigint primary key auto_increment,
    username        varchar(20) unique not null,
    password        varchar(30)        not null,
    comment         varchar(50),
    created_time    datetime           not null,
    updated_time    datetime           not null,
    last_login_time datetime           not null
);