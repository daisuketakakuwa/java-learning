CREATE TABLE policy (
    id varchar(50),
    policy_holder_name varchar(50),
    policy_holder_gender char(1),
    policy_type varchar(100)
);

CREATE TABLE maintenance_progress (
    id varchar(50),
    status varchar(30),
    created_at datetime,
    policy_id varchar(50)
);
