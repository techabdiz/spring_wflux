CREATE TABLE users ( 
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE, 
    first_name varchar(50) NOT NULL, 
    last_name varchar(50) NOT NULL, 
    email varchar(100) NOT NULL UNIQUE, 
    password varchar(255) NOT NULL
);