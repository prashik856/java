SHOW TABLES;

CREATE TABLE Persons (
    Id int(10) unsigned AUTO_INCREMENT,
    PhoneNumber bigint not null,
    EmailId text not null,
    Name text not null,
    GroupSet text,
    CreationTime timestamp not null,
    primary key (Id)
);
SHOW TABLES;
DESCRIBE Persons;

CREATE TABLE SplitGroups (
    Id int(10) unsigned not null AUTO_INCREMENT,
    Name text not null,
    CreatedByEmail text not null,
    PeopleInvolved text not null,
    CreationTime timestamp not null,
    primary key (Id)
);
SHOW TABLES;
DESCRIBE SplitGroups;

CREATE TABLE Transactions (
    Id int(10) unsigned not null AUTO_INCREMENT,
    PersonEmail text not null,
    GroupName text not null,
    Shares mediumtext not null,
    Amount int unsigned not null,
    CreationTime timestamp not null,
    primary key (Id)
);
SHOW TABLES;
DESCRIBE Transactions;