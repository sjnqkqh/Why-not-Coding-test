DROP TABLE IF EXISTS TB_USER;
DROP TABLE IF EXISTS TB_PHONE_AUTH;
DROP TABLE IF EXISTS TB_USER_LIBRARY_MAPPING;
DROP TABLE IF EXISTS TB_BOOK_RESERVATION;
DROP TABLE IF EXISTS TB_LIBRARY_BOOK_CNT;
DROP TABLE IF EXISTS TB_LIBRARIAN;
DROP TABLE IF EXISTS TB_USER;
DROP TABLE IF EXISTS TB_LIBRARY;
DROP TABLE IF EXISTS TB_BOOK;
DROP TABLE IF EXISTS TB_ADMIN;

CREATE TABLE TB_USER
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    login_id          VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL,
    enc_password      VARCHAR(100) NOT NULL,
    nickname          VARCHAR(40)  NOT NULL,
    enc_phone         VARCHAR(50)  NOT NULL,
    access_token      VARCHAR(300) NULL,
    access_expired_at DATETIME     NULL,
    delete_yn         VARCHAR(1)   NOT NULL DEFAULT 'N',
    created_at        DATETIME     NOT NULL DEFAULT NOW(),
    updated_at        DATETIME     NOT NULL DEFAULT NOW()
);

CREATE INDEX LOGIN_ID_INDEX ON TB_USER (login_id, delete_yn);
CREATE INDEX ACCESS_TOKEN_INDEX ON TB_USER (access_token, delete_yn);

CREATE TABLE TB_PHONE_AUTH
(
    id              BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT       NULL,
    enc_phone       VARCHAR(50)  NOT NULL,
    telecom_code    ENUM ('SKT','KT','LGU','SAVE_SKT','SAVE_KT','SAVE_LGU'),
    auth_value      VARCHAR(20)  NOT NULL,
    authentication  VARCHAR(200) NULL,
    AUTHORIZED_YN   CHAR(1)      NOT NULL DEFAULT 'N',
    auth_type       ENUM ('SIGN_IN', 'PASSWORD_RESET'),
    auth_until      DATETIME     NOT NULL DEFAULT NOW(),
    guarantee_until DATETIME     NULL,
    created_at      DATETIME     NOT NULL DEFAULT NOW(),
    updated_at      DATETIME     NOT NULL DEFAULT NOW(),
    CONSTRAINT PHONE_AUTH_REFERENCE FOREIGN KEY (user_id) references TB_USER (id)
);

CREATE INDEX PHONE_INFO_INDEX ON TB_PHONE_AUTH (enc_phone, telecom_code, auth_type, created_at DESC);
CREATE INDEX AUTHENTICATION_INDEX ON TB_PHONE_AUTH (auth_type, authentication);


# 도서관 메타 테이블
CREATE TABLE TB_LIBRARY
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    library_name VARCHAR(255)               NOT NULL,
    address      VARCHAR(255)               NOT NULL,
    phone_number VARCHAR(255) DEFAULT NULL,
    use_yn       CHAR(1)      DEFAULT 'Y',
    created_at   DATETIME     DEFAULT NOW() NOT NULL,
    updated_at   DATETIME     DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX LIBRARY_NAME_INDEX ON TB_LIBRARY (library_name, use_yn);
CREATE INDEX LIBRARY_CREATE_DATE_INDEX ON TB_LIBRARY (use_yn,created_at);


# 회원별 도서관 매핑 테이블
CREATE TABLE TB_USER_LIBRARY_MAPPING
(
    id         BIGINT                 NOT NULL AUTO_INCREMENT,
    user_id    BIGINT UNSIGNED        NOT NULL,
    library_id BIGINT UNSIGNED        NOT NULL,
    created_at DATETIME DEFAULT NOW() NOT NULL,
    updated_at DATETIME DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_library_mapping FOREIGN KEY (library_id) REFERENCES tb_library (id),
    CONSTRAINT fk_user_mapping FOREIGN KEY (user_id) REFERENCES tb_user (id)
);

