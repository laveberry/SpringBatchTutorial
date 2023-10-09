--주문 테이블 생성
create table `spring_batch`.`orders` (
    `id` int not null auto_increment,
    `order_item` varchar(45) null,
    `price` int null,
    `order_date` date null,
    primary key (`id`)
);

--정산 테이블 생성
create table `spring_batch`.`accounts` (
    `id` int not null auto_increment,
    `order_item` varchar(45) null,
    `price` int null,
    `order_date` date null,
    `account_date` date null,
    primary key (`id`)
);

insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('카카오 선물', 15000, '2023-03-01');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('배달주문', 18000, '2023-03-01');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('알라딘', 14000, '2023-03-02');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('아이스크림', 3800, '2023-03-03');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('치킨', 21000, '2022-03-04');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('커피', 4000, '2022-03-04');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('알라딘', 13800, '2022-03-05');
insert into spring_batch.orders(`order_item`, `price`, `order_date`) values ('카카오 선물', 5500, '2022-03-06');


select *
from spring_batch.orders;

select *
from spring_batch.accounts;