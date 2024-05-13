create table GOODS
(
    BRAND_NAME CHARACTER VARYING(1000) not null,
    TOP        INTEGER,
    OUTER      INTEGER,
    PANTS      INTEGER,
    SNEAKERS   INTEGER,
    BAG        INTEGER,
    HAT        INTEGER,
    SOCKS      INTEGER,
    ACCESSORY  INTEGER,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    VERSION    INTEGER   default 0,
    constraint GOODS_PK
        primary key (BRAND_NAME)
);

