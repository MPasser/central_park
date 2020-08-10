drop database if exists central_park;
create database central_park;
use central_park;

-- 用户表
create table t_user
(
    id            varchar(100) primary key,
    username      varchar(200) binary not null unique,
    password      varchar(200) binary not null,
    portrait      varchar(200) not null,
    register_date datetime     not null,
    gender        boolean      not null,
    email         varchar(200) binary
) charset = UTF8MB4;

-- 消息表
create table t_chat_message
(
    id              varchar(100) primary key,
    user_id         varchar(100) not null,
    sent_time       datetime     not null,
    message_type    int          not null, -- 0表示文本消息，1表示图片文件消息，2表示其他文件消息
    message_payload varchar(500) binary not null, -- type为0时payload为消息内容，type为1时payload为文件名
    message_ref     varchar(100),          -- type为0时ref为空，type为1时ref为文件地址

    foreign key (user_id) references t_user (id)
) charset = UTF8MB4;