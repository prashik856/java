INSERT INTO Persons (PhoneNumber, EmailId, Name, GroupSet, CreationTime) VALUES (4206942069,
"stupid@example.com",
"Stupid Person",
"Example Group, Stupid Group",
CURRENT_TIMESTAMP);

INSERT INTO Persons (PhoneNumber, EmailId, Name, GroupSet, CreationTime) VALUES (6942069420,
"imbecile@example.com",
"Imbecile Person",
"Example Group, Stupid Group",
CURRENT_TIMESTAMP);

INSERT INTO SplitGroups (Name, CreatedByEmail, PeopleInvolved, CreationTime)
VALUES ("Example Group","stupid@example.com","stupid@example.com,imbecile@example.com",CURRENT_TIMESTAMP);

INSERT INTO SplitGroups (Name, CreatedByEmail, PeopleInvolved, CreationTime)
VALUES ("Stupid Group", "imbecile@example.com", "stupid@example.com,imbecile@example.com", CURRENT_TIMESTAMP);