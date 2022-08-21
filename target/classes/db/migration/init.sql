create table cash_balance
(
    id          bigint auto_increment not null,
    deposit     double                null,
    total_money double                null,
    deleted      bit              null,
    constraint pk_cash_balance primary key (id)
);

CREATE TABLE cash_balance_payement_history
(
    cash_balance_id     BIGINT NOT NULL,
    payement_history_id BIGINT NOT NULL,
    CONSTRAINT pk_cash_balance_payementhistory PRIMARY KEY (cash_balance_id, payement_history_id)
);

CREATE TABLE cathegory
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    deleted BIT(1)                NULL,
    CONSTRAINT pk_cathegory PRIMARY KEY (id)
);

CREATE TABLE costumer
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    card_number VARCHAR(255)          NULL,
    deleted     BIT(1)                NOT NULL,
    CONSTRAINT pk_costumer PRIMARY KEY (id)
);

CREATE TABLE discount
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    percent  FLOAT                 NULL,
    expired  BIT(1)                NULL,
    deleted BIT(1)                NULL,
    CONSTRAINT pk_discount PRIMARY KEY (id)
);
CREATE TABLE product
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255)          NULL,
    product_code VARCHAR(255)          NULL,
    type         VARCHAR(255)          NOT NULL,
    discount_id  BIGINT                NULL,
    price        DOUBLE                NOT NULL,
    cathegory_id BIGINT                NULL,
    deleted      BIT(1)                NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);
CREATE TABLE receipt
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    store_name     VARCHAR(255)          NULL,
    payment_method VARCHAR(255)          NULL,
    date           date                  NULL,
    user_id        BIGINT                NULL,
    final_price    DOUBLE                NULL,
    deleted       BIT(1)                NULL,
    zipcode        INT                   NULL,
    street_number  INT                   NULL,
    street         VARCHAR(255)          NULL,
    city           VARCHAR(255)          NULL,
    country        VARCHAR(255)          NULL,
    CONSTRAINT pk_receipt PRIMARY KEY (id)
);

CREATE TABLE receipt_product_list
(
    receipt_id      BIGINT NOT NULL,
    product_list_id BIGINT NOT NULL
);

CREATE TABLE receipt_per_product
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    product_name_id BIGINT                NULL,
    price           DOUBLE                NULL,
    quantity        INT                   NULL,
    total_price     DOUBLE                NULL,
    deleted        BIT(1)                NULL,
    CONSTRAINT pk_receipt_per_product PRIMARY KEY (id)
);
CREATE TABLE stock
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    product_id           BIGINT                NULL,
    quantity_per_product INT                   NOT NULL,
    deleted              BIT(1)                NOT NULL,
    zipcode              INT                   NULL,
    street_number        INT                   NULL,
    street               VARCHAR(255)          NULL,
    city                 VARCHAR(255)          NULL,
    country              VARCHAR(255)          NULL,
    CONSTRAINT pk_stock PRIMARY KEY (id)
);
CREATE TABLE transaction
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    total          DOUBLE                NOT NULL,
    `description`  VARCHAR(255)          NULL,
    payment_method INT                   NULL,
    deleted        BIT(1)                NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);
CREATE TABLE transaction_history
(
    transaction_id BIGINT NOT NULL,
    history_id     BIGINT NOT NULL,
    CONSTRAINT pk_transaction_history PRIMARY KEY (transaction_id, history_id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    code_name  VARCHAR(255)          NULL,
    password   VARCHAR(255)          NULL,
    deleted    BIT(1)                NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);


CREATE TABLE role
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

ALTER TABLE cash_balance_payement_history
    ADD CONSTRAINT uc_cash_balance_payement_history_payementhistory UNIQUE (payement_history_id);

ALTER TABLE cash_balance_payement_history
    ADD CONSTRAINT fk_casbalpayhis_on_cash_balance FOREIGN KEY (cash_balance_id) REFERENCES cash_balance (id);

ALTER TABLE cash_balance_payement_history
    ADD CONSTRAINT fk_casbalpayhis_on_transaction FOREIGN KEY (payement_history_id) REFERENCES transaction (id);

ALTER TABLE receipt_product_list
    ADD CONSTRAINT uc_receipt_product_list_productlist UNIQUE (product_list_id);

ALTER TABLE receipt
    ADD CONSTRAINT FK_RECEIPT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE receipt_product_list
    ADD CONSTRAINT fk_recprolis_on_receipt FOREIGN KEY (receipt_id) REFERENCES receipt (id);

ALTER TABLE receipt_product_list
    ADD CONSTRAINT fk_recprolis_on_receipt_per_product FOREIGN KEY (product_list_id) REFERENCES receipt_per_product (id);

ALTER TABLE product
    ADD CONSTRAINT uc_product_productcode UNIQUE (product_code);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATHEGORY FOREIGN KEY (cathegory_id) REFERENCES cathegory (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_DISCOUNT FOREIGN KEY (discount_id) REFERENCES discount (id);

ALTER TABLE receipt_per_product
    ADD CONSTRAINT FK_RECEIPT_PER_PRODUCT_ON_PRODUCTNAME FOREIGN KEY (product_name_id) REFERENCES product (id);

ALTER TABLE stock
    ADD CONSTRAINT FK_STOCK_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE transaction_history
    ADD CONSTRAINT uc_transaction_history_history UNIQUE (history_id);

ALTER TABLE transaction_history
    ADD CONSTRAINT fk_trahis_on_receipt FOREIGN KEY (history_id) REFERENCES receipt (id);

ALTER TABLE transaction_history
    ADD CONSTRAINT fk_trahis_on_transaction FOREIGN KEY (transaction_id) REFERENCES transaction (id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_codename UNIQUE (code_name);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE receipt
add    costumer_id    BIGINT                NULL

ALTER TABLE receipt
    ADD CONSTRAINT FK_RECEIPT_ON_COSTUMER FOREIGN KEY (costumer_id) REFERENCES costumer (id);
