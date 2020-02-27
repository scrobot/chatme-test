create sequence id_seq;

create table if not exists tasks
(
    id              bigint     not null default nextval('id_seq'),
    name            varchar(50) not null,
    is_verified     boolean     not null default false,
    created_at      timestamp   not null,
    updated_at      timestamp   not null,

    primary key (id)
);