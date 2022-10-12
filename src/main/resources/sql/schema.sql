drop table if exists auth_session;
drop table if exists member;

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
    uniq_id     varchar(255) not null,
    created_at  timestamp,
    updated_at  timestamp,
    block       boolean      not null,
    device_name varchar(255),
    member_id   varchar(255) not null,
    os          varchar(255),
    push_token  varchar(255),
    version     varchar(255),
    primary key (uniq_id)
)