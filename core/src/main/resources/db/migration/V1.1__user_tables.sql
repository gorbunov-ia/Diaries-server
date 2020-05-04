drop table if exists t_user_role;
drop table if exists t_role;
drop table if exists t_user;

create table t_user
(
    id        serial not null,
    login     varchar(32) not null,
    password  varchar(60) not null,
    email     varchar(255) not null,
    is_active bool not null,

    constraint pk_t_users primary key (id),
    constraint uix_t_users_login unique (login)
);

create table t_role
(
    id          serial not null,
    description varchar(32) not null,

    constraint pk_t_role primary key (id),
    constraint uix_t_role_description unique (description)
);

insert into t_role ( description ) values ('USER'),('ADMIN');

create table t_user_role
(
    user_id int not null,
    role_id int not null,

    constraint pk_t_user_role primary key (user_id, role_id),
    constraint fk_t_user_role_t_role foreign key (role_id) references t_role(id),
    constraint fk_t_user_role_t_user foreign key (user_id) references t_user(id)
);
