drop database if exists central_park;
create database central_park;
use central_park;

-- 用户表
create table t_user(
    id           varchar(100) primary key,
    username     varchar(200) not null unique,
    password     varchar(200) not null,
    online_state int          not null,
    portrait     varchar(200) not null,
    regist_date  datetime     not null,
    gender       boolean,
    email        varchar(200)
) charset = utf8;