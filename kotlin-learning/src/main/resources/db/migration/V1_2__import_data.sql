-- organizer
INSERT INTO organizer (organization_name) VALUES
    ('組織1'),('組織2'),('組織3'),('組織4'),('組織5');

-- event
INSERT INTO event (organizer_id, event_name, starts_at, ends_at) VALUES
	(1, 'イベント1', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(1, 'イベント2', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(2, 'イベント3', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(2, 'イベント4', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(3, 'イベント5', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(3, 'イベント6', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(4, 'イベント7', '2024-05-02 10:30:00', '2024-05-02 12:30:00'),
	(5, 'イベント8', '2024-05-02 10:30:00', '2024-05-02 12:30:00');

-- participant
INSERT INTO participant(first_name, last_name, gender) VALUES
	('LeBron','James', 'M'),
	('Stephen','Curry', 'M'),
	('Kevin','Durant', 'M'),
	('Giannis','Antetokounmpo', 'M'),
	('Kawhi','Leonard', 'M'),
	('Luka','Dončić', 'M'),
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
    -- Dončić
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
