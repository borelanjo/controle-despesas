create table t_account (
    id BIGSERIAL PRIMARY KEY,
    account_number BIGINT not null,
    balance DECIMAL(10,2) not null,
    deleted BOOLEAN not null,
    activated BOOLEAN not null,
    dt_created TIMESTAMP WITHOUT TIME ZONE not null,
    dt_updated TIMESTAMP WITHOUT TIME ZONE not null,
    CONSTRAINT uk_account_account_number UNIQUE (account_number)
);

create table t_transaction_history (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20),
    description VARCHAR(100),
    value DECIMAL(10,2) not null,
    id_account BIGINT,
    deleted BOOLEAN not null,
    activated BOOLEAN not null,
    dt_created TIMESTAMP WITHOUT TIME ZONE not null,
    dt_updated TIMESTAMP WITHOUT TIME ZONE not null,
    CONSTRAINT transaction_history_account_id_account FOREIGN KEY (id_account) REFERENCES t_account (id) ON DELETE CASCADE
);
