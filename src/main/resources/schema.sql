create table Member
(
    id bigint       not null auto_increment,
    email     varchar(255) not null,
    password  varchar(255) not null,
    primary key (member_id)
);

create table Product
(
    id        bigint       not null auto_increment,
    member_id bigint       not null,
    name      varchar(255) not null,
    price     bigint       not null,
    imageUrl  varchar(255) not null,
    primary key (id, member_id),
    foreign key (member_id) references Member (member_id)
);

insert into Member (email, password)
values ('admin', 'admin');
