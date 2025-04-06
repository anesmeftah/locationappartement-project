USE locationappartement;
-- CLIENTS
INSERT INTO CLIENTS (FirstName, LastName, email, ID, TELEPHONE, MDP,class)
VALUES 
('John', 'Doe', 'john.doe@example.com', 1, '1234567890', 'password123',0),
('Jane', 'Smith', 'jane.smith@example.com', 2, '0987654321', 'password456', 0),
('Alice', 'Johnson', 'alice.johnson@example.com', 3, '1122334455', 'password789', 0),
('Bob', 'Brown', 'bob.brown@example.com', 4, '6677889900', 'password111', 0),
('anas', 'meftah', 'anas.meftah@example.com', 6, '+21697846830', 'password123',1);

-- APPARTEMENT
INSERT INTO APPARTEMENT (ID, ADDRESS, PRIX, DESCRIP, STATUT)
VALUES 
(1, '123 Main St, Cityville', 500.00, 'A cozy one-bedroom apartment in the heart of the city.', 'available'),
(2, '456 Elm St, Townsville', 750.00, 'A spacious two-bedroom apartment with a beautiful view.', 'available'),
(3, '789 Oak St, Villageton', 600.00, 'A modern one-bedroom apartment with all amenities.', 'available'),
(4, '321 Pine St, Hamletburg', 900.00, 'A luxurious three-bedroom apartment with a swimming pool.', 'available');

-- RESERVATION
INSERT INTO RESERVATION (ID, ID_CLIENT, ID_APPARTEMENT, DATEDEBUT, DATEFIN, STATUT, PENALITE)
VALUES 
(1, 1, 1, '2025-05-01', '2025-05-10', 'confirmed', 0),
(2, 2, 2, '2025-06-01', '2025-06-15', 'confirmed', 0),
(3, 3, 3, '2025-07-01', '2025-07-10', 'pending', 0),
(4, 4, 4, '2025-08-01', '2025-08-20', 'confirmed', 0);


SELECT * From appartement;
SELECT * FROM CLIENTS;
