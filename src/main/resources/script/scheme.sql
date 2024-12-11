CREATE DATABASE mini_project_spring_db ;

-- Must run this query first
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users_tb (
    user_id UUID PRIMARY KEY default uuid_generate_v4(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255)
);

CREATE TABLE categories_tb (
    category_id UUID PRIMARY KEY default uuid_generate_v4(),
    name VARCHAR (100),
    description VARCHAR (250),
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users_tb (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE expenses_tb (
    expense_id UUID PRIMARY KEY default uuid_generate_v4(),
    amount INT,
    description VARCHAR (250),
    date TIMESTAMP,
    user_id UUID,
    category_id UUID,
    FOREIGN KEY (user_id) REFERENCES users_tb (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories_tb (category_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE otp_tb(
    otp_id UUID PRIMARY KEY default uuid_generate_v4(),
    otp_code VARCHAR(6) NOT NULL,
    issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiration_time TIMESTAMP NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    user_id UUID UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users_tb (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);





