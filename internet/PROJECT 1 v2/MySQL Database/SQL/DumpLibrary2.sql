CREATE DATABASE  IF NOT EXISTS `library2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `library2`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: library2
-- ------------------------------------------------------
-- Server version	5.5.47-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dissertation`
--

DROP TABLE IF EXISTS `dissertation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dissertation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dissertationId` varchar(45) NOT NULL,
  `title` varchar(80) NOT NULL,
  `supervisor` varchar(50) NOT NULL,
  `studentId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `dissertationId_UNIQUE` (`dissertationId`),
  KEY `fk_diss_student_id_idx` (`studentId`),
  CONSTRAINT `fk_diss_student_id` FOREIGN KEY (`studentId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dissertation`
--

LOCK TABLES `dissertation` WRITE;
/*!40000 ALTER TABLE `dissertation` DISABLE KEYS */;
/*!40000 ALTER TABLE `dissertation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventemail`
--

DROP TABLE IF EXISTS `eventemail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventemail` (
  `eventemailrecipient_id` int(11) NOT NULL,
  `libraryevent_id` int(11) NOT NULL,
  `senttime` datetime DEFAULT NULL,
  PRIMARY KEY (`eventemailrecipient_id`,`libraryevent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventemail`
--

LOCK TABLES `eventemail` WRITE;
/*!40000 ALTER TABLE `eventemail` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventemail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventemailrecipient`
--

DROP TABLE IF EXISTS `eventemailrecipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventemailrecipient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `registrationdate` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventemailrecipient`
--

