# create database
DROP DATABASE IF EXISTS jdbc_assignment1;
CREATE DATABASE jdbc_assignment1;

SET GLOBAL log_bin_trust_function_creators = 1;

# select database
USE jdbc_assignment1;

# create table
create table Nodes
(
	Node_ID int not null,
    City varchar(20) not null,
    Longitude varchar(10) not null,
    Latitude varchar(10) not null,
    Transmission_Rate varchar(10) not null,
    Operating_Frequency varchar(10) not null,
    Peak_Performance varchar(10) not null,
		primary key (Node_ID)
);

create table Performance
(
	Node_Num int not null,
    Time_1 int not null,
    Time_2 int not null,
    Time_3 int not null,
    Time_4 int not null,
    Time_5 int not null,
    Time_6 int not null,
    Time_7 int not null,
    Time_8 int not null,
		foreign key (Node_Num) References Nodes(Node_Id)
);
    
# insert rows into tables
insert into Nodes values
(1, 'Dublin', '53.3498', '6.2603', '5 Gb/s', '79 GHz', '96%'),
(2, 'Athlone', '53.4239', '7.9407', '1 Gb/s', '71 GHz', '88%'),
(3, 'Galway', '53.2707', '9.0568', '2 Gb/s', '72 GHz', '86%'),
(4, 'Cork', '51.8985', '8.4756', '3 Gb/s', '76 GHz', '94%'),
(5, 'Limerick', '52.6638', '8.6267', '4 Gb/s', '75 GHz', '87%'),
(6, 'Belfast', '54.5973', '5.9301', '3 Gb/s', '85 GHz', '85%'),
(7, 'Derry', '54.9966', '7.3086', '6 Gb/s', '80 GHz', '90%'),
(8, 'Wicklow', '52.9862', '6.3673', '7 Gb/s', '82 Ghz', '89%'),
(9, 'Wexford', '52.3369', '6.4633', '9 Gb/s', '86 Ghz', '91%'),
(10, 'Donegal', '54.6549', '8.1041', '8 Gb/s', '84 GHz', '92%');

insert into Performance values
(1, 5, 11, 19, 33, 40, 49, 56, 70),
(2, 7, 13, 21, 35, 44, 50, 61, 75),
(3, 6, 15, 22, 34, 42, 54, 67, 79),
(4, 3, 16, 25, 30, 50, 59, 66, 72),
(5, 8, 14, 24, 37, 48, 61, 78, 84),
(6, 7, 10, 20, 39, 50, 65, 73, 81),
(7, 6, 13, 22, 35, 47, 56, 68, 71),
(8, 7, 17, 25, 31, 45, 51, 59, 67),
(9, 8, 15, 26, 29, 42, 52, 64, 73),
(10, 9, 16, 27, 40, 54, 61, 77, 89);

# create audit tables
create table Audit1
(
	Node_ID int not null,
    Action_Type varchar(20),
    Action_Date datetime not null
);

create table Audit2
(
	Node_Num int not null,
    Action_Type varchar(20),
    Action_Date datetime not null
);

#create trigger for inserts, delets and updates for both tables
DROP TRIGGER IF EXISTS audit1_after_insert;
DELIMITER //
CREATE TRIGGER audit1_after_insert
	AFTER INSERT ON Nodes
    FOR EACH ROW 
BEGIN 
	INSERT INTO Audit1 VALUES
    (NEW.Node_ID, "INSERTED", NOW());
END //
DELIMITER ;

DROP TRIGGER IF EXISTS audit1_after_delete;
DELIMITER //
CREATE TRIGGER audit1_after_delete
	AFTER DELETE ON Nodes
    FOR EACH ROW 
BEGIN 
    INSERT INTO Audit1 VALUES 
    (OLD.Node_ID, "DELETED", NOW());
END //
DELIMITER ;

