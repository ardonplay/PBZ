CREATE TABLE
    TEACHER(
        id VARCHAR(4) PRIMARY KEY,
        surname VARCHAR(255) NOT NULL,
        position VARCHAR(255) NOT NULL,
        department VARCHAR(255) NOT NULL,
        speciality VARCHAR(255) NOT NULL,
        telephone VARCHAR(3) NOT NULL
    );

CREATE TABLE
    SUBJECT (
        id VARCHAR(4) PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        hours INT NOT NULL,
        speciality VARCHAR(255) NOT NULL,
        term INT NOT NULL
    );

CREATE TABLE
    STUDENT_GROUP (
        id VARCHAR(4) PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        count INT NOT NULL,
        speciality VARCHAR(255) NOT NULL,
        surnamePrefects VARCHAR(255) NOT NULL
    );

CREATE TABLE
    TEACHER_TEACHES_SUBJECTS_IN_GROUPS (
        id VARCHAR(4) NOT NULL,
        name VARCHAR(255) NOT NULL,
        personalId VARCHAR(4) NOT NULL,
        audienceNumber VARCHAR(255) NOT NULL
    );

INSERT INTO
    TEACHER (
        id,
        surname,
        position,
        department,
        speciality,
        telephone
    )
VALUES (
        '221Л',
        'Фролов',
        'Доцент',
        'ЭВМ',
        'АСОИ, ЭВМ',
        487
    ), (
        '222Л',
        'Костин',
        'Доцент',
        'ЭВМ',
        'ЭВМ',
        543
    ), (
        '225Л',
        'Бойко',
        'Профессор',
        'АСУ',
        'АСОИ, ЭВМ',
        112
    ), (
        '430Л',
        'Глазов',
        'Ассистент',
        'ТФ',
        'СД',
        421
    ), (
        '110Л',
        'Петров',
        'Ассистент',
        'Экономики',
        'Международная экономика',
        324
    );

INSERT INTO
    subject (
        id,
        name,
        hours,
        speciality,
        term
    )
VALUES ('12П', 'Мини ЭВМ', 36, 'ЭВМ', 1), ('14П', 'ПЭВМ', 72, 'ЭВМ', 2), ('17П', 'СУБД ПК', 48, 'АСОИ', 4), ('18П', 'ВКСС', 52, 'АСОИ', 6), ('34П', 'Физика', 30, 'СД', 6), (
        '22П',
        'Аудит',
        24,
        'Бухучета',
        3
    );

INSERT INTO
    student_group (
        id,
        name,
        count,
        speciality,
        surnameprefects
    )
VALUES (
        '8Г',
        'Э-12',
        18,
        'ЭВМ',
        'Иванова'
    ), (
        '7Г',
        'Э-15',
        22,
        'ЭВМ',
        'Сеткин'
    ), (
        '4Г',
        'АС-9',
        24,
        'АСОИ',
        'Балабанов'
    ), (
        '3Г',
        'АС-8',
        20,
        'АСОИ',
        'Чижов'
    ), (
        '17Г',
        'С-14',
        29,
        'СД',
        'Амросов'
    ), (
        '12Г',
        'М-6',
        16,
        'Международная экономика',
        'Трубин'
    ), (
        '10Г',
        'Б-4',
        21,
        'Бухучет',
        'Зязюткин'
    );

INSERT INTO
    teacher_teaches_subjects_in_groups (
        id,
        audiencenumber,
        name,
        personalid
    )
VALUES ('8Г', '12П', '222Л', 112), ('8Г', '14П', '221Л', 220), ('8Г', '17П', '222Л', 112), ('7Г', '14П', '221Л', 220), ('7Г', '17П', '222Л', 241), ('7Г', '18П', '225Л', 210), ('4Г', '12П', '222Л', 112), ('4Г', '18П', '225Л', 210), ('3Г', '12П', '222Л', 112), ('3Г', '17П', '221Л', 241), ('3Г', '18П', '225Л', 210), ('17Г', '12П', '222Л', 112), ('17Г', '22П', '110Л', 220), ('17Г', '34П', '430Л', 118), ('12Г', '12П', '222Л', 112), ('12Г', '22П', '110Л', 210), ('10Г', '12П', '222Л', 210), ('10Г', '22П', '110Л', 210);