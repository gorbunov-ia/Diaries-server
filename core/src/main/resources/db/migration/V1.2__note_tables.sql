drop table if exists t_note_element;
drop table if exists t_note;

create table t_note
(
    id            serial not null,
    description   varchar(64) not null,
    last_modified timestamp not null,
    sort_by       int not null,
    user_id       int not null,

    constraint pk_t_note primary key (id),
    constraint fk_t_note_t_user foreign key (user_id) references t_user(id)
);

create table t_note_element
(
    id            serial not null,
    description   varchar(64) not null,
    last_modified timestamp not null,
    sort_by       int not null,
    note_id       int not null,

    constraint pk_t_note_elements primary key (id),
    constraint uix_t_note_element_note_id_sort_by unique (note_id, sort_by),
    constraint fk_t_note_element_t_note foreign key (note_id) references t_note(id)
);
