Create table Item (ID int auto_increment,
ItemCode varchar(10) UNIQUE,
ItemDescription varchar(50),
Price decimal(4,2) DEFAULT 00.00,
primary key (ID));

Create table Purchase (ID int auto_increment,
ItemID int,
Quantity int NOT NULL,
PurchaseDate datetime,
FOREIGN KEY (ItemID)
	REFERENCES Item(ID),
primary key (ID));

Create table Shipment(ID int auto_increment,
ItemID int,
Quantity int NOT NULL,
ShipmentDate date NOT NULL UNIQUE,
FOREIGN KEY (ItemID)
	REFERENCES Item(ID),
primary key (ID));

select * from Item;
select * from Purchase;

INSERT INTO Item(itemCode, itemDescription, price)
VALUES('DELETETEST', 'This is an example', 1.00);

use concessions;

INSERT INTO Purchase(Quantity, ItemID)
VALUES('3',
(SELECT ID from Item
WHERE itemCode = 'Example'));

DELETE FROM Item WHERE itemCode = 'DELETETEST';
