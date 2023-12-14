create table task(
    id BIGINT  NOT NULL  AUTO_INCREMENT,
    name TEXT,
    status TEXT,
    duration_in_seconds BIGINT,
    created TIMESTAMP,
    started TIMESTAMP,
    finished TIMESTAMP,
    thread_name TEXT,

    primary key (id)
)