drop table member if exists;
create table member
(
    member_id     varchar(255) not null,
    nickname      varchar(255) not null,
    password      varchar(255) not null,
    profile_image varchar(255),
    state_message varchar(255),
    status        varchar(2),
    created_at    timestamp,
    updated_at    timestamp,
    primary key (member_id)
)