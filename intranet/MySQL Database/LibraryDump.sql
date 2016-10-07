CREATE DATABASE  IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `library`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	5.5.47

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
  `subject_areas` varchar(500) DEFAULT NULL,
  `pdf` blob,
  `pdf_pages` int(11) DEFAULT NULL,
  `director_status` bit(1) DEFAULT NULL,
  `director_notes` varchar(250) DEFAULT NULL,
  `managerstatusId` int(11) NOT NULL DEFAULT '3',
  `manager_notes` varchar(200) DEFAULT NULL,
  `studentId` int(11) NOT NULL,
  `submissionstatusId` int(11) NOT NULL,
  `ispdfloaded` bit(1) NOT NULL,
  `studentinformmethod` varchar(1) DEFAULT 'X',
  `studentinformdate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `dissertationId_UNIQUE` (`dissertationId`),
  KEY `fk_diss_student_id_idx` (`studentId`),
  KEY `fk_diss_submission_status_id_idx` (`submissionstatusId`),
  KEY `fk_diss_manager_status_id_idx` (`managerstatusId`),
  CONSTRAINT `fk_diss_manager_status_id` FOREIGN KEY (`managerstatusId`) REFERENCES `manager_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_diss_student_id` FOREIGN KEY (`studentId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_diss_submission_status_id` FOREIGN KEY (`submissionstatusId`) REFERENCES `submission_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dissertation`
--

LOCK TABLES `dissertation` WRITE;
/*!40000 ALTER TABLE `dissertation` DISABLE KEYS */;
INSERT INTO `dissertation` VALUES (15,'DS001','Title DS001','Supervisor DS001','Subject area DS001',NULL,160,'\0','',1,'Manager Accepted DS001',29,2,'','E','2015-12-13'),(16,'DS002','Title DS002','Supervisior DS002','Subject area DS002',NULL,220,'','Director Approved DS002',1,'Manager Approved DS002',30,2,'','E','2014-10-23'),(17,'DS003','Title DS003','Supervisior DS003','Subject area DS003',NULL,230,'\0','',3,'',31,2,'','X',NULL),(18,'DS004','Title DS004','Supervisor DS004','Subject area DS004',NULL,NULL,'\0','',3,'',30,4,'\0','X',NULL);
/*!40000 ALTER TABLE `dissertation` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`tioannid`@`%`*/ /*!50003 TRIGGER trg_dissertation_beforeins
BEFORE INSERT ON dissertation FOR EACH ROW
BEGIN
	
    DECLARE m_message VARCHAR(150);
    DECLARE c_submission_status VARCHAR(45);
    DECLARE c_is_pdf_null BOOLEAN;
    DECLARE c_is_pdf_pages_null BOOLEAN;
    
    SET @c_is_pdf_null := NOT NEW.ispdfloaded;
    SET @c_is_pdf_pages_null := ISNULL(NEW.pdf_pages);
    -- Retrieve information about the submission_status
    SET @c_submission_status := (SELECT status  FROM submission_status WHERE id = NEW.submissionstatusId);

	-- Implement business logic
	IF (UCASE(@c_submission_status) = 'ACCEPTED') THEN 
        IF (@c_is_pdf_null IS TRUE) THEN	-- pdf is null
			BEGIN
				SET @m_message := 'TABLE(dissertation).INSERT, pdf cannot be null for ACCEPTED submission status!';
				SIGNAL SQLSTATE '45004' SET MESSAGE_TEXT = @m_message;        
			END;
		ELSE	-- pdf is not null
			IF ((NEW.pdf_pages < 60) OR (NEW.pdf_pages > 260)) THEN
                BEGIN
					SET @m_message := 'TABLE(dissertation).INSERT, pdf_pages valid range is [60, 260] for ACCEPTED submission status!';
					SIGNAL SQLSTATE '45005' SET MESSAGE_TEXT = @m_message;
                END;
			ELSEIF (@c_is_pdf_pages_null IS TRUE) THEN
                BEGIN
					SET @m_message := 'TABLE(dissertation).INSERT, pdf_pages cannot be null for ACCEPTED submission status!';
					SIGNAL SQLSTATE '45006' SET MESSAGE_TEXT = @m_message;
                END;
			END IF;
		END IF;
	ELSEIF (UCASE(@c_submission_status) = 'OUT OF PAGE RANGE') THEN
		IF (@c_is_pdf_null IS TRUE) THEN	-- pdf is null
			SET NEW.pdf_pages := NULL;
		ELSE	-- pdf is not null
			BEGIN
				SET @m_message := 'TABLE(dissertation).INSERT, pdf must be null for OUT OF PAGE RANGE submission status!';
				SIGNAL SQLSTATE '45007' SET MESSAGE_TEXT = @m_message;            
            END;
		END IF;
	ELSE
		IF (@c_is_pdf_null IS TRUE) THEN	-- pdf is null
			SET NEW.pdf_pages := NULL;
		ELSE	-- pdf is not null
			BEGIN
				SET @m_message := 'TABLE(dissertation).INSERT, pdf must be null for NON STANDARD submission status!';
				SIGNAL SQLSTATE '45008' SET MESSAGE_TEXT = @m_message;            
            END;
		END IF;
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
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`tioannid`@`%`*/ /*!50003 TRIGGER trg_dissertation_beforeupd
BEFORE UPDATE ON dissertation FOR EACH ROW
BEGIN
	
    DECLARE m_message VARCHAR(150);
    DECLARE c_submission_status VARCHAR(45);
    DECLARE c_is_pdf_null BOOLEAN;
    DECLARE c_is_pdf_pages_null BOOLEAN;
    
    SET @c_is_pdf_null := NOT NEW.ispdfloaded;
    SET @c_is_pdf_pages_null := ISNULL(NEW.pdf_pages);
    -- Retrieve information about the submission_status
    SET @c_submission_status := (SELECT status  FROM submission_status WHERE id = NEW.submissionstatusId);

	-- Implement business logic
	IF (UCASE(@c_submission_status) = 'ACCEPTED') THEN 
        IF (@c_is_pdf_null IS TRUE) THEN	-- pdf is null
			BEGIN
				SET @m_message := 'TABLE(dissertation).UPDATE, pdf cannot be null for ACCEPTED submission status!';
				SIGNAL SQLSTATE '45010' SET MESSAGE_TEXT = @m_message;        
			END;
		ELSE	-- pdf is not null
			IF ((NEW.pdf_pages < 60) OR (NEW.pdf_pages > 260)) THEN
                BEGIN
					SET @m_message := 'TABLE(dissertation).UPDATE, pdf_pages valid range is [60, 260] for ACCEPTED submission status!';
					SIGNAL SQLSTATE '45011' SET MESSAGE_TEXT = @m_message;
                END;
			ELSEIF (@c_is_pdf_pages_null IS TRUE) THEN
                BEGIN
					SET @m_message := 'TABLE(dissertation).UPDATE, pdf_pages cannot be null for ACCEPTED submission status!';
					SIGNAL SQLSTATE '45012' SET MESSAGE_TEXT = @m_message;
                END;
			END IF;
		END IF;
	ELSEIF (UCASE(@c_submission_status) = 'OUT OF PAGE RANGE') THEN
		IF (@c_is_pdf_null IS TRUE) THEN	-- pdf is null
			SET NEW.pdf_pages := NULL;
		ELSE	-- pdf is not null
			BEGIN
				SET @m_message := 'TABLE(dissertation).UPDATE, pdf must be null for OUT OF PAGE RANGE submission status!';
				SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = @m_message;            
            END;
		END IF;
	ELSE
		IF (@c_is_pdf_null IS TRUE) THEN	-- pdf is null
			SET NEW.pdf_pages := NULL;
		ELSE	-- pdf is not null
			BEGIN
				SET @m_message := 'TABLE(dissertation).UPDATE, pdf must be null for NON STANDARD submission status!';
				SIGNAL SQLSTATE '45014' SET MESSAGE_TEXT = @m_message;            
            END;
		END IF;
	END IF;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
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
-- Table structure for table `submission_status`
--

DROP TABLE IF EXISTS `submission_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submission_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `status_UNIQUE` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission_status`
--

LOCK TABLES `submission_status` WRITE;
/*!40000 ALTER TABLE `submission_status` DISABLE KEYS */;
INSERT INTO `submission_status` VALUES (2,'Accepted'),(4,'Non Standard'),(3,'Out of page range');
/*!40000 ALTER TABLE `submission_status` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  KEY `fk_USER_ROLE1_idx` (`roleId`),
  CONSTRAINT `fk_USER_ROLE1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (21,'Theofilos Ioannidis','S100','tioa','AE105591','tioannid@yahoo.com','2108034901','',6),(22,'Monica Miranda','S200','monica','EXT23456','monicabmir@yahoo.com','2108034901','',10),(28,'Alexandros Ioannidis','S400','alex','','','','',11),(29,'Nick Ioannidis','S1001','nick','','nickioan@freemail.gr','2310725178','10368',7),(30,'Roula Ioannidi','S1002','roula','','stioann@yahoo.gr','2310746843','11453',7),(31,'Kostas Theologou','S1003','kostas','AB236758','ktheologou@hotmail.com','2310453786','19456',7),(32,'Theocharis Ioannidis','S300','theo','AE105689','','2310746843','',9);
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
 1 AS `subject_areas`,
 1 AS `pdf`,
 1 AS `pdf_pages`,
 1 AS `director_status`,
 1 AS `director_notes`,
 1 AS `managerstatusId`,
 1 AS `manager_notes`,
 1 AS `studentId`,
 1 AS `submissionstatusId`,
 1 AS `ispdfloaded`,
 1 AS `studentinformmethod`,
 1 AS `studentinformdate`,
 1 AS `userid`,
 1 AS `student_name`,
 1 AS `submission_status`,
 1 AS `manager_status`*/;
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
 1 AS `subject_areas`,
 1 AS `pdf`,
 1 AS `pdf_pages`,
 1 AS `director_status`,
 1 AS `director_notes`,
 1 AS `managerstatusId`,
 1 AS `manager_notes`,
 1 AS `studentId`,
 1 AS `submissionstatusId`,
 1 AS `ispdfloaded`,
 1 AS `studentinformmethod`,
 1 AS `studentinformdate`,
 1 AS `userid`,
 1 AS `student_name`,
 1 AS `submission_status`,
 1 AS `manager_status`*/;
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
 1 AS `subject_areas`,
 1 AS `pdf`,
 1 AS `pdf_pages`,
 1 AS `director_status`,
 1 AS `director_notes`,
 1 AS `managerstatusId`,
 1 AS `manager_notes`,
 1 AS `studentId`,
 1 AS `submissionstatusId`,
 1 AS `ispdfloaded`,
 1 AS `studentinformmethod`,
 1 AS `studentinformdate`,
 1 AS `userid`,
 1 AS `student_name`,
 1 AS `submission_status`,
 1 AS `manager_status`*/;
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
 1 AS `roleid`,
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
 1 AS `roleId`*/;
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
 1 AS `rolename`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'library'
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
/*!50001 VIEW `vdissertation` AS select `d`.`id` AS `id`,`d`.`dissertationId` AS `dissertationId`,`d`.`title` AS `title`,`d`.`supervisor` AS `supervisor`,`d`.`subject_areas` AS `subject_areas`,`d`.`pdf` AS `pdf`,`d`.`pdf_pages` AS `pdf_pages`,`d`.`director_status` AS `director_status`,`d`.`director_notes` AS `director_notes`,`d`.`managerstatusId` AS `managerstatusId`,`d`.`manager_notes` AS `manager_notes`,`d`.`studentId` AS `studentId`,`d`.`submissionstatusId` AS `submissionstatusId`,`d`.`ispdfloaded` AS `ispdfloaded`,`d`.`studentinformmethod` AS `studentinformmethod`,`d`.`studentinformdate` AS `studentinformdate`,`stud`.`userId` AS `userid`,`stud`.`name` AS `student_name`,`sub`.`status` AS `submission_status`,`man`.`status` AS `manager_status` from (((`dissertation` `d` join `vstudent` `stud`) join `submission_status` `sub`) join `manager_status` `man`) where ((`d`.`studentId` = `stud`.`id`) and (`d`.`submissionstatusId` = `sub`.`id`) and (`d`.`managerstatusId` = `man`.`id`)) */;
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
/*!50013 DEFINER=`tioannid`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vdissertation_director` AS select `v`.`id` AS `id`,`v`.`dissertationId` AS `dissertationId`,`v`.`title` AS `title`,`v`.`supervisor` AS `supervisor`,`v`.`subject_areas` AS `subject_areas`,`v`.`pdf` AS `pdf`,`v`.`pdf_pages` AS `pdf_pages`,`v`.`director_status` AS `director_status`,`v`.`director_notes` AS `director_notes`,`v`.`managerstatusId` AS `managerstatusId`,`v`.`manager_notes` AS `manager_notes`,`v`.`studentId` AS `studentId`,`v`.`submissionstatusId` AS `submissionstatusId`,`v`.`ispdfloaded` AS `ispdfloaded`,`v`.`studentinformmethod` AS `studentinformmethod`,`v`.`studentinformdate` AS `studentinformdate`,`v`.`userid` AS `userid`,`v`.`student_name` AS `student_name`,`v`.`submission_status` AS `submission_status`,`v`.`manager_status` AS `manager_status` from `vdissertation` `v` where ((ucase(`v`.`submission_status`) = 'ACCEPTED') and (`v`.`pdf_pages` > 200)) */;
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
/*!50013 DEFINER=`tioannid`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `vdissertation_manager` AS (select `d`.`id` AS `id`,`d`.`dissertationId` AS `dissertationId`,`d`.`title` AS `title`,`d`.`supervisor` AS `supervisor`,`d`.`subject_areas` AS `subject_areas`,`d`.`pdf` AS `pdf`,`d`.`pdf_pages` AS `pdf_pages`,`d`.`director_status` AS `director_status`,`d`.`director_notes` AS `director_notes`,`d`.`managerstatusId` AS `managerstatusId`,`d`.`manager_notes` AS `manager_notes`,`d`.`studentId` AS `studentId`,`d`.`submissionstatusId` AS `submissionstatusId`,`d`.`ispdfloaded` AS `ispdfloaded`,`d`.`studentinformmethod` AS `studentinformmethod`,`d`.`studentinformdate` AS `studentinformdate`,`d`.`userid` AS `userid`,`d`.`student_name` AS `student_name`,`d`.`submission_status` AS `submission_status`,`d`.`manager_status` AS `manager_status` from `vdissertation` `d` where ((ucase(`d`.`submission_status`) = 'ACCEPTED') and (`d`.`pdf_pages` < 200))) union all (select `v`.`id` AS `id`,`v`.`dissertationId` AS `dissertationId`,`v`.`title` AS `title`,`v`.`supervisor` AS `supervisor`,`v`.`subject_areas` AS `subject_areas`,`v`.`pdf` AS `pdf`,`v`.`pdf_pages` AS `pdf_pages`,`v`.`director_status` AS `director_status`,`v`.`director_notes` AS `director_notes`,`v`.`managerstatusId` AS `managerstatusId`,`v`.`manager_notes` AS `manager_notes`,`v`.`studentId` AS `studentId`,`v`.`submissionstatusId` AS `submissionstatusId`,`v`.`ispdfloaded` AS `ispdfloaded`,`v`.`studentinformmethod` AS `studentinformmethod`,`v`.`studentinformdate` AS `studentinformdate`,`v`.`userid` AS `userid`,`v`.`student_name` AS `student_name`,`v`.`submission_status` AS `submission_status`,`v`.`manager_status` AS `manager_status` from `vdissertation_director` `v` where (`v`.`director_status` = 1)) */;
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
/*!50001 VIEW `vroleminmaxusercount` AS select `role`.`id` AS `id`,`role`.`name` AS `name`,`role`.`MaxUsers` AS `maxusers`,`role`.`MinUsers` AS `minusers`,if(isnull(`vroleusercount`.`userCount`),0,`vroleusercount`.`userCount`) AS `userCount` from (`role` left join `vroleusercount` on((`role`.`id` = `vroleusercount`.`roleid`))) */;
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
/*!50001 VIEW `vroleusercount` AS select `user`.`roleId` AS `roleid`,count(0) AS `userCount` from `user` group by `user`.`roleId` */;
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
/*!50001 VIEW `vstudent` AS select `user`.`id` AS `id`,`user`.`name` AS `name`,`user`.`userId` AS `userId`,`user`.`password` AS `password`,`user`.`adt` AS `adt`,`user`.`email` AS `email`,`user`.`telephone` AS `telephone`,`user`.`am` AS `am`,`user`.`roleId` AS `roleId` from `user` where `user`.`roleId` in (select `role`.`id` from `role` where (`role`.`name` = 'Student')) */;
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
/*!50001 VIEW `vuser` AS select `user`.`id` AS `id`,`user`.`name` AS `name`,`user`.`userId` AS `userId`,`user`.`password` AS `password`,`user`.`adt` AS `adt`,`user`.`email` AS `email`,`user`.`telephone` AS `telephone`,`user`.`am` AS `am`,`user`.`roleId` AS `roleId`,`role`.`name` AS `rolename` from (`user` join `role`) where (`user`.`roleId` = `role`.`id`) */;
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

-- Dump completed on 2015-12-15 16:37:44