# 사서 계정 메타 테이블
CREATE TABLE TB_LIBRARIAN
(
    id                       BIGINT UNSIGNED            NOT NULL AUTO_INCREMENT,
    login_id                 VARCHAR(255)               NOT NULL,
    enc_password             VARCHAR(255)               NOT NULL,
    user_name                VARCHAR(255)               NOT NULL,
    email                    VARCHAR(255)               NOT NULL,
    library_id               BIGINT UNSIGNED            NOT NULL,
    access_token             VARCHAR(255) DEFAULT NULL,
    refresh_token            VARCHAR(255) DEFAULT NULL,
    refresh_token_expired_at DATETIME     DEFAULT NULL,
    delete_yn                CHAR(1)                    NOT NULL DEFAULT 'N',
    created_at               DATETIME     DEFAULT NOW() NOT NULL,
    updated_at               DATETIME     DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_user_to_library (library_id) REFERENCES TB_LIBRARY (ID)
);
CREATE INDEX LOGIN_ID_INDEX ON TB_LIBRARIAN (login_id, delete_yn);
CREATE INDEX ACCESS_TOKEN_INDEX ON TB_LIBRARIAN (access_token);

# 도서 메타 정보 테이블
CREATE TABLE TB_BOOK
(
    id               BIGINT UNSIGNED        NOT NULL AUTO_INCREMENT,
    title            VARCHAR(255)           NOT NULL,
    author           VARCHAR(255)           NOT NULL,
    publisher_name   VARCHAR(255)           NOT NULL,
    isbn             VARCHAR(255)           NOT NULL,
    book_information TEXT                   NOT NULL,
    book_cover_image VARCHAR(255)           NOT NULL,
    delete_yn        CHAR(1)                NOT NULL DEFAULT 'N',
    created_at       DATETIME DEFAULT NOW() NOT NULL,
    updated_at       DATETIME DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX ISBN_SEARCH_INDEX ON TB_BOOK (isbn, delete_yn);
CREATE INDEX AUTHOR_SEARCH_INDEX ON TB_BOOK (author, delete_yn);
CREATE INDEX PUBLISHER_SEARCH_INDEX ON TB_BOOK (publisher_name, title, delete_yn);
CREATE INDEX SORT_INDEX ON TB_BOOK(updated_at DESC);

# 도서 소장 권수 테이블
CREATE TABLE TB_LIBRARY_BOOK_CNT
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    library_id BIGINT UNSIGNED NOT NULL,
    book_id    BIGINT UNSIGNED NOT NULL,
    book_cnt   INT             NOT NULL DEFAULT 0,
    use_yn     CHAR(1)         NOT NULL DEFAULT 'Y',
    created_at DATETIME                 DEFAULT NOW() NOT NULL,
    updated_at DATETIME                 DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_library_mapping (library_id) REFERENCES TB_LIBRARY (id),
    FOREIGN KEY fk_book_mapping (book_id) REFERENCES TB_BOOK (id)
);

CREATE INDEX BOOK_COUNT_INDEX ON TB_LIBRARY_BOOK_CNT (library_id, book_id, use_yn);

# 도서 대출 예약 테이블
CREATE TABLE TB_BOOK_RESERVATION
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    library_id     BIGINT UNSIGNED NOT NULL,
    book_id        BIGINT UNSIGNED NOT NULL,
    user_id        BIGINT UNSIGNED NOT NULL,
    expired_at     DATETIME        NOT NULL,
    expired_yn     CHAR(1)         NOT NULL DEFAULT 'N',
    expired_reason VARCHAR(255)             DEFAULT NULL,
    delete_yn      CHAR(1)         NOT NULL DEFAULT 'N',
    created_at     DATETIME                 DEFAULT NOW() NOT NULL,
    updated_at     DATETIME                 DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_library_mapping (library_id) REFERENCES TB_LIBRARY (id),
    FOREIGN KEY fk_book_mapping (book_id) REFERENCES TB_BOOK (id),
    FOREIGN KEY fk_book_mapping (user_id) REFERENCES TB_USER (id)
);

CREATE INDEX RESERVE_INDEX ON TB_BOOK_RESERVATION (library_id, book_id, delete_yn, updated_at DESC);
CREATE INDEX USER_RESERVE_INDEX ON TB_BOOK_RESERVATION (user_id, delete_yn, updated_at DESC);