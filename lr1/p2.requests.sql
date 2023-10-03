--second part

--23

SELECT DISTINCT p_id
FROM SPJ
WHERE d_id IN(
        SELECT d_id
        FROM SPJ
        WHERE p_id IN(
                SELECT
                    DISTINCT p_id
                FROM SPJ
                WHERE d_id IN(
                        SELECT
                            id
                        FROM
                            details
                        WHERE
                            color = 'Красный'
                    )
            )
    );

--20

SELECT DISTINCT d.color
FROM POSTAVSHIKI AS p
    JOIN SPJ AS spj ON p.id = spj.p_id
    JOIN DETAILS AS d ON spj.d_id = d.id
WHERE p.id = 'П1';

--28

SELECT DISTINCT j_id
FROM SPJ
WHERE j_id NOT IN(
        SELECT DISTINCT j_id
        FROM SPJ
        WHERE p_id in(
                SELECT id
                FROM
                    postavshiki
                WHERE
                    city = 'Таллин'
            )
            and d_id in(
                SELECT id
                FROM details
                WHERE
                    color = 'Красный'
            )
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
WHERE
    p.city = 'Таллинн'
    AND pr.city = 'Таллинн';

SELECT details.id
FROM details
    JOIN postavshiki ON postavshiki.id = details.

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