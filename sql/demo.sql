create table turn_point
(
    id         serial
        primary key,
    coordinate geometry(Point, 4326) not null,
    index      varchar               not null
);
