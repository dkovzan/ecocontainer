DROP TABLE IF EXISTS UserRole;
DROP TABLE IF EXISTS UserInfo;
DROP TABLE IF EXISTS UserInRole;

CREATE TABLE UserRole
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(500),
    category VARCHAR(200)
);

CREATE TABLE UserInfo
(
    id SERIAL PRIMARY KEY,
    login VARCHAR(200) NOT NULL,
    firstName VARCHAR(200),
    lastName VARCHAR(200),
    password VARCHAR(500) NOT NULL,
    email VARCHAR(200),
    phone VARCHAR(200),
    address VARCHAR(500),
    status VARCHAR(200),
    createdOn TIMESTAMP,
    updatedOn TIMESTAMP,
    passwordChangedOn TIMESTAMP,
    isEnabled BOOLEAN NOT NULL
);

CREATE TABLE UserInRole
(
    id SERIAL PRIMARY KEY,
    userId INTEGER,
    roleId INTEGER,
    status VARCHAR(200)
);