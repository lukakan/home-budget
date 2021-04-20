CREATE DATABASE home_budget CHAR SET = 'utf8mb4' COLLATE ='utf8mb4_polish_ci';

USE home_budget;

CREATE TABLE home_budget.transaction(
id BIGINT NOT NULL AUTO_INCREMENT,
type ENUM('INCOME','EXPENSE') DEFAULT 'INCOME' NOT NULL,
description VARCHAR(1000) NOT NULL,
amount DECIMAL(10,2) NOT NULL,
transaction_date DATE,
PRIMARY KEY(id));

INSERT INTO transaction (type, description, amount, transaction_date) VALUES 
('INCOME', 'Salary from UX industired', 5500.50, '2021-04-01'),
('INCOME', 'Fee for extrahours', 100.50, '2021-04-01'),
('EXPENSE', 'Food shopping', 300.50, '2021-04-05'),
('EXPENSE', 'Food shopping', 400.50, '2021-04-12'),
('EXPENSE', 'cold water bill', 30.44, '2021-04-05'),
('EXPENSE', 'energy bill', 75.66, '2021-04-05');

SELECT * FROM transaction;

