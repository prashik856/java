CREATE USER 'microservice_splitvise'@'localhost' IDENTIFIED BY 'Oracle@123';
GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'microservice_splitvise'@'localhost';
FLUSH PRIVILEGES;
commit;