DROP TRIGGER IF EXISTS audit1_after_update;
DELIMITER //
CREATE TRIGGER audit1_after_update
	AFTER UPDATE ON Nodes
    FOR EACH ROW 
BEGIN
	INSERT INTO Audit1 values
    (OLD.Node_ID, "UPDATED (OLD)", NOW());
	INSERT INTO Audit1 VALUES
    (NEW.Node_ID, "UPDATED (NEW)", NOW());
END //
DELIMITER ;
    
DROP TRIGGER IF EXISTS audit2_after_insert;
DELIMITER //
CREATE TRIGGER audit2_after_insert
	AFTER INSERT ON Performance 
    FOR EACH ROW 
BEGIN 
	INSERT INTO Audit2 VALUES
    (NEW.Node_Num, "INSERTED", NOW());
END //
DELIMITER ;

DROP TRIGGER IF EXISTS audit2_after_delete;
DELIMITER //
CREATE TRIGGER audit2_after_delete
	AFTER DELETE ON Performance
    FOR EACH ROW 
BEGIN 
    INSERT INTO Audit2 VALUES 
    (OLD.Node_Num, "DELETED", NOW());
END //
DELIMITER ;

DROP TRIGGER IF EXISTS audit2_after_update;
DELIMITER //
CREATE TRIGGER audit2_after_update
	AFTER UPDATE ON Performance
    FOR EACH ROW 
BEGIN
	INSERT INTO Audit2 values
    (OLD.Node_Num, "UPDATED (OLD)", NOW());
	INSERT INTO Audit2 VALUES
    (NEW.Node_Num, "UPDATED (NEW)", NOW());
END //
DELIMITER ;

create table Average
(
	Average_Node_Loss varchar(10),
    avgerage_loss_1 double not null,
    avgerage_loss_2 double not null,
    avgerage_loss_3 double not null,
    avgerage_loss_4 double not null,
    avgerage_loss_5 double not null,
    avgerage_loss_6 double not null,
    avgerage_loss_7 double not null,
    avgerage_loss_8 double not null
);

#create functions for each time period column, calculating the average of each column (not applied to the java program)
DROP FUNCTION IF EXISTS avg_loss_1;
DELIMITER //
CREATE FUNCTION avg_loss_1
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_1 DECIMAL(9,2);
		SELECT avg(Time_1)
        INTO average_loss_1
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_1;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_2;
DELIMITER //
CREATE FUNCTION avg_loss_2
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_2 DECIMAL(9,2);
		SELECT avg(Time_2)
        INTO average_loss_2
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_2;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_3;
DELIMITER //
CREATE FUNCTION avg_loss_3
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_3 DECIMAL(9,2);
		SELECT avg(Time_3)
        INTO average_loss_3
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_3;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_4;
DELIMITER //
CREATE FUNCTION avg_loss_4
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_4 DECIMAL(9,2);
		SELECT avg(Time_4)
        INTO average_loss_4
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_4;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_5;
DELIMITER //
CREATE FUNCTION avg_loss_5
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_5 DECIMAL(9,2);
		SELECT avg(Time_5)
        INTO average_loss_5
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_5;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_6;
DELIMITER //
CREATE FUNCTION avg_loss_6
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_6 DECIMAL(9,2);
		SELECT avg(Time_6)
        INTO average_loss_6
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_6;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_7;
DELIMITER //
CREATE FUNCTION avg_loss_7
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_7 DECIMAL(9,2);
		SELECT avg(Time_7)
        INTO average_loss_7
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_7;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS avg_loss_8;
DELIMITER //
CREATE FUNCTION avg_loss_8
(
	Node_Num_param int
)
RETURNS DECIMAL(9,2)
BEGIN 
	DECLARE average_loss_8 DECIMAL(9,2);
		SELECT avg(Time_8)
        INTO average_loss_8
        FROM Performance
        WHERE Node_Num = Node_Num_param;
	RETURN average_loss_8;
END //
DELIMITER ;