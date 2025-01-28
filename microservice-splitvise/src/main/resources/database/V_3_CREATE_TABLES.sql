SHOW TABLES;

CREATE TABLE Persons (
    Id text,
    PhoneNumber int,
    EmailId text,
    Name text,
    GroupSet text,
    CreationTime timestamp
);
SHOW TABLES;

CREATE TABLE SplitGroups (
    Id text,
    Name text,
    CreatedByEmail text,
    PeopleInvolved text,
    CreationTime timestamp
);
SHOW TABLES;

CREATE TABLE Transactions (
    Id text,
    PersonEmail text,
    GroupName text,
    Shares mediumtext,
    Amount int,
    CreationTime timestamp
);
SHOW TABLES;