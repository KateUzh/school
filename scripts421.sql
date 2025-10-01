--Возраст студента не может быть меньше 16 лет.
alter table student
add constraint age_constraint check (age>16),

--Имена студентов должны быть уникальными и не равны нулю.
add constraint name_unique unique (name),
alter column name set not null

--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty add constraint name_color_unique unique (name, color)

--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table student alter column age set default 20