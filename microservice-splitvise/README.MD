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
