DELETE FROM rel_event_participant;
DELETE FROM participant;
DELETE FROM event;
DELETE FROM organizer;

ALTER SEQUENCE organizer_id_seq RESTART WITH 1;
ALTER SEQUENCE event_id_seq RESTART WITH 1;
ALTER SEQUENCE participant_id_seq RESTART WITH 1;

-- organizer
INSERT INTO organizer (organization_name) VALUES
    ('�g�D1'),('�g�D2'),('�g�D3'),('�g�D4'),('�g�D5');

-- event
INSERT INTO event (organizer_id, event_name, starts_at, ends_at) VALUES
	(1, '�C�x���g1', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(1, '�C�x���g2', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(2, '�C�x���g3', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(2, '�C�x���g4', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(3, '�C�x���g5', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(3, '�C�x���g6', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(4, '�C�x���g7', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(5, '�C�x���g8', '2024-05-02 10:30:00', '2024-05-02 12:30:00');

-- participant
INSERT INTO participant(first_name, last_name, gender) VALUES
	('LeBron','James', 'M'),
	('Stephen','Curry', 'M'),
	('Kevin','Durant', 'M'),
	('Giannis','Antetokounmpo', 'M'),
	('Kawhi','Leonard', 'M'),
	('Luka','Don?i?', 'M'),
	('Diana','Taurasi', 'F'),
	('Sue','Bird', 'F'),
	('Breanna','Stewart', 'F'),
	('Aja','Wilson', 'F');

-- rel_event_participant
INSERT INTO rel_event_participant VALUES
    -- Lebron
    (1, 1),
    (2, 1),
    (3, 1),
    -- Curry
    (4, 2),
    (5, 2),
    (6, 2),
    -- Durant
    (2, 3),
    (3, 3),
    (4, 3),
    -- Antetokounmpo
    (6, 4),
    (7, 4),
    (8, 4),
    -- Leonard
    (3, 5),
    (4, 5),
    (5, 5),
    -- Don?i?
    (2, 6),
    (3, 6),
    (4, 6),
    -- Taurasi
    (5, 7),
    (6, 7),
    (7, 7),
    -- Bird
    (4, 8),
    (5, 8),
    (6, 8),
    -- Stewart
    (1, 9),
    (2, 9),
    (3, 9),
    -- Wilson
    (1, 10),
    (5, 10),
    (6, 10);

