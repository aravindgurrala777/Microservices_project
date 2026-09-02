Alter table orders alter column id restart with 101;

insert into orders( user_id, product, amount) values( 1, 'Laptop', 65000.00);
insert into orders( user_id, product, amount) values( 1, 'Charger', 3000.00);
insert into orders( user_id, product, amount) values( 2, 'Mobile Phone', 32000.00);
insert into orders( user_id, product, amount) values( 3, 'Headset', 1800.00);
insert into orders( user_id, product, amount) values( 4, 'Mouse', 1200.00);
insert into orders( user_id, product, amount) values( 5, 'TV', 36000.00);