drop table if exists client;
create table client
(
    id      serial
        constraint client_pk
            primary key,
    name    varchar(100) not null,
    domain  varchar(100) not null,
    address varchar(100) not null
);

drop table if exists contact;
create table contact
(
    id         serial
        constraint contact_pk
            primary key,
    first_name varchar(100)   not null,
    last_name  varchar(100)   not null,
    email      varchar(100)   not null,
    client_id  integer unique null,
    foreign key (client_id) references client (id) on delete cascade
);

drop table if exists task;
create table task
(
    id               serial
        constraint task_pk
            primary key,
    description      varchar(100)   not null,
    status           varchar(100)   not null,
    execution_period timestamp      not null,
    contact_id       integer unique null,
    foreign key (contact_id) references contact (id)
);

drop table if exists users;
create table users
(
    id       serial
        constraint users_pk
            primary key,
    username varchar(100) not null,
    email    varchar(100) not null,
    password varchar(100) not null
);

drop table if exists roles;
create table roles
(
    id   serial
        constraint roles_pk
            primary key,
    name varchar(100) not null
);
insert into roles
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

drop table if exists users_roles;
create table users_roles
(
    user_id integer not null,
    role_id integer not null,
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id),
    unique (user_id, role_id)
);

insert into users(username, email, password)
values ('test', 'test@gmail.com', '$2a$10$aYEJEDSlfHMKQ2WObmlbjeJx/Q4ihRjrSPOZE9PAwD5haZoKa.NvO');
insert into users_roles(user_id, role_id)
values (1, 2);

insert into client(id, name, domain, address)
values (1, 'Client 1', 'Healthcare', 'Address 1'),
       (2, 'Client 2', 'IT', 'Address 2'),
       (3, 'Client 3', 'Finance', 'Address 3');
insert into contact(id, first_name, last_name, email, client_id)
values (1, 'Elon', 'Musk', 'elon@gmail.com', 2),
       (2, 'Denys', 'Bondarenko', 'denys@gmail.com', 1),
       (3, 'Warren', 'Buffett', 'warren@gmail.com', 3);
insert into task(id, description, status, execution_period, contact_id)
values (1, 'Finish house work', 'IN_PROGRESS', current_timestamp, 2),
       (2, 'Fly to the Mars', 'OPEN', current_timestamp, 1),
       (3, 'Buy new business', 'DONE', current_timestamp, 3),
       (4, 'Track progress', 'OPEN', current_timestamp, null);

SELECT setval(pg_get_serial_sequence('client', 'id'), (SELECT MAX(id) FROM client) + 1);
SELECT setval(pg_get_serial_sequence('contact', 'id'), (SELECT MAX(id) FROM contact) + 1);
SELECT setval(pg_get_serial_sequence('task', 'id'), (SELECT MAX(id) FROM task) + 1);