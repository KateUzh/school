--Подключиться с помощью IDEA к базе данных и выполнить простой запрос select * from student.
select * from student s

--Получить всех студентов, возраст которых находится между 10 и 50.
select * from student s where s.age between 10 and 50

--Получить всех студентов, но отобразить только список их имен.
select s.name from student s

--Получить всех студентов, у которых в имени присутствует буква a.
select * from student s where s."name" like '%a%'

--Получить всех студентов, у которых возраст меньше идентификатора.
select * from student s where s.age < s.id

--Получить всех студентов упорядоченных по возрасту.
select * from student s order by age