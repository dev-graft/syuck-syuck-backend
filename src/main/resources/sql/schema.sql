drop table if exists auth_session;
drop table if exists member;
drop table if exists follow;
drop table if exists friend_relation;
drop table if exists time_line;

create table member
(
    member_id     varchar(255) not null,
    created_at    timestamp,
    updated_at    timestamp,
    nickname      varchar(255) not null,
    password      varchar(255) not null,
    profile_image varchar(255),
    state_message varchar(255),
    status        varchar(255),
    primary key (member_id)
);

create table auth_session
(
    uniq_id       varchar(255)          not null,
    created_at    timestamp,
    updated_at    timestamp,
    access_status integer default 0     not null,
    block         boolean default false not null,
    connect       boolean default false not null,
    device_name   varchar(255),
    member_id     varchar(255)          not null,
    os            integer,
    push_token    varchar(255),
    version       varchar(255),
    primary key (uniq_id)
);

CREATE TABLE follow
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    follower_id  VARCHAR(255),
    following_id VARCHAR(255),
    CONSTRAINT pk_follow PRIMARY KEY (id)
);

create table friend_relation
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  timestamp,
    updated_at  timestamp,
    are_friends boolean      not null,
    receiver    varchar(255) not null,
    sender      varchar(255) not null,
    primary key (id)
);

create table time_line
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    code       varchar(255),
    content    varchar(255),
    member_id  varchar(255),
    tag        varchar(255),
    primary key (id)
)