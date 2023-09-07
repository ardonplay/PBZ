#1 SELECT * FROM teacher;

#2 select * from student_group where speciality='ЭВМ';

#4
SELECT s.title, s.id FROM teacher as t JOIN subject as s ON t.specialization LIKE CONCAT('%', s.specialization ,'%') WHERE t.surname = 'Фролов';

#5
SELECT teacher_teaches_subjects_in_groups.id FROM teacher_teaches_subjects_in_groups JOIN teacher ON teacher.id = teacher_teaches_subjects_in_groups.personalid WHERE teacher.surname = 'Фролов';

#8
SELECT teacher.surname FROM teacher JOIN teacher_teaches_subjects_in_groups ON teacher_teaches_subjects_in_groups.personalid = teacher.id WHERE teacher_teaches_subjects_in_groups.audiencenumber = 210;

#11
SELECT SUM(count) AS total FROM student_group WHERE speciality = 'ЭВМ';

#14
SELECT teacher.surname FROM teacher JOIN subject ON  teacher.speciality LIKE concat('%',subject.speciality,'%') WHERE subject.id = '14П';

#17
SELECT * from teacher JOIN teacher_teaches_subjects_in_groups ON teacher.id = teacher_teaches_subjects_in_groups.personalid WHERE teacher_teaches_subjects_in_groups.id = '3Г' OR teacher_teaches_subjects_in_groups.id = '8Г' AND teacher.position='Доцент';

#20
SELECT teacher.id FROM teacher JOIN teacher_teaches_subjects_in_groups 
ON teacher.id = teacher_teaches_subjects_in_groups.personalid JOIN student_group ON student_group.id = teacher_teaches_subjects_in_groups.id
WHERE teacher.department = 'ЭВМ' AND teacher.speciality = student_group.speciality;

#23
SELECT DISTINCT sg.id
FROM student_group AS sg
JOIN teacher_teaches_subjects_in_groups AS ttsg ON sg.id = ttsg.id
JOIN subject s ON ttsg.name = s.id
WHERE ttsg.name IN (
    SELECT ttsg.name
    FROM student_group sg
    JOIN teacher_teaches_subjects_in_groups AS ttsg ON sg.id = ttsg.id
    WHERE sg.name = 'АС-8'
);

#26
SELECT DISTINCT t.id FROM teacher as t
JOIN teacher_teaches_subjects_in_groups as ttsg ON t.id = ttsg.personalId 
JOIN student_group sg ON ttsg.id = sg.id
WHERE sg.name = 'С-14' AND ttsg.name != '12П';
