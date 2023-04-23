
DROP TABLE IF EXISTS TB_PHONE_AUTH;
DROP TABLE IF EXISTS TB_USER;

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

