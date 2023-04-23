DROP TABLE IF EXISTS TB_PHONE_AUTH;
DROP TABLE IF EXISTS TB_POST_IMAGE;
DROP TABLE IF EXISTS TB_HIRING_POST;
DROP TABLE IF EXISTS TB_USER;
DROP TABLE IF EXISTS TB_COMPANY;


CREATE TABLE TB_USER
(
    id                BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
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
    id              BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT UNSIGNED NULL,
    enc_phone       VARCHAR(50)     NOT NULL,
    telecom_code    ENUM ('SKT','KT','LGU','SAVE_SKT','SAVE_KT','SAVE_LGU'),
    auth_value      VARCHAR(20)     NOT NULL,
    authentication  VARCHAR(200)    NULL,
    AUTHORIZED_YN   CHAR(1)         NOT NULL DEFAULT 'N',
    auth_type       ENUM ('SIGN_IN', 'PASSWORD_RESET'),
    auth_until      DATETIME        NOT NULL DEFAULT NOW(),
    guarantee_until DATETIME        NULL,
    created_at      DATETIME        NOT NULL DEFAULT NOW(),
    updated_at      DATETIME        NOT NULL DEFAULT NOW(),
    CONSTRAINT PHONE_AUTH_REFERENCE FOREIGN KEY (user_id) references TB_USER (id)
);

CREATE INDEX PHONE_INFO_INDEX ON TB_PHONE_AUTH (enc_phone, telecom_code, auth_type, created_at DESC);
CREATE INDEX AUTHENTICATION_INDEX ON TB_PHONE_AUTH (auth_type, authentication);

CREATE TABLE TB_COMPANY
(
    id             BIGINT UNSIGNED            NOT NULL AUTO_INCREMENT,
    company_name   VARCHAR(255)               NOT NULL,
    industry_type  VARCHAR(255)               NOT NULL,
    company_type   VARCHAR(255)               NOT NULL,
    employee_count INT          DEFAULT NULL,
    address        VARCHAR(255) DEFAULT NULL,
    homepage       VARCHAR(255) DEFAULT NULL,
    phone_number   VARCHAR(255) DEFAULT NULL,
    thumbnail_url  VARCHAR(255) DEFAULT NULL,
    created_at     DATETIME     DEFAULT NOW() NOT NULL,
    updated_at     DATETIME     DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX COMPANY_NAME_INDEX ON TB_COMPANY (company_name);
CREATE INDEX COMPANY_TYPE_INDEX ON TB_COMPANY (industry_type, company_type);

CREATE TABLE TB_HIRING_POST
(
    id                                BIGINT UNSIGNED            NOT NULL AUTO_INCREMENT,
    company_id                        BIGINT UNSIGNED            NOT NULL,
    post_name                         VARCHAR(255)               NOT NULL,

    qualifications_career             VARCHAR(255) DEFAULT NULL,
    qualifications_education          VARCHAR(255) DEFAULT NULL,
    qualifications_skill              VARCHAR(255) DEFAULT NULL,
    working_condition_employment_type VARCHAR(255) DEFAULT NULL,
    working_condition_region          VARCHAR(255) DEFAULT NULL,
    coding_test_exist_yn              CHAR(1)      DEFAULT 'N',
    assignment_exist_yn               CHAR(1)      DEFAULT 'N',
    only_image_yn                     CHAR(1)      DEFAULT 'N',
    admin_checked_yn                  CHAR(1)      DEFAULT 'N',
    post_url                          VARCHAR(255)               NOT NULL,
    created_at                        DATETIME     DEFAULT NOW() NOT NULL,
    updated_at                        DATETIME     DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_hiring_post_to_company FOREIGN KEY (company_id) REFERENCES tb_company (id)
);

CREATE TABLE TB_POST_IMAGE
(
    id          BIGINT UNSIGNED        NOT NULL AUTO_INCREMENT,
    post_id     BIGINT UNSIGNED        NOT NULL,
    image_url   VARCHAR(255)           NOT NULL,
    image_order INT                    NOT NULL,
    created_at  DATETIME DEFAULT NOW() NOT NULL,
    updated_at  DATETIME DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_post_image_to_post FOREIGN KEY (post_id) REFERENCES TB_HIRING_POST (id)
);
