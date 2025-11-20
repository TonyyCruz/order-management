-- password_user: 123456
-- password_admin: loginAdmin

INSERT INTO USERS (id, username, password, role, birth_date)
VALUES
  ('c0c4a69a-9dda-4b50-ab59-d896ce0a5c6e', 'admin', '$2a$10$vC1hgddH4UJBxQYv0AUqLOqd5HGPfeD5Pbp3nhTwy9tnOavEudiBK', 'ADMIN', '1988-01-10'),
  ('7b87f809-d142-4dfa-8802-87644d774dd5', 'user',  '$2a$10$GaeCNDGHa.u6vNAPS6xUee/3PoWsN.nVxaDHmNK5LMheS7ZDqa6TG', 'CUSTOMER', '1995-05-15');
