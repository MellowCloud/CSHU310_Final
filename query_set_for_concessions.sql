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