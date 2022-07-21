create table cash_balance
(
    id          bigint auto_increment not null,
    deposit     double                null,
    total_money double                null,
    deleted      bit              null,
    constraint pk_cash_balance primary key (id)
);
CREATE TABLE cathegory
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    deleted BIT(1)                NULL,
    CONSTRAINT pk_cathegory PRIMARY KEY (id)
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
    discount_id  BIGINT                NULL,
    product_code VARCHAR(255)          NULL,
    type         VARCHAR(255)          NOT NULL,
    price        DOUBLE                NULL,
    cathegory_id BIGINT                NULL,
    deleted     BIT(1)                NULL,
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
CREATE TABLE `userRole`
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    user_id  BIGINT                NULL,
    deleted BIT(1)                NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);
CREATE TABLE stock
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    product_id           BIGINT                NULL,
    quantity_per_product INT                   NULL,
    deleted             BIT(1)                NULL,
    zipcode              INT                   NULL,
    street_number        INT                   NULL,
    street               VARCHAR(255)          NULL,
    city                 VARCHAR(255)          NULL,
    country              VARCHAR(255)          NULL,
    CONSTRAINT pk_stock PRIMARY KEY (id)
);
CREATE TABLE transaction
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    total         DOUBLE                NULL,
    description VARCHAR(255)          NULL,
    deleted      BIT             NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);
CREATE TABLE user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255)          NULL,
    last_name     VARCHAR(255)          NULL,
    deleted      BIT               NULL,
    token_expired BIT                NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATHEGORY FOREIGN KEY (cathegory_id) REFERENCES cathegory (id);
ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_DISCOUNT FOREIGN KEY (discount_id) REFERENCES discount (id);
ALTER TABLE receipt
    ADD CONSTRAINT FK_RECEIPT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE receipt_per_product
    ADD CONSTRAINT FK_RECEIPT_PER_PRODUCT_ON_PRODUCTNAME FOREIGN KEY (product_name_id) REFERENCES product (id);
ALTER TABLE `userRole`
    ADD CONSTRAINT FK_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE stock
    ADD CONSTRAINT FK_STOCK_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);