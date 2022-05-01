CREATE TABLE HOSPITAL (
    id SERIAL,
    name VARCHAR(50),
    address VARCHAR(100),
    zipcode CHAR(7),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(36) NOT NULL DEFAULT 'system',
    updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by CHAR(36) NOT NULL DEFAULT 'system', 
    version bigint NOT NULL DEFAULT 0,
    CONSTRAINT pk_hospital PRIMARY KEY(id)
);

CREATE TABLE DOCTOR_GENRE (
    id SERIAL,
    name VARCHAR(30),
    CONSTRAINT pk_doctor_genre PRIMARY KEY(id)
);

CREATE TABLE DOCTOR (
    doctor_id SERIAL,
    hospital_id INTEGER,
    doctor_genre_id INTEGER,
    name VARCHAR(50),
    CONSTRAINT pk_doctor PRIMARY KEY(doctor_id, hospital_id),
    CONSTRAINT fk_doctor FOREIGN KEY(hospital_id) REFERENCES hospital(id),
    CONSTRAINT fk_doctor_genre FOREIGN KEY(doctor_genre_id) REFERENCES doctor_genre(id)
);

CREATE TABLE HOSPITAL_CATEGORY (
    id SERIAL,
    name VARCHAR(50),
    CONSTRAINT pk_hospital_category PRIMARY KEY(id)
);

CREATE TABLE HOSPITAL_HOSPITAL_CATEGORY_REL (
    hospital_id INTEGER,
    hospital_category_id INTEGER,
    CONSTRAINT pk_hospital_hospital_category_rel PRIMARY KEY(hospital_id, hospital_category_id),
    CONSTRAINT fk_hospital FOREIGN KEY(hospital_id) REFERENCES hospital(id),
    CONSTRAINT fk_hospital_category FOREIGN KEY(hospital_category_id) REFERENCES hospital_category(id)
);
