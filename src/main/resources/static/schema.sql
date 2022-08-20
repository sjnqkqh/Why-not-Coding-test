CREATE TABLE temp(
    id BIGINT
);

CREATE TABLE TB_USER(
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    login_id VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    enc_password VARCHAR(100) NOT NULL,
    nickname VARCHAR(40) NOT NULL,
    enc_phone VARCHAR(50) NOT NULL,
    refresh_token VARCHAR(100) NULL,
    delete_yn VARCHAR(1) NOT NULL DEFAULT 'N',
    created_at DATETIME NOT NULL  DEFAULT SYSTIMESTAMP,
    updated_at DATETIME NOT NULL  DEFAULT SYSTIMESTAMP
);

CREATE INDEX email_idx ON TB_USER(login_id);

CREATE TABLE TB_PHONE_AUTH(
    auth_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    enc_phone VARCHAR(50) NOT NULL,
    telecom_code ENUM('SKT','KT','LGU','SAVE_SKT','SAVE_KT','SAVE_LGU'),
    auth_value VARCHAR(20) NOT NULL,
    authentication VARCHAR(200) NULL,
    auth_type ENUM('SIGN_IN', 'PASSWORD_RESET'),
    auth_until DATETIME NOT NULL DEFAULT SYSTIMESTAMP,
    created_at DATETIME NOT NULL  DEFAULT SYSTIMESTAMP,
    updated_at DATETIME NOT NULL  DEFAULT SYSTIMESTAMP
);

CREATE INDEX PHONE_INFO_IDX ON TB_PHONE_AUTH(enc_phone, telecom_code, auth_type);
CREATE INDEX AUTHENTICATION_IDX ON TB_PHONE_AUTH(auth_type, authentication);
