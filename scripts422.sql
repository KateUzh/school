create table persons (
name text primary key,
age smallserial,
driver_status boolean default 'no',
car_id serial references cars (id)
)

create table cars (
id serial primary key,
brand text,
model text,
cost money,
person_name text references persons (name)
)