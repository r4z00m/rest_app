create table sensor(
    id int generated by default as identity primary key,
    name varchar(30) not null unique
);

create table measurement(
    id int generated by default as identity primary key,
    sensor_name varchar(30) references sensor(name) on delete cascade,
    value decimal,
    is_raining boolean,
    measured_at time
);