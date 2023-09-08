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
        audienceNumber INT NOT NULL
    );

INSERT INTO
    teacher (id,surname,position,department,speciality,telephone)
VALUES 
    ('221Л','Фролов','Доцент','ЭВМ','АСОИ, ЭВМ',487), 
    ('222Л','Костин','Доцент','ЭВМ','ЭВМ',543), 
    ('225Л','Бойко','Профессор','АСУ','АСОИ, ЭВМ',112), 
    ('430Л','Глазов','Ассистент','ТФ','СД',421), 
    ('110Л','Петров','Ассистент','Экономики','Международная экономика',324);

INSERT INTO
    subject (id,name,hours,speciality,term)
VALUES 
        ('12П', 'Мини ЭВМ', 36, 'ЭВМ', 1),
        ('14П', 'ПЭВМ', 72, 'ЭВМ', 2),
        ('17П', 'СУБД ПК', 48, 'АСОИ', 4),
        ('18П', 'ВКСС', 52, 'АСОИ', 6), 
        ('34П', 'Физика', 30, 'СД', 6), 
        ('22П','Аудит',24,'Бухучета',3);

INSERT INTO
    student_group (id,name,count,speciality,surnameprefects)
VALUES
        ('8Г','Э-12',18,'ЭВМ','Иванова'), 
        ('7Г','Э-15',22,'ЭВМ','Сеткин'), 
        ('4Г','АС-9',24,'АСОИ','Балабанов'), 
        ('3Г','АС-8',20,'АСОИ','Чижов'), 
        ('17Г','С-14',29,'СД','Амросов'), 
        ('12Г','М-6',16,'Международная экономика','Трубин'), 
        ('10Г','Б-4',21,'Бухучет','Зязюткин');

INSERT INTO
    teacher_teaches_subjects_in_groups (id,name,personalid,audiencenumber)
VALUES 
        ('8Г', '12П', '222Л', 112),
        ('8Г', '14П', '221Л', 220),
        ('8Г', '17П', '222Л', 112),
        ('7Г', '14П', '221Л', 220),
        ('7Г', '17П', '222Л', 241),
        ('7Г', '18П', '225Л', 210),
        ('4Г', '12П', '222Л', 112), 
        ('4Г', '18П', '225Л', 210),
        ('3Г', '12П', '222Л', 112), 
        ('3Г', '17П', '221Л', 241), 
        ('3Г', '18П', '225Л', 210), 
        ('17Г', '12П', '222Л', 112), 
        ('17Г', '22П', '110Л', 220), 
        ('17Г', '34П', '430Л', 118), 
        ('12Г', '12П', '222Л', 112), 
        ('12Г', '22П', '110Л', 210), 
        ('10Г', '12П', '222Л', 210), 
        ('10Г', '22П', '110Л', 210);



CREATE TABLE POSTAVSHIKI (
    id VARCHAR(2) UNIQUE PRIMARY KEY,
    name VARCHAR(255),
    status INT,
    city VARCHAR(255)
);

CREATE TABLE DETAILS (
    id VARCHAR(2) UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    size INT NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE PROJECTS ( 
    id VARCHAR(3) UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE SPJ (
    p_id VARCHAR(2) NOT NULL,
    d_id VARCHAR(2) NOT NULL,
    j_id VARCHAR(3) NOT NULL,
    s INT NOT NULL
);

INSERT INTO POSTAVSHIKI 
VALUES 
    ('П1','Петров',20,'Москва'),
    ('П2','Синицин',10,'Таллинн'),	
    ('П3','Федоров',30,'Таллинн'),	
    ('П4','Чаянов',20,'Минск'),	
    ('П5','Крюков',30,'Киев');

INSERT INTO DETAILS
VALUES
    ('Д1','Болт','Красный',12,'Москва'),
    ('Д2','Гайка','Зеленая',17, 'Минск'),
    ('Д3','Диск','Черный',17,'Вильнюс'),
    ('Д4','Диск','Черный',14,'Москва'),
    ('Д5','Корпус','Красный',12,'Минск'),
    ('Д6','Крышки','Красный',19,'Москва');

INSERT INTO PROJECTS 
VALUES
    ('ПР1',	'ИПР1',	'Минск'),
    ('ПР2',	'ИПР2',	'Таллинн'),
    ('ПР3',	'ИПР3',	'Псков'),
    ('ПР4',	'ИПР4',	'Псков'),
    ('ПР5',	'ИПР4',	'Москва'),
    ('ПР6',	'ИПР6',	'Саратов'),
    ('ПР7',	'ИПР7',	'Москва');

INSERT INTO SPJ
VALUES
    ('П1','Д1','ПР1',200),
    ('П1','Д1','ПР2',700),
    ('П2','Д3','ПР1',400),
    ('П2','Д2','ПР2',200),
    ('П2','Д3','ПР3',200),
    ('П2','Д3','ПР4',500),
    ('П2','Д3','ПР5',600),
    ('П2','Д3','ПР6',400),
    ('П2','Д3','ПР7',800),
    ('П2','Д5','ПР2',100),
    ('П3','Д3','ПР1',200),
    ('П3','Д4','ПР2',500),
    ('П4','Д6','ПР3',300),
    ('П4','Д6','ПР7',300),
    ('П5','Д2','ПР2',200),
    ('П5','Д2','ПР4',100),
    ('П5','Д5','ПР5',500),
    ('П5','Д5','ПР7',100),
    ('П5','Д6','ПР2',200),
    ('П5','Д1','ПР2',100),
    ('П5','Д3','ПР4',200),
    ('П5','Д4','ПР4',800),
    ('П5','Д5','ПР4',400),
    ('П5','Д6','ПР4',500);
