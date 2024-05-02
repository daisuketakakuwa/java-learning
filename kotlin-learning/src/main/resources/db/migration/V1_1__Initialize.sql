-- organizerテーブル
CREATE TABLE organizer (
    id VARCHAR(50) PRIMARY KEY,
    organization_name VARCHAR(300)
);

-- organizer用のsequence
CREATE SEQUENCE organizer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- eventテーブル
CREATE TABLE event (
    id VARCHAR(50) PRIMARY KEY,
    organizer_id VARCHAR(50) REFERENCES organizer(id),
    event_name VARCHAR(300),
    starts_at TIMESTAMP,
    ends_at TIMESTAMP
);

-- event用のsequence
CREATE SEQUENCE event_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- participantテーブル
CREATE TABLE participant (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    gender CHAR(1)
);

-- participant用のsequence
CREATE SEQUENCE participant_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- rel_event_participantテーブル
CREATE TABLE rel_event_participant (
    event_id VARCHAR(50) REFERENCES event(id),
    participant_id VARCHAR(50) REFERENCES participant(id),
    PRIMARY KEY (event_id, participant_id)
);
