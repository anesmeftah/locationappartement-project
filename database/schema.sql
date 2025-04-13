CREATE SCHEMA `locationappartement`;
USE `locationappartement`;

CREATE TABLE CLIENTS(
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	email VARCHAR(200) UNIQUE,
	ID INT PRIMARY KEY,
	TELEPHONE VARCHAR(12),
	MDP VARCHAR(50),
	class INT DEFAULT 0,
    COOKIE VARCHAR(250)
);

CREATE TABLE APPARTEMENT(
	ID INT PRIMARY KEY,
	ADDRESS VARCHAR(100),
	PRIX DOUBLE,
	DESCRIP VARCHAR(300),
    NumberOfRooms INT,
    SizeInSquareMeters DECIMAL(10, 2),
	STATUT VARCHAR(20)
);

CREATE TABLE RESERVATION (
    ID INT PRIMARY KEY,                          
    ID_CLIENT INT,                              
    ID_APPARTEMENT INT,                         
    DATEDEBUT DATE,                              
    DATEFIN DATE,                               
    STATUT VARCHAR(20) DEFAULT 'PENDING',                          
    PENALITE DOUBLE DEFAULT 0,                   
    CONSTRAINT fk_client FOREIGN KEY (ID_CLIENT) REFERENCES CLIENTS(ID) ON DELETE CASCADE,
    CONSTRAINT fk_appartement FOREIGN KEY (ID_APPARTEMENT) REFERENCES APPARTEMENT(ID)    
);