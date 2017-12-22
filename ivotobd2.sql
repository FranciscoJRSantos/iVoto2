-- MySQL dump 10.13  Distrib 5.7.20, for osx10.13 (x86_64)
--
-- Host: localhost    Database: ivotobd2
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `eleicao`
--

DROP TABLE IF EXISTS `eleicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eleicao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(512) NOT NULL,
  `inicio` datetime NOT NULL,
  `fim` datetime NOT NULL,
  `descricao` varchar(512) DEFAULT NULL,
  `tipo` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eleicao`
--

LOCK TABLES `eleicao` WRITE;
/*!40000 ALTER TABLE `eleicao` DISABLE KEYS */;
INSERT INTO `eleicao` VALUES (1,'nucleo1','2017-12-13 20:02:01','2020-03-20 00:00:00','nucleo1',0),(2,'nucleo','2016-03-20 00:00:00','2020-02-20 00:00:00','nucleo',0),(3,'nucleo1','2017-12-13 00:00:00','2017-12-13 00:00:00','nucelo2',0),(4,'nucl3','2017-12-13 00:00:00','2017-12-13 00:00:00','nucleo3',0),(5,'nucleo3','2017-12-13 00:00:00','2017-12-13 00:00:00','nucleo3',0),(6,'nucleo4','2017-12-13 00:00:00','2017-12-13 00:00:00','nucleo4',0),(7,'NEI','2020-02-20 00:00:00','2020-02-22 00:00:00','Nucleo de Estudantes de Informática',0),(8,'NEEC','2020-02-20 00:00:00','2020-02-22 00:00:00','Nucleo de Estudantes de Eletro',0),(9,'nucleo tres','2020-02-20 00:00:00','2020-02-22 00:00:00','nucleo3',0);
/*!40000 ALTER TABLE `eleicao` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`bd`@`localhost`*/ /*!50003 TRIGGER after_eleicao_create 
    AFTER INSERT ON eleicao
    FOR EACH ROW 
BEGIN
  INSERT INTO lista (nome,tipo_utilizador,votos,eleicao_id) VALUES ("Blank",0,0,new.ID);
  INSERT INTO lista (nome,tipo_utilizador,votos,eleicao_id) VALUES ("Null",0,0,new.ID);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `eleicao_utilizador`
--

DROP TABLE IF EXISTS `eleicao_utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eleicao_utilizador` (
  `unidade_organica_nome` varchar(512) DEFAULT NULL,
  `eleicao_id` int(11) DEFAULT NULL,
  `utilizador_numero_cc` int(11) DEFAULT NULL,
  `mesa_voto_numero` int(11) DEFAULT NULL,
  `data_voto` datetime DEFAULT CURRENT_TIMESTAMP,
  KEY `eleicao_utilizador_fk2` (`eleicao_id`),
  KEY `eleicao_utilizador_fk3` (`utilizador_numero_cc`),
  KEY `eleicao_utilizador_fk4` (`unidade_organica_nome`),
  CONSTRAINT `eleicao_utilizador_fk1` FOREIGN KEY (`unidade_organica_nome`) REFERENCES `unidade_organica_eleicao` (`unidade_organica_nome`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `eleicao_utilizador_fk2` FOREIGN KEY (`eleicao_id`) REFERENCES `eleicao` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `eleicao_utilizador_fk3` FOREIGN KEY (`utilizador_numero_cc`) REFERENCES `utilizador` (`numero_cc`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `eleicao_utilizador_fk4` FOREIGN KEY (`unidade_organica_nome`) REFERENCES `mesa_voto` (`unidade_organica_nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eleicao_utilizador`
--

LOCK TABLES `eleicao_utilizador` WRITE;
/*!40000 ALTER TABLE `eleicao_utilizador` DISABLE KEYS */;
INSERT INTO `eleicao_utilizador` VALUES (NULL,1,14789471,NULL,'2017-12-21 13:04:10');
/*!40000 ALTER TABLE `eleicao_utilizador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista`
--

DROP TABLE IF EXISTS `lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lista` (
  `nome` varchar(512) NOT NULL,
  `votos` int(11) NOT NULL,
  `tipo_utilizador` tinyint(4) NOT NULL,
  `eleicao_id` int(11) NOT NULL,
  KEY `AK_KEY_2` (`eleicao_id`,`nome`),
  CONSTRAINT `lista_fk1` FOREIGN KEY (`eleicao_id`) REFERENCES `eleicao` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista`
--

LOCK TABLES `lista` WRITE;
/*!40000 ALTER TABLE `lista` DISABLE KEYS */;
INSERT INTO `lista` VALUES ('Blank',0,0,1),('Null',0,0,1),('Lista J',3,1,1),('Lista T',0,1,1),('Blank',0,0,2),('Null',0,0,2),('Blank',0,0,3),('Null',0,0,3),('Blank',0,0,4),('Null',0,0,4),('Lista T',0,1,1),('Blank',0,0,5),('Null',0,0,5),('Blank',0,0,6),('Null',0,0,6),('Blank',0,0,7),('Null',0,0,7),('Blank',0,0,8),('Null',0,0,8),('Blank',0,0,9),('Null',0,0,9),('Lista V',0,0,9);
/*!40000 ALTER TABLE `lista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa_voto`
--

DROP TABLE IF EXISTS `mesa_voto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesa_voto` (
  `numero` int(11) NOT NULL,
  `unidade_organica_nome` varchar(512) NOT NULL,
  `eleicao_id` int(11) NOT NULL,
  KEY `AK_KEY_1` (`numero`,`eleicao_id`),
  KEY `mesa_voto_fk1` (`unidade_organica_nome`),
  KEY `mesa_voto_fk2` (`eleicao_id`),
  CONSTRAINT `mesa_voto_fk1` FOREIGN KEY (`unidade_organica_nome`) REFERENCES `unidade_organica` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mesa_voto_fk2` FOREIGN KEY (`eleicao_id`) REFERENCES `eleicao` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa_voto`
--

LOCK TABLES `mesa_voto` WRITE;
/*!40000 ALTER TABLE `mesa_voto` DISABLE KEYS */;
INSERT INTO `mesa_voto` VALUES (1,'DEI',1),(2,'FDUC',1),(1,'DEEC',9);
/*!40000 ALTER TABLE `mesa_voto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa_voto_utilizador`
--

DROP TABLE IF EXISTS `mesa_voto_utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesa_voto_utilizador` (
  `mesa_voto_numero` int(11) DEFAULT NULL,
  `eleicao_id` int(11) DEFAULT NULL,
  `utilizador_numero_cc` int(11) DEFAULT NULL,
  KEY `mesa_voto_utilizador_fk1` (`mesa_voto_numero`,`eleicao_id`),
  KEY `mesa_voto_utilizador_fk2` (`utilizador_numero_cc`),
  CONSTRAINT `mesa_voto_utilizador_fk1` FOREIGN KEY (`mesa_voto_numero`, `eleicao_id`) REFERENCES `mesa_voto` (`numero`, `eleicao_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mesa_voto_utilizador_fk2` FOREIGN KEY (`utilizador_numero_cc`) REFERENCES `utilizador` (`numero_cc`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa_voto_utilizador`
--

LOCK TABLES `mesa_voto_utilizador` WRITE;
/*!40000 ALTER TABLE `mesa_voto_utilizador` DISABLE KEYS */;
INSERT INTO `mesa_voto_utilizador` VALUES (1,1,14789471),(2,1,12345678),(1,9,14789471);
/*!40000 ALTER TABLE `mesa_voto_utilizador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade_organica`
--

DROP TABLE IF EXISTS `unidade_organica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade_organica` (
  `nome` varchar(512) NOT NULL,
  `pertence` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`nome`),
  KEY `unidade_organica_fk1` (`pertence`),
  CONSTRAINT `unidade_organica_fk1` FOREIGN KEY (`pertence`) REFERENCES `unidade_organica` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade_organica`
--

LOCK TABLES `unidade_organica` WRITE;
/*!40000 ALTER TABLE `unidade_organica` DISABLE KEYS */;
INSERT INTO `unidade_organica` VALUES ('FCTUC',NULL),('FDUC',NULL),('null',NULL),('DEEC','FCTUC'),('DEI','FCTUC');
/*!40000 ALTER TABLE `unidade_organica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade_organica_eleicao`
--

DROP TABLE IF EXISTS `unidade_organica_eleicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade_organica_eleicao` (
  `unidade_organica_nome` varchar(512) DEFAULT NULL,
  `eleicao_id` int(11) DEFAULT NULL,
  KEY `unidade_organica_eleicao_fk1` (`unidade_organica_nome`),
  KEY `unidade_organica_eleicao_fk2` (`eleicao_id`),
  CONSTRAINT `unidade_organica_eleicao_fk1` FOREIGN KEY (`unidade_organica_nome`) REFERENCES `unidade_organica` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `unidade_organica_eleicao_fk2` FOREIGN KEY (`eleicao_id`) REFERENCES `eleicao` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade_organica_eleicao`
--

LOCK TABLES `unidade_organica_eleicao` WRITE;
/*!40000 ALTER TABLE `unidade_organica_eleicao` DISABLE KEYS */;
INSERT INTO `unidade_organica_eleicao` VALUES ('DEI',1),('DEI',2),('FDUC',3),('FDUC',4),('FDUC',5),('FDUC',6),('DEI',8),('DEEC',9);
/*!40000 ALTER TABLE `unidade_organica_eleicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade_organica_utilizador`
--

DROP TABLE IF EXISTS `unidade_organica_utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade_organica_utilizador` (
  `unidade_organica_nome` varchar(512) DEFAULT NULL,
  `utilizador_numero_cc` int(11) DEFAULT NULL,
  KEY `unidade_organica_utilizador_fk1` (`unidade_organica_nome`),
  KEY `unidade_organica_utilizador_fk2` (`utilizador_numero_cc`),
  CONSTRAINT `unidade_organica_utilizador_fk1` FOREIGN KEY (`unidade_organica_nome`) REFERENCES `unidade_organica` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `unidade_organica_utilizador_fk2` FOREIGN KEY (`utilizador_numero_cc`) REFERENCES `utilizador` (`numero_cc`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade_organica_utilizador`
--

LOCK TABLES `unidade_organica_utilizador` WRITE;
/*!40000 ALTER TABLE `unidade_organica_utilizador` DISABLE KEYS */;
INSERT INTO `unidade_organica_utilizador` VALUES ('DEI',14789471),('FDUC',12345678),('DEI',87654321),('DEI',87766554);
/*!40000 ALTER TABLE `unidade_organica_utilizador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizador`
--

DROP TABLE IF EXISTS `utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utilizador` (
  `numero_cc` int(11) NOT NULL,
  `nome` varchar(512) NOT NULL,
  `password_hashed` varchar(512) NOT NULL,
  `morada` varchar(512) NOT NULL,
  `contacto` int(11) NOT NULL,
  `validade_cc` date NOT NULL,
  `tipo` tinyint(4) NOT NULL,
  PRIMARY KEY (`numero_cc`),
  UNIQUE KEY `contacto` (`contacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizador`
--

LOCK TABLES `utilizador` WRITE;
/*!40000 ALTER TABLE `utilizador` DISABLE KEYS */;
INSERT INTO `utilizador` VALUES (12345678,'nome3','secret','morada1',123456789,'2020-02-20',1),(14789471,'nome1','secret','morada1',918433131,'2023-02-20',1),(87654321,'xico','secret','Matas',213243546,'2020-02-20',0),(87766554,'ti mendes','secret','matas',122334456,'2020-02-20',0);
/*!40000 ALTER TABLE `utilizador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizador_lista`
--

DROP TABLE IF EXISTS `utilizador_lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utilizador_lista` (
  `utilizador_numero_cc` int(11) DEFAULT NULL,
  `lista_eleicao_id` int(11) DEFAULT NULL,
  `lista_nome` varchar(512) NOT NULL,
  KEY `utilizador_lista_fk1` (`utilizador_numero_cc`),
  KEY `utilizador_lista_fk2` (`lista_eleicao_id`),
  CONSTRAINT `utilizador_lista_fk1` FOREIGN KEY (`utilizador_numero_cc`) REFERENCES `utilizador` (`numero_cc`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `utilizador_lista_fk2` FOREIGN KEY (`lista_eleicao_id`) REFERENCES `lista` (`eleicao_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizador_lista`
--

LOCK TABLES `utilizador_lista` WRITE;
/*!40000 ALTER TABLE `utilizador_lista` DISABLE KEYS */;
INSERT INTO `utilizador_lista` VALUES (14789471,1,'Lista J'),(14789471,1,'Lista T'),(12345678,1,'Lista T'),(14789471,9,'Lista V');
/*!40000 ALTER TABLE `utilizador_lista` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-21 15:55:12
