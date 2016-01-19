CREATE DATABASE  IF NOT EXISTS `ymca` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ymca`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: ymca
-- ------------------------------------------------------
-- Server version	5.6.19

INSERT INTO `program` VALUES (1,'Smith',400,'Child Care','2014-07-25 00:00:00','USA'),(2,'Johnson',800,'Swim Class','2014-07-26 00:00:00','USA'),(3,'Williams',350,'Abs Class','2014-05-04 00:00:00','USA'),(4,'Jones',1200,'Theme Park','2013-02-13 00:00:00','USA'),(5,'Brown',300,'Riding Class','2014-06-14 00:00:00','USA');
INSERT INTO `donation` VALUES (1,30607600155,'Thomas',37337535,'Mitchell',1500,'2014-02-04 00:00:00',8740,'Card','18740','social'),(2,30607600138,'Harris',37337536,'Perez',2000,'2014-07-16 00:00:00',9740,'Online','48732','sports'),(3,30607600177,'Martin',37337537,'Parker',1000,'2014-12-22 00:00:00',8840,'Cash','25740','education'),(4,30607600180,'Lee',37337538,'Edwards',1800,'2014-05-14 00:00:00',8750,'ATM','68763','social'),(5,30607600145,'Young',37337539,'Wright',1200,'2014-10-04 00:00:00',8741,'Check','88754','charity');

