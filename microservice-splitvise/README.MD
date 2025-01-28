# Split Vise Backend Replica

## Create a new SQL Schema for this service in MYSQL
```shell
# Login as root
mysql -u root -p
# Enter root password
```

```sql
# Create User for Microservice split vise
CREATE USER 'microservice_splitvise'@'localhost' IDENTIFIED BY 'Oracle@123';

# Grant Permissions
GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'microservice_splitvise'@'localhost';

# Flush Privileges
FLUSH PRIVILEGES;

# Commit
commit;

# Exit from mysql
```

```shell
# Login using new user
mysql --database=microservice_splitvise -u microservice_splitvise  -p
```

## Connecting to MYSQL Server using JDBC

```shell
# Follow this url
# https://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager

# mysql url: jdbc:mysql://localhost:3306/microservice_splitvise?user=microservice_splitvise&password=Oracle@123
```

```sql
# Insert test data in Persons table
DESCRIBE Persons;

ALTER TABLE Persons MODIFY COLUMN PhoneNumber BIGINT; 
ALTER TABLE Persons ADD PRIMARY KEY(Id);
ALTER TABLE SplitGroups ADD PRIMARY KEY(Id);

INSERT INTO Persons VALUES ("0", 
4206942069, 
"stupid@example.com", 
"Stupid Person", 
"Example Group, Stupid Group", 
CURRENT_TIMESTAMP);

INSERT INTO Persons VALUES ("1", 
6942069420, 
"imbecile@example.com", 
"Imbecile Person", 
"Example Group, Stupid Group", 
CURRENT_TIMESTAMP);

INSERT INTO SplitGroups VALUES ("0", 
"Example Group", 
"stupid@example.com", 
"stupid@example.com,imbecile@example.com", 
CURRENT_TIMESTAMP);

INSERT INTO SplitGroups VALUES ("1", 
"Stupid Group", 
"imbecile@example.com", 
"stupid@example.com,imbecile@example.com", 
CURRENT_TIMESTAMP);

```
