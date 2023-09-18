--1

SELECT * FROM teacher;

--2

SELECT * FROM student_group WHERE speciality='ЭВМ';

--3

SELECT
    DISTINCT t.id,
    tgs.audiencenumber
FROM teacher as t
    JOIN teacher_teaches_subjects_in_groups AS tgs on t.id = tgs.personalid
where tgs.name = '18П';

--4

SELECT s.name, s.id
FROM teacher as t
    JOIN subject as s ON t.speciality LIKE CONCAT('%', s.speciality, '%')
WHERE t.surname = 'Костин';

--5

SELECT
    teacher_teaches_subjects_in_groups.id
FROM
    teacher_teaches_subjects_in_groups
    JOIN teacher ON teacher.id = teacher_teaches_subjects_in_groups.personalid
WHERE
    teacher.surname = 'Фролов';

--6

SELECT * from subject where speciality = 'АСОИ';

--7

SELECT * FROM teacher WHERE speciality LIKE '%АСОИ%';

--8

SELECT teacher.surname
FROM teacher
    JOIN teacher_teaches_subjects_in_groups ON teacher_teaches_subjects_in_groups.personalid = teacher.id
WHERE
    teacher_teaches_subjects_in_groups.audiencenumber = 210;

--9

SELECT distinct s.name, sg.name
from
    teacher_teaches_subjects_in_groups as tgs
    join subject as s on s.id = tgs.name
    join student_group as sg on sg.id = tgs.id
where
    tgs.audiencenumber > 100
    AND tgs.audiencenumber < 200;

--10

--11

SELECT SUM(count) AS total
FROM student_group
WHERE speciality = 'ЭВМ';

--12

SELECT id from teacher where speciality like '%ЭВМ%';

--13

SELECT ttsg.name
FROM
    teacher_teaches_subjects_in_groups as ttsg
WHERE ttsg.name NOT IN (
        SELECT DISTINCT ttsg.name
        FROM
            teacher_teaches_subjects_in_groups ttsg
            LEFT JOIN student_group sg ON ttsg.id = sg.id
        WHERE sg.id IS NULL
    )
GROUP BY ttsg.name;

--14

SELECT teacher.surname
FROM teacher
    JOIN subject ON teacher.speciality LIKE concat('%', subject.speciality, '%')
WHERE subject.id = '14П';

--15

SELECT
    distinct s.id,
    s.name,
    s.speciality
from subject as s
    join teacher_teaches_subjects_in_groups as tgs on s.id = tgs.name
where s.id NOT IN (
        SELECT s.id
        from subject as s
            JOIN teacher as t ON t.speciality LIKE '%' || s.speciality || '%'
        WHERE t.id = '221Л'
    );

--16

SELECT *
FROM subject
WHERE name NOT IN (
        SELECT DISTINCT name
        from student_group
        WHERE id = 'М-6'
    );

--17

SELECT *
from teacher
    JOIN teacher_teaches_subjects_in_groups ON teacher.id = teacher_teaches_subjects_in_groups.personalid
WHERE
    teacher_teaches_subjects_in_groups.id = '3Г'
    OR teacher_teaches_subjects_in_groups.id = '8Г'
    AND teacher.position = 'Доцент';

--18

SELECT
    tgs.name,
    tgs.personalid,
    tgs.id
from
    teacher_teaches_subjects_in_groups as tgs
where tgs.personalid in (
        select
            t.id
        from teacher as t
        where
            t.department LIKE '%ЭВМ%'
            AND t.speciality LIKE '%АСОИ%'
    );

--19

SELECT DISTINCT sg.id
FROM student_group as sg
    INNER JOIN teacher as t on sg.speciality = t.speciality;

--20

SELECT teacher.id
FROM teacher
    JOIN teacher_teaches_subjects_in_groups ON teacher.id = teacher_teaches_subjects_in_groups.personalid
    JOIN student_group ON student_group.id = teacher_teaches_subjects_in_groups.id
WHERE
    teacher.department = 'ЭВМ'
    AND teacher.speciality = student_group.speciality;

--21

