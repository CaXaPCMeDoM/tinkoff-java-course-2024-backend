CREATE TABLE IF NOT EXISTS Link
(
    url_id          bigint generated always as identity,
    url             text                                    not null,

    last_check_time timestamp with time zone                not null,

    created_at      timestamp with time zone                not null,
    created_by      text                                    not null,

    primary key (url_id),
    unique (url)
)
