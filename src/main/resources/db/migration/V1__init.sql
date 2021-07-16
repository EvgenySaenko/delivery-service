
drop table if exists delivery_orders;
create table delivery_orders (
   id                    bigserial primary key,
   first_name            VARCHAR(30) NOT NULL UNIQUE,
   address_from          VARCHAR(255) NOT NULL,
   address_to            VARCHAR(255) NOT NULL,
   price                 numeric(8,2) NOT NULL,
   created_at            VARCHAR(255) NOT NULL,
   status                INTEGER NOT NULL
);


insert into delivery_orders(first_name,address_from,address_to, price,created_at,status)
values
('Сергей','Красноармейская 170','Красноармейская 190', 890.0, '12:36 13:07:2021',0),
('Евгений','Ворошиловский 120','Красноармейская 131', 1560.0, '12:36 13:07:2021',0);