LOCK TABLES `eventemailrecipient` WRITE;
/*!40000 ALTER TABLE `eventemailrecipient` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventemailrecipient` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`tioannid`@`%`*/ /*!50003 TRIGGER trg_eventemailrecipient_afterins
AFTER INSERT ON eventemailrecipient FOR EACH ROW
BEGIN	
    INSERT INTO eventemail(eventemailrecipient_id, libraryevent_id, senttime)
    SELECT NEW.id, id, NOW() FROM vlibraryevent;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `librarian_status`
--

DROP TABLE IF EXISTS `librarian_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `librarian_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `status_UNIQUE` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarian_status`
--

LOCK TABLES `librarian_status` WRITE;
/*!40000 ALTER TABLE `librarian_status` DISABLE KEYS */;
INSERT INTO `librarian_status` VALUES (2,'Accepted'),(4,'Non Standard'),(3,'Out of page range'),(5,'Student Submitted');
/*!40000 ALTER TABLE `librarian_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `library` (
  `name` varchar(100) NOT NULL,
  `address` varchar(150) NOT NULL,
  `telephone` varchar(45) NOT NULL,
  `email` varchar(50) NOT NULL,
  `openhours` varchar(100) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
INSERT INTO `library` VALUES ('HUA LIBRARY','Ermou 21, 52381 Kallithea','210555888','library@hua.gr','Mon-Fri 08:00 - 21:30, Sat 09:00 - 18:30 ');
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libraryevent`
--

DROP TABLE IF EXISTS `libraryevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libraryevent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT '''C'' or ''E'' or ''S''',
  `title` varchar(100) NOT NULL,
  `startdate` date NOT NULL,
  `enddate` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libraryevent`
--

LOCK TABLES `libraryevent` WRITE;
/*!40000 ALTER TABLE `libraryevent` DISABLE KEYS */;
INSERT INTO `libraryevent` VALUES (2,'Collection','Portrait of Gosu','2012-05-04','2015-05-03'),(3,'Event','Bookfest','2016-06-24','2016-07-04'),(4,'Seminar','ITIL v3','2016-11-21','2016-12-21'),(5,'Collection','Renessance Revisited','2015-11-13','2016-11-10'),(6,'Seminar','C# 2013 Professional','2016-02-01','2016-02-16');
/*!40000 ALTER TABLE `libraryevent` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`tioannid`@`%`*/ /*!50003 TRIGGER trg_libraryevent_afterins
AFTER INSERT ON libraryevent FOR EACH ROW
BEGIN	
    INSERT INTO eventemail(eventemailrecipient_id, libraryevent_id, senttime)
    SELECT id, NEW.id, NOW() FROM eventemailrecipient;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `manager_status`
--

DROP TABLE IF EXISTS `manager_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `status_UNIQUE` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_status`
--

LOCK TABLES `manager_status` WRITE;
/*!40000 ALTER TABLE `manager_status` DISABLE KEYS */;
INSERT INTO `manager_status` VALUES (1,'Accepted'),(3,'Not Checked'),(2,'Rejected');
/*!40000 ALTER TABLE `manager_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `webview` varchar(45) DEFAULT NULL,
  `MaxUsers` int(11) NOT NULL,
  `MinUsers` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (6,'Administrator','admin',1,1),(7,'Student','student',100,0),(9,'Manager','manager',1,0),(10,'Director','director',1,0),(11,'Librarian','librarian',4,0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_has_service`
--

DROP TABLE IF EXISTS `role_has_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_has_service` (
  `ROLE_id` int(11) NOT NULL,
  `SERVICE_id` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_id`,`SERVICE_id`),
  KEY `fk_ROLE_has_SERVICE_SERVICE1_idx` (`SERVICE_id`),
  KEY `fk_ROLE_has_SERVICE_ROLE1_idx` (`ROLE_id`),
  CONSTRAINT `fk_ROLE_has_SERVICE_ROLE1` FOREIGN KEY (`ROLE_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ROLE_has_SERVICE_SERVICE1` FOREIGN KEY (`SERVICE_id`) REFERENCES `service` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_has_service`
--

LOCK TABLES `role_has_service` WRITE;
/*!40000 ALTER TABLE `role_has_service` DISABLE KEYS */;
INSERT INTO `role_has_service` VALUES (11,1),(6,2),(6,4),(6,5),(6,6),(6,7),(6,8),(6,9),(9,10),(10,10),(11,10),(9,11),(10,12);
/*!40000 ALTER TABLE `role_has_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'ADD Dissertation'),(6,'ADD Role'),(8,'ADD Service'),(2,'ADD User'),(12,'APPROVE BIG Dissertation'),(11,'APPROVE Dissertation'),(7,'DELETE Role'),(9,'DELETE Service'),(4,'DELETE User'),(10,'MODIFY Dissertation'),(5,'UPDATE User');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submission`
--

DROP TABLE IF EXISTS `submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `submissionNo` int(2) NOT NULL,
  `dissertationId` int(11) NOT NULL,
  `subject_areas` varchar(500) DEFAULT NULL,
  `pdf` longblob,
  `pdf_pages` int(11) DEFAULT NULL,
  `librarianstatusId` int(11) NOT NULL,
  `director_status` bit(1) DEFAULT NULL,
  `director_notes` varchar(250) DEFAULT NULL,
  `managerstatusId` int(11) NOT NULL DEFAULT '3',
  `manager_notes` varchar(200) DEFAULT NULL,
  `studentinformmethod` varchar(1) DEFAULT 'X',
  `studentinformdate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_submission_diss_id_idx` (`dissertationId`),
  KEY `fk_submission_librarianstatus_id_idx` (`librarianstatusId`),
  KEY `fk_managerstatus_id_idx` (`managerstatusId`),
  CONSTRAINT `fk_managerstatus_id` FOREIGN KEY (`managerstatusId`) REFERENCES `manager_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_submission_diss_id` FOREIGN KEY (`dissertationId`) REFERENCES `dissertation` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_submission_librarianstatus_id` FOREIGN KEY (`librarianstatusId`) REFERENCES `librarian_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission`
--

LOCK TABLES `submission` WRITE;
/*!40000 ALTER TABLE `submission` DISABLE KEYS */;
/*!40000 ALTER TABLE `submission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `userId` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `adt` varchar(15) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `am` varchar(10) DEFAULT NULL,
  `roleId` int(11) NOT NULL,
  `onlinesubmit` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  KEY `fk_USER_ROLE1_idx` (`roleId`),
  CONSTRAINT `fk_USER_ROLE1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (21,'Administrator','admin','admin','AA11111','admin@library.gr','210111111','',6,'\0'),(22,'Director','director','director','BB222222','director@library.gr','210222222','',10,'\0'),(28,'Librariarian 1','librarian1','librarian1','LL111111','librarian1@library.gr','210444444','',11,'\0'),(32,'Manager','manager','manager','CC444444','manager@library.gr','2310746843','',9,'\0'),(34,'Librarian 2','librarian2','librarian2','FF333333','librarian2@library.gr','210333666','',11,'\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_user_beforeins
BEFORE INSERT ON user FOR EACH ROW
BEGIN
	DECLARE m_result INT;
    DECLARE m_role_name VARCHAR(45);
    DECLARE m_min_users INT;
    DECLARE m_max_users INT;
    DECLARE m_message VARCHAR(100);

	SET @m_result := (SELECT count(*) FROM vcaninsertusertorole WHERE id = NEW.roleId);
    -- If result=0 it means that NO INSERT should be allowed for this role id
    IF @m_result = 0 THEN
		BEGIN
			-- retrieve role name to build an informative erro message
			SELECT name, maxusers, minusers INTO @m_role_name, @m_max_users, @m_min_users FROM vroleminmaxusercount WHERE id = NEW.roleId;
            SET @m_message := concat('TABLE(user).INSERT attempted to violate the allowed range [', @m_min_users);
            SET @m_message := concat(@m_message, ',');
            SET @m_message := concat(@m_message, @m_max_users);
            SET @m_message := concat(@m_message, '] of number of users for role ');
            SET @m_message := concat(@m_message, ucase(@m_role_name));
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @m_message;
        END;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_user_beforeupd 
BEFORE UPDATE ON user FOR EACH ROW
BEGIN
	DECLARE m_result_del INT;
    DECLARE m_result_ins INT;
    DECLARE m_role_name VARCHAR(45);
    DECLARE m_min_users INT;
    DECLARE m_max_users INT;
    DECLARE m_message VARCHAR(100);

	-- 1. check if there is a modification of the role ID field
    IF (OLD.roleId <> NEW.roleId) THEN
		BEGIN
			-- 2. check if deletion is allowed for user with role equal to OLD.roleId
			SET @m_result_del := (SELECT count(*) FROM vcandeleteuserfromrole WHERE id = OLD.roleId);
			-- If result=0 it means that NO DELETE should be allowed for this role id
			IF @m_result_del = 0 THEN
				BEGIN
					-- retrieve role name to build an informative error message
					SELECT name, maxusers, minusers INTO @m_role_name, @m_max_users, @m_min_users FROM vroleminmaxusercount WHERE id = OLD.roleId;
					SET @m_message := concat('TABLE(user).UPDATE_DeleteStep attempted to violate the allowed range [', @m_min_users);
					SET @m_message := concat(@m_message, ',');
					SET @m_message := concat(@m_message, @m_max_users);
					SET @m_message := concat(@m_message, '] of number of users for role ');
					SET @m_message := concat(@m_message, ucase(@m_role_name));
					SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = @m_message;
				END;
			END IF;
			-- 3. check if insertion is allowed for user with role equal to NEW.roleId
			SET @m_result_ins := (SELECT count(*) FROM vcaninsertusertorole WHERE id = NEW.roleId);
			-- If result=0 it means that NO INSERT should be allowed for this role id
			IF @m_result_ins = 0 THEN
				BEGIN
					-- retrieve role name to build an informative erro message
					SELECT name, maxusers, minusers INTO @m_role_name, @m_max_users, @m_min_users FROM vroleminmaxusercount WHERE id = NEW.roleId;
					SET @m_message := concat('TABLE(user).UPDATE_InsertStep attempted to violate the allowed range [', @m_min_users);
					SET @m_message := concat(@m_message, ',');
					SET @m_message := concat(@m_message, @m_max_users);
					SET @m_message := concat(@m_message, '] of number of users for role ');
					SET @m_message := concat(@m_message, ucase(@m_role_name));
					SIGNAL SQLSTATE '45003' SET MESSAGE_TEXT = @m_message;
				END;
			END IF;        
		END;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_user_beforedel 
BEFORE DELETE ON user FOR EACH ROW
BEGIN
	DECLARE m_result INT;
    DECLARE m_role_name VARCHAR(45);
    DECLARE m_min_users INT;
    DECLARE m_max_users INT;
    DECLARE m_message VARCHAR(100);

	SET @m_result := (SELECT count(*) FROM vcandeleteuserfromrole WHERE id = OLD.roleId);
    -- If result=0 it means that NO DELETE should be allowed for this role id
    IF @m_result = 0 THEN
		BEGIN
			-- retrieve role name to build an informative error message
			SELECT name, maxusers, minusers INTO @m_role_name, @m_max_users, @m_min_users FROM vroleminmaxusercount WHERE id = OLD.roleId;
            SET @m_message := concat('TABLE(user).DELETE attempted to violate the allowed range [', @m_min_users);
            SET @m_message := concat(@m_message, ',');
            SET @m_message := concat(@m_message, @m_max_users);
            SET @m_message := concat(@m_message, '] of number of users for role ');
            SET @m_message := concat(@m_message, ucase(@m_role_name));
			SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = @m_message;
        END;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `vcandeleteuserfromrole`
--

DROP TABLE IF EXISTS `vcandeleteuserfromrole`;
/*!50001 DROP VIEW IF EXISTS `vcandeleteuserfromrole`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vcandeleteuserfromrole` AS SELECT 
 1 AS `id`,
 1 AS `name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vcaninsertusertorole`
--

DROP TABLE IF EXISTS `vcaninsertusertorole`;
/*!50001 DROP VIEW IF EXISTS `vcaninsertusertorole`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vcaninsertusertorole` AS SELECT 
 1 AS `id`,
 1 AS `name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vdissertation`
--

DROP TABLE IF EXISTS `vdissertation`;
/*!50001 DROP VIEW IF EXISTS `vdissertation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vdissertation` AS SELECT 
 1 AS `id`,
 1 AS `dissertationId`,
 1 AS `title`,
 1 AS `supervisor`,
 1 AS `studentId`,
 1 AS `studentname`,
 1 AS `userid`,
 1 AS `studentcansubmitonline`,
 1 AS `studentemail`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vdissertation_director`
--

DROP TABLE IF EXISTS `vdissertation_director`;
/*!50001 DROP VIEW IF EXISTS `vdissertation_director`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vdissertation_director` AS SELECT 
 1 AS `id`,
 1 AS `dissertationId`,
 1 AS `title`,
 1 AS `supervisor`,
 1 AS `studentId`,
 1 AS `studentname`,
 1 AS `userid`,
 1 AS `studentcansubmitonline`,
 1 AS `studentemail`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vdissertation_manager`
--

DROP TABLE IF EXISTS `vdissertation_manager`;
/*!50001 DROP VIEW IF EXISTS `vdissertation_manager`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vdissertation_manager` AS SELECT 
 1 AS `id`,
 1 AS `dissertationId`,
 1 AS `title`,
 1 AS `supervisor`,
 1 AS `studentId`,
 1 AS `studentname`,
 1 AS `userid`,
 1 AS `studentcansubmitonline`,
 1 AS `studentemail`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vlibraryevent`
--

DROP TABLE IF EXISTS `vlibraryevent`;
/*!50001 DROP VIEW IF EXISTS `vlibraryevent`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vlibraryevent` AS SELECT 
 1 AS `id`,
 1 AS `type`,
 1 AS `title`,
 1 AS `startdate`,
 1 AS `enddate`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vroleminmaxusercount`
--

DROP TABLE IF EXISTS `vroleminmaxusercount`;
/*!50001 DROP VIEW IF EXISTS `vroleminmaxusercount`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vroleminmaxusercount` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `maxusers`,
 1 AS `minusers`,
 1 AS `userCount`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vroleusercount`
--

DROP TABLE IF EXISTS `vroleusercount`;
/*!50001 DROP VIEW IF EXISTS `vroleusercount`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vroleusercount` AS SELECT 
 1 AS `roleId`,
 1 AS `userCount`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vstudent`
--

DROP TABLE IF EXISTS `vstudent`;
/*!50001 DROP VIEW IF EXISTS `vstudent`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vstudent` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `userId`,
 1 AS `password`,
 1 AS `adt`,
 1 AS `email`,
 1 AS `telephone`,
 1 AS `am`,
 1 AS `roleId`,
 1 AS `onlinesubmit`,
 1 AS `rolename`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vsubmission`
--

DROP TABLE IF EXISTS `vsubmission`;
/*!50001 DROP VIEW IF EXISTS `vsubmission`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vsubmission` AS SELECT 
 1 AS `id`,
 1 AS `submissionNo`,
 1 AS `dissertationId`,
 1 AS `subject_areas`,
 1 AS `pdf`,
 1 AS `pdf_pages`,
 1 AS `librarianstatusId`,
 1 AS `director_status`,
 1 AS `director_notes`,
 1 AS `managerstatusId`,
 1 AS `manager_notes`,
 1 AS `studentinformmethod`,
 1 AS `studentinformdate`,
 1 AS `dissertation_ID`,
 1 AS `title`,
 1 AS `supervisor`,
 1 AS `studentname`,
 1 AS `userid`,
 1 AS `studentcansubmitonline`,
 1 AS `studentemail`,
 1 AS `librarianstatus`,
 1 AS `managerstatus`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vsubmission_director`
--

DROP TABLE IF EXISTS `vsubmission_director`;
/*!50001 DROP VIEW IF EXISTS `vsubmission_director`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vsubmission_director` AS SELECT 
 1 AS `id`,
 1 AS `submissionNo`,
 1 AS `dissertationId`,
 1 AS `subject_areas`,
 1 AS `pdf`,
 1 AS `pdf_pages`,
 1 AS `librarianstatusId`,
 1 AS `director_status`,
 1 AS `director_notes`,
 1 AS `managerstatusId`,
 1 AS `manager_notes`,
 1 AS `studentinformmethod`,
 1 AS `studentinformdate`,
 1 AS `dissertation_ID`,
 1 AS `title`,
 1 AS `supervisor`,
 1 AS `studentname`,
 1 AS `userid`,
 1 AS `studentcansubmitonline`,
 1 AS `studentemail`,
 1 AS `librarianstatus`,
 1 AS `managerstatus`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vsubmission_manager`
--

DROP TABLE IF EXISTS `vsubmission_manager`;
/*!50001 DROP VIEW IF EXISTS `vsubmission_manager`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vsubmission_manager` AS SELECT 
 1 AS `id`,
 1 AS `submissionNo`,
 1 AS `dissertationId`,
 1 AS `subject_areas`,
 1 AS `pdf`,
 1 AS `pdf_pages`,
 1 AS `librarianstatusId`,
 1 AS `director_status`,
 1 AS `director_notes`,
 1 AS `managerstatusId`,
 1 AS `manager_notes`,
 1 AS `studentinformmethod`,
 1 AS `studentinformdate`,
 1 AS `dissertation_ID`,
 1 AS `title`,
 1 AS `supervisor`,
 1 AS `studentname`,
 1 AS `userid`,
 1 AS `studentcansubmitonline`,
 1 AS `studentemail`,
 1 AS `librarianstatus`,
 1 AS `managerstatus`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vuser`
--

DROP TABLE IF EXISTS `vuser`;
/*!50001 DROP VIEW IF EXISTS `vuser`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vuser` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `userId`,
 1 AS `password`,
 1 AS `adt`,
 1 AS `email`,
 1 AS `telephone`,
 1 AS `am`,
 1 AS `roleId`,
 1 AS `onlinesubmit`,
 1 AS `rolename`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'library2'
--

--
-- Final view structure for view `vcandeleteuserfromrole`
--

/*!50001 DROP VIEW IF EXISTS `vcandeleteuserfromrole`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vcandeleteuserfromrole` AS select `vroleminmaxusercount`.`id` AS `id`,`vroleminmaxusercount`.`name` AS `name` from `vroleminmaxusercount` where (`vroleminmaxusercount`.`userCount` > `vroleminmaxusercount`.`minusers`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vcaninsertusertorole`
--

/*!50001 DROP VIEW IF EXISTS `vcaninsertusertorole`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vcaninsertusertorole` AS select `vroleminmaxusercount`.`id` AS `id`,`vroleminmaxusercount`.`name` AS `name` from `vroleminmaxusercount` where (`vroleminmaxusercount`.`userCount` < `vroleminmaxusercount`.`maxusers`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vdissertation`
--

/*!50001 DROP VIEW IF EXISTS `vdissertation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vdissertation` AS select `d`.`id` AS `id`,`d`.`dissertationId` AS `dissertationId`,`d`.`title` AS `title`,`d`.`supervisor` AS `supervisor`,`d`.`studentId` AS `studentId`,`vs`.`name` AS `studentname`,`vs`.`userId` AS `userid`,`vs`.`onlinesubmit` AS `studentcansubmitonline`,`vs`.`email` AS `studentemail` from (`dissertation` `d` join `vstudent` `vs`) where (`d`.`studentId` = `vs`.`id`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vdissertation_director`
--

/*!50001 DROP VIEW IF EXISTS `vdissertation_director`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vdissertation_director` AS select `vd`.`id` AS `id`,`vd`.`dissertationId` AS `dissertationId`,`vd`.`title` AS `title`,`vd`.`supervisor` AS `supervisor`,`vd`.`studentId` AS `studentId`,`vd`.`studentname` AS `studentname`,`vd`.`userid` AS `userid`,`vd`.`studentcansubmitonline` AS `studentcansubmitonline`,`vd`.`studentemail` AS `studentemail` from (`vdissertation` `vd` join `vsubmission_director` `vsd`) where (`vd`.`id` = `vsd`.`dissertationId`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vdissertation_manager`
--

/*!50001 DROP VIEW IF EXISTS `vdissertation_manager`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vdissertation_manager` AS select `vd`.`id` AS `id`,`vd`.`dissertationId` AS `dissertationId`,`vd`.`title` AS `title`,`vd`.`supervisor` AS `supervisor`,`vd`.`studentId` AS `studentId`,`vd`.`studentname` AS `studentname`,`vd`.`userid` AS `userid`,`vd`.`studentcansubmitonline` AS `studentcansubmitonline`,`vd`.`studentemail` AS `studentemail` from (`vdissertation` `vd` join `vsubmission_manager` `vsm`) where (`vd`.`id` = `vsm`.`dissertationId`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vlibraryevent`
--

/*!50001 DROP VIEW IF EXISTS `vlibraryevent`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vlibraryevent` AS select `libraryevent`.`id` AS `id`,`libraryevent`.`type` AS `type`,`libraryevent`.`title` AS `title`,`libraryevent`.`startdate` AS `startdate`,`libraryevent`.`enddate` AS `enddate` from `libraryevent` where (curdate() < `libraryevent`.`enddate`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vroleminmaxusercount`
--

/*!50001 DROP VIEW IF EXISTS `vroleminmaxusercount`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vroleminmaxusercount` AS select `role`.`id` AS `id`,`role`.`name` AS `name`,`role`.`MaxUsers` AS `maxusers`,`role`.`MinUsers` AS `minusers`,if(isnull(`vroleusercount`.`userCount`),0,`vroleusercount`.`userCount`) AS `userCount` from (`role` left join `vroleusercount` on((`role`.`id` = `vroleusercount`.`roleId`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vroleusercount`
--

/*!50001 DROP VIEW IF EXISTS `vroleusercount`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vroleusercount` AS select `user`.`roleId` AS `roleId`,count(0) AS `userCount` from `user` group by `user`.`roleId` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vstudent`
--

/*!50001 DROP VIEW IF EXISTS `vstudent`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vstudent` AS select `vuser`.`id` AS `id`,`vuser`.`name` AS `name`,`vuser`.`userId` AS `userId`,`vuser`.`password` AS `password`,`vuser`.`adt` AS `adt`,`vuser`.`email` AS `email`,`vuser`.`telephone` AS `telephone`,`vuser`.`am` AS `am`,`vuser`.`roleId` AS `roleId`,`vuser`.`onlinesubmit` AS `onlinesubmit`,`vuser`.`rolename` AS `rolename` from `vuser` where (`vuser`.`rolename` = 'Student') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vsubmission`
--

/*!50001 DROP VIEW IF EXISTS `vsubmission`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vsubmission` AS select `s`.`id` AS `id`,`s`.`submissionNo` AS `submissionNo`,`s`.`dissertationId` AS `dissertationId`,`s`.`subject_areas` AS `subject_areas`,`s`.`pdf` AS `pdf`,`s`.`pdf_pages` AS `pdf_pages`,`s`.`librarianstatusId` AS `librarianstatusId`,`s`.`director_status` AS `director_status`,`s`.`director_notes` AS `director_notes`,`s`.`managerstatusId` AS `managerstatusId`,`s`.`manager_notes` AS `manager_notes`,`s`.`studentinformmethod` AS `studentinformmethod`,`s`.`studentinformdate` AS `studentinformdate`,`vd`.`dissertationId` AS `dissertation_ID`,`vd`.`title` AS `title`,`vd`.`supervisor` AS `supervisor`,`vd`.`studentname` AS `studentname`,`vd`.`userid` AS `userid`,`vd`.`studentcansubmitonline` AS `studentcansubmitonline`,`vd`.`studentemail` AS `studentemail`,`ls`.`status` AS `librarianstatus`,`ms`.`status` AS `managerstatus` from (((`submission` `s` join `vdissertation` `vd`) join `librarian_status` `ls`) join `manager_status` `ms`) where ((`s`.`dissertationId` = `vd`.`id`) and (`s`.`librarianstatusId` = `ls`.`id`) and (`s`.`managerstatusId` = `ms`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vsubmission_director`
--

/*!50001 DROP VIEW IF EXISTS `vsubmission_director`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vsubmission_director` AS select `vsubmission`.`id` AS `id`,`vsubmission`.`submissionNo` AS `submissionNo`,`vsubmission`.`dissertationId` AS `dissertationId`,`vsubmission`.`subject_areas` AS `subject_areas`,`vsubmission`.`pdf` AS `pdf`,`vsubmission`.`pdf_pages` AS `pdf_pages`,`vsubmission`.`librarianstatusId` AS `librarianstatusId`,`vsubmission`.`director_status` AS `director_status`,`vsubmission`.`director_notes` AS `director_notes`,`vsubmission`.`managerstatusId` AS `managerstatusId`,`vsubmission`.`manager_notes` AS `manager_notes`,`vsubmission`.`studentinformmethod` AS `studentinformmethod`,`vsubmission`.`studentinformdate` AS `studentinformdate`,`vsubmission`.`dissertation_ID` AS `dissertation_ID`,`vsubmission`.`title` AS `title`,`vsubmission`.`supervisor` AS `supervisor`,`vsubmission`.`studentname` AS `studentname`,`vsubmission`.`userid` AS `userid`,`vsubmission`.`studentcansubmitonline` AS `studentcansubmitonline`,`vsubmission`.`studentemail` AS `studentemail`,`vsubmission`.`librarianstatus` AS `librarianstatus`,`vsubmission`.`managerstatus` AS `managerstatus` from `vsubmission` where ((ucase(`vsubmission`.`librarianstatus`) = 'ACCEPTED') and (`vsubmission`.`pdf_pages` > 200) and ucase((`vsubmission`.`managerstatus` = 'NOT CHECKED'))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vsubmission_manager`
--

/*!50001 DROP VIEW IF EXISTS `vsubmission_manager`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`tioannid`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vsubmission_manager` AS (select `vsubmission`.`id` AS `id`,`vsubmission`.`submissionNo` AS `submissionNo`,`vsubmission`.`dissertationId` AS `dissertationId`,`vsubmission`.`subject_areas` AS `subject_areas`,`vsubmission`.`pdf` AS `pdf`,`vsubmission`.`pdf_pages` AS `pdf_pages`,`vsubmission`.`librarianstatusId` AS `librarianstatusId`,`vsubmission`.`director_status` AS `director_status`,`vsubmission`.`director_notes` AS `director_notes`,`vsubmission`.`managerstatusId` AS `managerstatusId`,`vsubmission`.`manager_notes` AS `manager_notes`,`vsubmission`.`studentinformmethod` AS `studentinformmethod`,`vsubmission`.`studentinformdate` AS `studentinformdate`,`vsubmission`.`dissertation_ID` AS `dissertation_ID`,`vsubmission`.`title` AS `title`,`vsubmission`.`supervisor` AS `supervisor`,`vsubmission`.`studentname` AS `studentname`,`vsubmission`.`userid` AS `userid`,`vsubmission`.`studentcansubmitonline` AS `studentcansubmitonline`,`vsubmission`.`studentemail` AS `studentemail`,`vsubmission`.`librarianstatus` AS `librarianstatus`,`vsubmission`.`managerstatus` AS `managerstatus` from `vsubmission` where ((ucase(`vsubmission`.`librarianstatus`) = 'ACCEPTED') and (`vsubmission`.`pdf_pages` <= 200) and (ucase(`vsubmission`.`managerstatus`) = 'NOT CHECKED') and isnull(`vsubmission`.`studentinformdate`))) union all (select `vsubmission_director`.`id` AS `id`,`vsubmission_director`.`submissionNo` AS `submissionNo`,`vsubmission_director`.`dissertationId` AS `dissertationId`,`vsubmission_director`.`subject_areas` AS `subject_areas`,`vsubmission_director`.`pdf` AS `pdf`,`vsubmission_director`.`pdf_pages` AS `pdf_pages`,`vsubmission_director`.`librarianstatusId` AS `librarianstatusId`,`vsubmission_director`.`director_status` AS `director_status`,`vsubmission_director`.`director_notes` AS `director_notes`,`vsubmission_director`.`managerstatusId` AS `managerstatusId`,`vsubmission_director`.`manager_notes` AS `manager_notes`,`vsubmission_director`.`studentinformmethod` AS `studentinformmethod`,`vsubmission_director`.`studentinformdate` AS `studentinformdate`,`vsubmission_director`.`dissertation_ID` AS `dissertation_ID`,`vsubmission_director`.`title` AS `title`,`vsubmission_director`.`supervisor` AS `supervisor`,`vsubmission_director`.`studentname` AS `studentname`,`vsubmission_director`.`userid` AS `userid`,`vsubmission_director`.`studentcansubmitonline` AS `studentcansubmitonline`,`vsubmission_director`.`studentemail` AS `studentemail`,`vsubmission_director`.`librarianstatus` AS `librarianstatus`,`vsubmission_director`.`managerstatus` AS `managerstatus` from `vsubmission_director` where (`vsubmission_director`.`director_status` = 1)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vuser`
--

/*!50001 DROP VIEW IF EXISTS `vuser`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vuser` AS select `u`.`id` AS `id`,`u`.`name` AS `name`,`u`.`userId` AS `userId`,`u`.`password` AS `password`,`u`.`adt` AS `adt`,`u`.`email` AS `email`,`u`.`telephone` AS `telephone`,`u`.`am` AS `am`,`u`.`roleId` AS `roleId`,`u`.`onlinesubmit` AS `onlinesubmit`,`r`.`name` AS `rolename` from (`user` `u` join `role` `r`) where (`u`.`roleId` = `r`.`id`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-06 18:52:58
