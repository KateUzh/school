--Получить информацию обо всех студентах школы Хогвартс вместе с названиями факультетов.
select s.name, s.age, f."name"  from student s inner join faculty f on s.faculty_id = f.id

--Получить только тех студентов, у которых есть аватарки.
select s.name, s.age from student s right join avatar a on s.id = a.student_id