select sg.speciality
from student_group as sg
    join teacher as t on t.speciality LIKE '%' || sg.speciality || '%'
where t.id in (
        select id
        from teacher
        where department = 'АСУ'
    );

--22

--23

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

--24

--25

SELECT DISTINCT sg.id
from student_group sg
WHERE sg.id NOT IN (
        SELECT DISTINCT tsg.id
        FROM
            teacher_teaches_subjects_in_groups tsg
        WHERE tsg.personalid = '430Л'
    );

--26

SELECT DISTINCT t.id
FROM teacher as t
    JOIN teacher_teaches_subjects_in_groups as ttsg ON t.id = ttsg.personalId
    JOIN student_group sg ON ttsg.id = sg.id
WHERE
    sg.name = 'С-14'
    AND ttsg.name != '12П';

--second part

--23

SELECT DISTINCT p1.id
FROM POSTAVSHIKI AS p1
    JOIN SPJ AS spj1 ON p1.id = spj1.p_id
    JOIN DETAILS AS d1 ON spj1.d_id = d1.id
    JOIN SPJ AS spj2 ON d1.id = spj2.d_id
    JOIN POSTAVSHIKI AS p2 ON spj2.p_id = p2.id
    JOIN DETAILS AS d2 ON spj2.d_id = d2.id
WHERE d2.color = 'Красный';

--20

SELECT DISTINCT d.color
FROM POSTAVSHIKI AS p
    JOIN SPJ AS spj ON p.id = spj.p_id
    JOIN DETAILS AS d ON spj.d_id = d.id
WHERE p.id = 'П1';

--28

SELECT DISTINCT p.id
FROM PROJECTS p
    JOIN SPJ spj ON p.id = spj.j_id
    JOIN DETAILS d ON spj.d_id = d.id
    JOIN POSTAVSHIKI ps ON spj.p_id = ps.id
WHERE
    d.color <> 'Красный'
    OR (
        d.color = 'Красный'
        AND ps.city <> 'Москва'
    )
    OR (
        d.color IS NULL
        AND ps.city = 'Москва'
    );

--5

SELECT DISTINCT color, city FROM DETAILS;

--6

SELECT
    spj.p_id,
    spj.d_id,
    spj.j_id
FROM SPJ spj
    JOIN POSTAVSHIKI p ON spj.p_id = p.id
    JOIN DETAILS d ON spj.d_id = d.id
    JOIN PROJECTS pr ON spj.j_id = pr.id
WHERE
    p.city = d.city
    AND p.city = pr.city;

--10
SELECT DISTINCT d.id
FROM POSTAVSHIKI p
JOIN SPJ spj ON p.id = spj.p_id
JOIN DETAILS d ON spj.d_id = d.id
JOIN PROJECTS pr ON spj.j_id = pr.id
WHERE p.city = 'Таллинн' AND pr.city = 'Таллинн';


SELECT details.id from details JOIN postavshiki ON postavshiki.id = details.

--14

SELECT
    DISTINCT spj1.d_id,
    spj2.d_id
FROM SPJ spj1
    JOIN SPJ spj2 ON spj1.p_id = spj2.p_id
WHERE spj1.d_id <> spj2.d_id;

--34

SELECT DISTINCT d.id
FROM DETAILS d
    JOIN SPJ spj ON d.id = spj.d_id
    JOIN POSTAVSHIKI p ON spj.p_id = p.id
    JOIN PROJECTS pr ON spj.j_id = pr.id
WHERE
    p.city = 'Москва'
    OR pr.city = 'Москва';

--19

SELECT DISTINCT pr.name
FROM POSTAVSHIKI p
    JOIN SPJ spj ON p.id = spj.p_id
    JOIN PROJECTS pr ON spj.j_id = pr.id
WHERE p.id = 'П1';

--35

SELECT DISTINCT p.id, d.id
FROM POSTAVSHIKI p
    CROSS JOIN DETAILS d
WHERE NOT EXISTS (
        SELECT 1
        FROM SPJ spj
        WHERE
            spj.p_id = p.id
            AND spj.d_id = d.id
    );