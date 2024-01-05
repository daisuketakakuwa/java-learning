CREATE TABLE policy (
    policy_no char(8),
    policy_holder_name varchar(50),
    policy_holder_gender char(1),
    policy_type varchar(100),
    PRIMARY KEY(policy_no)
);

CREATE TABLE maintenance_progress (
    id varchar(50),
    status varchar(30),
    created_at datetime,
    policy_no char(8),
    PRIMARY KEY(id)
);
