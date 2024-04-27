-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: food_management_system
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_group`
--

DROP TABLE IF EXISTS `auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group`
--

LOCK TABLES `auth_group` WRITE;
/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_group_permissions`
--

DROP TABLE IF EXISTS `auth_group_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group_permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group_permissions`
--

LOCK TABLES `auth_group_permissions` WRITE;
/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_permission`
--

DROP TABLE IF EXISTS `auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content_type_id` int NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`),
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add log entry',1,'add_logentry'),(2,'Can change log entry',1,'change_logentry'),(3,'Can delete log entry',1,'delete_logentry'),(4,'Can view log entry',1,'view_logentry'),(5,'Can add permission',2,'add_permission'),(6,'Can change permission',2,'change_permission'),(7,'Can delete permission',2,'delete_permission'),(8,'Can view permission',2,'view_permission'),(9,'Can add group',3,'add_group'),(10,'Can change group',3,'change_group'),(11,'Can delete group',3,'delete_group'),(12,'Can view group',3,'view_group'),(13,'Can add content type',4,'add_contenttype'),(14,'Can change content type',4,'change_contenttype'),(15,'Can delete content type',4,'delete_contenttype'),(16,'Can view content type',4,'view_contenttype'),(17,'Can add session',5,'add_session'),(18,'Can change session',5,'change_session'),(19,'Can delete session',5,'delete_session'),(20,'Can view session',5,'view_session'),(21,'Can add users',6,'add_users'),(22,'Can change users',6,'change_users'),(23,'Can delete users',6,'delete_users'),(24,'Can view users',6,'view_users'),(25,'Can add food',7,'add_food'),(26,'Can change food',7,'change_food'),(27,'Can delete food',7,'delete_food'),(28,'Can view food',7,'view_food'),(29,'Can add ngo',8,'add_ngo'),(30,'Can change ngo',8,'change_ngo'),(31,'Can delete ngo',8,'delete_ngo'),(32,'Can view ngo',8,'view_ngo'),(33,'Can add report',9,'add_report'),(34,'Can change report',9,'change_report'),(35,'Can delete report',9,'delete_report'),(36,'Can view report',9,'view_report'),(37,'Can add notification',10,'add_notification'),(38,'Can change notification',10,'change_notification'),(39,'Can delete notification',10,'delete_notification'),(40,'Can view notification',10,'view_notification'),(41,'Can add history',11,'add_history'),(42,'Can change history',11,'change_history'),(43,'Can delete history',11,'delete_history'),(44,'Can view history',11,'view_history'),(45,'Can add Token',12,'add_token'),(46,'Can change Token',12,'change_token'),(47,'Can delete Token',12,'delete_token'),(48,'Can view Token',12,'view_token'),(49,'Can add token',13,'add_tokenproxy'),(50,'Can change token',13,'change_tokenproxy'),(51,'Can delete token',13,'delete_tokenproxy'),(52,'Can view token',13,'view_tokenproxy'),(53,'Can add device',14,'add_device'),(54,'Can change device',14,'change_device'),(55,'Can delete device',14,'delete_device'),(56,'Can view device',14,'view_device');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authtoken_token`
--

DROP TABLE IF EXISTS `authtoken_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authtoken_token` (
  `key` varchar(40) NOT NULL,
  `created` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `authtoken_token_user_id_35299eff_fk_foodshare_users_id` FOREIGN KEY (`user_id`) REFERENCES `foodshare_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authtoken_token`
--

LOCK TABLES `authtoken_token` WRITE;
/*!40000 ALTER TABLE `authtoken_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `authtoken_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_admin_log`
--

DROP TABLE IF EXISTS `django_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_admin_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint unsigned NOT NULL,
  `change_message` longtext NOT NULL,
  `content_type_id` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  KEY `django_admin_log_user_id_c564eba6_fk_foodshare_users_id` (`user_id`),
  CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_foodshare_users_id` FOREIGN KEY (`user_id`) REFERENCES `foodshare_users` (`id`),
  CONSTRAINT `django_admin_log_chk_1` CHECK ((`action_flag` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_admin_log`
--

LOCK TABLES `django_admin_log` WRITE;
/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
INSERT INTO `django_admin_log` VALUES (1,'2024-04-11 10:43:43.302079','5','Food object (5)',2,'[{\"changed\": {\"fields\": [\"Is delete\"]}}]',7,1),(2,'2024-04-11 10:43:54.301762','4','Food object (4)',2,'[{\"changed\": {\"fields\": [\"Is delete\"]}}]',7,1),(3,'2024-04-13 01:26:25.464919','2','Report object (2)',2,'[{\"changed\": {\"fields\": [\"Is verify\"]}}]',9,1),(4,'2024-04-13 01:26:48.733181','1','Report object (1)',2,'[{\"changed\": {\"fields\": [\"Is verify\", \"Modify by\", \"Modify date\"]}}]',9,1),(5,'2024-04-13 01:42:07.335068','1','Report object (1)',2,'[{\"changed\": {\"fields\": [\"Is verify\"]}}]',9,1),(6,'2024-04-13 09:46:08.499193','1','Ngo object (1)',1,'[{\"added\": {}}]',8,1),(7,'2024-04-13 12:42:48.245142','1','Ngo object (1)',2,'[{\"changed\": {\"fields\": [\"<built-in method now of type object at 0x00007FFD607F12B0>\"]}}]',8,1),(8,'2024-04-14 04:33:56.133839','4','Notification object (4)',3,'',10,1),(9,'2024-04-14 04:34:01.187736','3','Notification object (3)',3,'',10,1),(10,'2024-04-14 04:34:06.372766','2','Notification object (2)',3,'',10,1),(11,'2024-04-14 04:34:11.279757','1','Notification object (1)',3,'',10,1),(12,'2024-04-15 14:00:54.095875','27','Food object (27)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',7,1),(13,'2024-04-15 14:01:02.206047','31','Food object (31)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',7,1),(14,'2024-04-15 14:01:12.369046','30','Food object (30)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',7,1),(15,'2024-04-15 14:01:24.877584','29','Food object (29)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',7,1),(16,'2024-04-15 14:01:33.812092','26','Food object (26)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',7,1),(17,'2024-04-15 14:01:42.626346','6','Food object (6)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',7,1),(18,'2024-04-15 14:26:06.380440','29','Food object (29)',3,'',7,1),(19,'2024-04-15 14:53:13.417415','31','Food object (31)',3,'',7,1),(20,'2024-04-15 14:53:18.032482','30','Food object (30)',3,'',7,1),(21,'2024-04-15 14:53:22.916746','28','Food object (28)',3,'',7,1),(22,'2024-04-15 14:53:27.637088','27','Food object (27)',3,'',7,1),(23,'2024-04-15 14:53:36.139414','26','Food object (26)',3,'',7,1),(24,'2024-04-15 14:53:42.244929','24','Food object (24)',3,'',7,1),(25,'2024-04-15 14:53:47.225059','23','Food object (23)',3,'',7,1),(26,'2024-04-15 14:53:52.401895','22','Food object (22)',3,'',7,1),(27,'2024-04-15 14:53:56.888006','21','Food object (21)',3,'',7,1),(28,'2024-04-15 14:54:01.041286','19','Food object (19)',3,'',7,1),(29,'2024-04-15 14:54:05.687009','18','Food object (18)',3,'',7,1),(30,'2024-04-15 14:54:09.978726','20','Food object (20)',3,'',7,1),(31,'2024-04-15 14:54:13.976402','17','Food object (17)',3,'',7,1),(32,'2024-04-15 14:54:19.399255','16','Food object (16)',3,'',7,1),(33,'2024-04-15 14:54:24.172235','25','Food object (25)',3,'',7,1),(34,'2024-04-15 14:54:29.344472','14','Food object (14)',3,'',7,1),(35,'2024-04-15 14:54:33.428530','13','Food object (13)',3,'',7,1),(36,'2024-04-15 14:54:38.238129','12','Food object (12)',3,'',7,1),(37,'2024-04-15 14:54:43.065171','15','Food object (15)',3,'',7,1),(38,'2024-04-15 14:54:48.370595','10','Food object (10)',3,'',7,1),(39,'2024-04-15 14:54:52.253479','9','Food object (9)',3,'',7,1),(40,'2024-04-15 14:54:56.407221','8','Food object (8)',3,'',7,1),(41,'2024-04-15 14:55:01.460251','5','Food object (5)',3,'',7,1),(42,'2024-04-15 14:55:06.081394','6','Food object (6)',3,'',7,1),(43,'2024-04-15 14:55:09.560946','3','Food object (3)',3,'',7,1),(44,'2024-04-15 14:55:14.277749','4','Food object (4)',3,'',7,1),(45,'2024-04-15 14:55:18.601543','2','Food object (2)',3,'',7,1),(46,'2024-04-15 14:55:24.468075','1','Food object (1)',3,'',7,1),(47,'2024-04-15 14:55:28.289137','7','Food object (7)',3,'',7,1),(48,'2024-04-15 14:55:32.970427','11','Food object (11)',3,'',7,1),(49,'2024-04-16 04:15:46.614046','38','Food object (38)',2,'[{\"changed\": {\"fields\": [\"Food name\", \"Pick up location\", \"Descriptions\", \"Modify by\", \"Modify date\"]}}]',7,1),(50,'2024-04-16 04:17:45.900129','38','Food object (38)',2,'[{\"changed\": {\"fields\": [\"Stream url\"]}}]',7,1),(51,'2024-04-16 04:18:44.700089','26','History object (26)',3,'',11,1),(52,'2024-04-16 07:00:20.053697','2','Donor',3,'',6,1),(53,'2024-04-16 07:32:02.797287','12','Sitaram',3,'',6,1),(54,'2024-04-16 07:32:08.558624','11','Sitaram Thing',3,'',6,1),(55,'2024-04-16 07:32:13.312516','10','New User Name',3,'',6,1),(56,'2024-04-16 08:07:20.131643','8','ABCE',3,'',6,1),(57,'2024-04-16 08:07:25.648898','7','Donor Nepal',3,'',6,1),(58,'2024-04-16 08:07:34.387472','9','Nepal Neplai',3,'',6,1),(59,'2024-04-16 08:07:39.751942','4','Farmer',3,'',6,1),(60,'2024-04-16 08:07:49.585238','5','Username',3,'',6,1),(61,'2024-04-16 08:08:18.424433','6','Staram Thing',3,'',6,1),(62,'2024-04-16 08:08:23.504636','3','volunteer',3,'',6,1),(63,'2024-04-16 08:42:32.306110','40','Food object (40)',2,'[{\"changed\": {\"fields\": [\"Stream url\", \"Modify by\", \"Modify date\"]}}]',7,1),(64,'2024-04-16 08:45:49.044227','41','Food object (41)',2,'[{\"changed\": {\"fields\": [\"Stream url\", \"Modify by\", \"Modify date\"]}}]',7,1),(65,'2024-04-16 08:48:42.571965','42','Food object (42)',2,'[{\"changed\": {\"fields\": [\"Stream url\", \"Modify by\", \"Modify date\"]}}]',7,1),(66,'2024-04-16 08:53:59.608324','43','Food object (43)',2,'[{\"changed\": {\"fields\": [\"Stream url\", \"Modify by\", \"Modify date\"]}}]',7,1),(67,'2024-04-16 08:57:53.605956','44','Food object (44)',2,'[{\"changed\": {\"fields\": [\"Food name\", \"Stream url\", \"Modify by\", \"Modify date\"]}}]',7,1),(68,'2024-04-16 09:00:43.378887','45','Food object (45)',2,'[{\"changed\": {\"fields\": [\"Stream url\", \"Modify by\", \"Modify date\"]}}]',7,1),(69,'2024-04-16 12:29:00.664009','1','Ngo object (1)',2,'[{\"changed\": {\"fields\": [\"Ngo stream url\"]}}]',8,1),(70,'2024-04-17 15:59:19.497747','21','laxman',2,'[{\"changed\": {\"fields\": [\"Email\", \"Address\", \"Contact number\", \"Gender\", \"Abouts user\", \"Photo url\", \"Is active\"]}}]',6,1),(71,'2024-04-17 16:01:25.453054','21','laxman',2,'[{\"changed\": {\"fields\": [\"Email\"]}}]',6,1),(72,'2024-04-17 17:56:24.477941','14','volunteerrr',2,'[{\"changed\": {\"fields\": [\"Address\", \"Contact number\", \"Abouts user\", \"Ngo\"]}}]',6,1),(73,'2024-04-18 02:44:25.269356','16','Notification object (16)',3,'',10,1),(74,'2024-04-18 02:44:31.606728','15','Notification object (15)',3,'',10,1),(75,'2024-04-18 02:44:50.596305','14','Notification object (14)',3,'',10,1),(76,'2024-04-18 02:44:57.059686','13','Notification object (13)',3,'',10,1),(77,'2024-04-18 02:45:00.855589','11','Notification object (11)',3,'',10,1),(78,'2024-04-18 02:45:03.829273','10','Notification object (10)',3,'',10,1),(79,'2024-04-18 02:45:06.773915','12','Notification object (12)',3,'',10,1),(80,'2024-04-18 02:45:09.739739','8','Notification object (8)',3,'',10,1),(81,'2024-04-18 02:45:13.475257','9','Notification object (9)',3,'',10,1),(82,'2024-04-18 04:03:57.778167','2','Device object (2)',1,'[{\"added\": {}}]',14,1),(83,'2024-04-18 04:07:16.766328','3','Device object (3)',1,'[{\"added\": {}}]',14,1),(84,'2024-04-18 04:15:49.452551','3','Device object (3)',2,'[{\"changed\": {\"fields\": [\"Token\"]}}]',14,1),(85,'2024-04-18 04:36:45.921294','2','Notification object (2)',2,'[{\"changed\": {\"fields\": [\"Status\"]}}]',10,1),(86,'2024-04-19 09:55:41.413388','17','a',3,'',6,1),(87,'2024-04-19 09:55:46.627691','19','g',3,'',6,1),(88,'2024-04-19 09:55:52.194646','18','satish',3,'',6,1),(89,'2024-04-19 09:55:58.575255','23','Na',3,'',6,1),(90,'2024-04-19 09:56:16.211990','20','pau',3,'',6,1),(91,'2024-04-20 14:21:14.557136','27','taus',2,'[{\"changed\": {\"fields\": [\"Address\", \"Contact number\", \"Gender\", \"Abouts user\", \"Photo url\", \"Is active\", \"Ngo\", \"Modify by\", \"Modify date\"]}}]',6,1),(92,'2024-04-20 15:18:18.868095','26','JHcdajcbds',2,'[{\"changed\": {\"fields\": [\"Username\", \"Address\", \"Contact number\", \"Gender\", \"Abouts user\", \"Photo url\", \"Is active\", \"Ngo\", \"Created by\", \"Modify by\", \"Modify date\"]}}]',6,1),(93,'2024-04-20 16:41:53.457032','1','Admin',2,'[{\"changed\": {\"fields\": [\"Photo url\", \"Ngo\"]}}]',6,1),(94,'2024-04-20 19:44:00.186943','22','Sabin',3,'',6,1),(95,'2024-04-20 19:44:11.620018','34','12AK',3,'',6,1),(96,'2024-04-20 19:44:20.080070','36','Jon Mana',3,'',6,1),(97,'2024-04-20 19:44:25.957906','35','jk',3,'',6,1),(98,'2024-04-20 19:44:36.693107','33','admin@gmail.com',3,'',6,1),(99,'2024-04-20 19:44:44.380354','32','sdvjks vd',3,'',6,1),(100,'2024-04-20 19:44:49.514486','30','ksckscbs',3,'',6,1),(101,'2024-04-20 19:44:55.454105','29','Hjhvkjb',3,'',6,1),(102,'2024-04-20 19:45:10.514790','31','djkvbsjv',3,'',6,1),(103,'2024-04-20 19:45:19.671650','27','taus',3,'',6,1),(104,'2024-04-20 19:45:36.807871','26','JHcdajcbds',3,'',6,1),(105,'2024-04-20 19:46:01.224680','28','admin@gmail.com',3,'',6,1),(106,'2024-04-20 19:47:01.197442','13','Food Donor',2,'[{\"changed\": {\"fields\": [\"Last login\", \"Username\", \"Address\", \"Contact number\", \"Abouts user\", \"Ngo\"]}}]',6,1),(107,'2024-04-20 19:47:08.865036','24','admin@gmail.com',3,'',6,1),(108,'2024-04-20 19:47:13.329707','25','admin@gmail.com',3,'',6,1);
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_content_type`
--

DROP TABLE IF EXISTS `django_content_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_content_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_content_type`
--

LOCK TABLES `django_content_type` WRITE;
/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
INSERT INTO `django_content_type` VALUES (1,'admin','logentry'),(3,'auth','group'),(2,'auth','permission'),(12,'authtoken','token'),(13,'authtoken','tokenproxy'),(4,'contenttypes','contenttype'),(14,'foodshare','device'),(7,'foodshare','food'),(11,'foodshare','history'),(8,'foodshare','ngo'),(10,'foodshare','notification'),(9,'foodshare','report'),(6,'foodshare','users'),(5,'sessions','session');
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_migrations`
--

DROP TABLE IF EXISTS `django_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_migrations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'foodshare','0001_initial','2024-04-11 09:23:33.504516'),(2,'contenttypes','0001_initial','2024-04-11 09:23:33.601571'),(3,'admin','0001_initial','2024-04-11 09:23:33.973071'),(4,'admin','0002_logentry_remove_auto_add','2024-04-11 09:23:33.997793'),(5,'admin','0003_logentry_add_action_flag_choices','2024-04-11 09:23:34.028134'),(6,'contenttypes','0002_remove_content_type_name','2024-04-11 09:23:34.232132'),(7,'auth','0001_initial','2024-04-11 09:23:34.908304'),(8,'auth','0002_alter_permission_name_max_length','2024-04-11 09:23:35.057475'),(9,'auth','0003_alter_user_email_max_length','2024-04-11 09:23:35.079893'),(10,'auth','0004_alter_user_username_opts','2024-04-11 09:23:35.094789'),(11,'auth','0005_alter_user_last_login_null','2024-04-11 09:23:35.105419'),(12,'auth','0006_require_contenttypes_0002','2024-04-11 09:23:35.115087'),(13,'auth','0007_alter_validators_add_error_messages','2024-04-11 09:23:35.127624'),(14,'auth','0008_alter_user_username_max_length','2024-04-11 09:23:35.138623'),(15,'auth','0009_alter_user_last_name_max_length','2024-04-11 09:23:35.148624'),(16,'auth','0010_alter_group_name_max_length','2024-04-11 09:23:35.187662'),(17,'auth','0011_update_proxy_permissions','2024-04-11 09:23:35.206014'),(18,'auth','0012_alter_user_first_name_max_length','2024-04-11 09:23:35.221889'),(19,'authtoken','0001_initial','2024-04-11 09:23:35.481202'),(20,'authtoken','0002_auto_20160226_1747','2024-04-11 09:23:35.510513'),(21,'authtoken','0003_tokenproxy','2024-04-11 09:23:35.521025'),(22,'sessions','0001_initial','2024-04-11 09:23:35.606366'),(23,'foodshare','0002_remove_notification_report_description_and_more','2024-04-12 01:34:57.541844'),(24,'foodshare','0003_alter_users_is_active','2024-04-16 06:13:44.695149'),(25,'foodshare','0004_alter_users_is_active','2024-04-16 06:23:12.045437'),(26,'foodshare','0004_users_ngo','2024-04-17 17:53:05.532796'),(27,'foodshare','0005_rename_is_delete_notification_status_and_more','2024-04-18 02:58:10.756885'),(28,'foodshare','0006_delete_notification','2024-04-18 03:51:51.645283'),(29,'foodshare','0007_notification','2024-04-18 03:52:41.979009'),(30,'foodshare','0008_alter_ngo_established_date','2024-04-20 04:53:28.840804'),(31,'foodshare','0009_alter_users_is_active','2024-04-20 15:36:39.855656'),(32,'foodshare','0010_rename_status_notification_is_delete','2024-04-20 19:20:08.441263');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_session`
--

DROP TABLE IF EXISTS `django_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`),
  KEY `django_session_expire_date_a5c62663` (`expire_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_session`
--

LOCK TABLES `django_session` WRITE;
/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
INSERT INTO `django_session` VALUES ('f37jl8yvvf3colgszgplrfj3ne8d7cdz','.eJxVjtFugjAUht-F642UIgi7EyGmxJa56QRuDJRiCwgEUIFl776aeLHdnfz_d75zvpVTch346dqz7iQy5U3RlJe_WZrQktWPIiuS-tyotKmHTqTqA1Gfba_iJmOV82T_CXjSc7kNl7oBAEhNmGkGsEBm6TkEmpWYgNpUz-Wo24xZugbB0rIy2zTYki2ABZmV2nkupUMj7VLFJp-nGyoC4aPDjDQiUI_qD4OukYnKNvxa-7YqIZDCoUorG7DQqVDRiOhIijgkMxJ3EYf8LrORzB7A82EibgS3a7-Nwp0ICk_He6wTl0JSRD26tCD5lPLiYJDjbgzc1Yg30YK4uzG6-DzYozGaKx4XDsdHb8IQLx43spBUtLLbWD6GQ6C-dx63NX1yotu-3brkNSHlatgZdOs63qU79-3EiuRWdvVd-fkF7cCHMA:1ryi3O:dSrJPfK_cgMPHKpQu2_XlEeykyCXNfDvyoDEtTrfGUw','2024-05-06 01:01:06.854872');
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_device`
--

DROP TABLE IF EXISTS `foodshare_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_device` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` longtext,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` date NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `foodshare_device_user_id_9b1a0808_fk_foodshare_users_id` (`user_id`),
  CONSTRAINT `foodshare_device_user_id_9b1a0808_fk_foodshare_users_id` FOREIGN KEY (`user_id`) REFERENCES `foodshare_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_device`
--

LOCK TABLES `foodshare_device` WRITE;
/*!40000 ALTER TABLE `foodshare_device` DISABLE KEYS */;
INSERT INTO `foodshare_device` VALUES (1,'cVCaY0G7S9W4H_H-j8fZ6L:APA91bGmHpLnWj_EyXMv8CZBGZNMLjJac8B5wxF1ZH01WWMAhbJ__opsLif_dfGDg-TKDCgqFBEHioPwml3W98e2CcUPYEnRHGeyLqhbDwsiGHpgoxTRqthMAtNwJGa7FpqRmlNgJBb-','Donor 1khihib','2024-04-18',1,13),(5,'efi5NtR7Qz64khw2REylRx:APA91bFpaB0gbtvgg-IuuJaH6MqllZU0KTwlwwgbXZXK6ywXsU1sKfwVhPY-mOgKIPErPIQbgniGNzyPr_MHSfUg-TfAf7wZh_h76F_vyPCyKIyAra90k--_sTfz3S98yJmv7IfoAE55','volunteerrr','2024-04-20',0,14),(6,'cVCaY0G7S9W4H_H-j8fZ6L:APA91bGmHpLnWj_EyXMv8CZBGZNMLjJac8B5wxF1ZH01WWMAhbJ__opsLif_dfGDg-TKDCgqFBEHioPwml3W98e2CcUPYEnRHGeyLqhbDwsiGHpgoxTRqthMAtNwJGa7FpqRmlNgJBb-','Admin','2024-04-20',0,1),(7,'eeQP7mrtRz2yqkVJJtJ4Jc:APA91bFYbMobbmFtbsuRqUUk7xdhl9F-fJDHV7N5aat0TQbkR_YK7Zy0pdT6uMkK91nyMxImX7fwFV5U5cOt6-ILtvdPIf0SAakuCazBIZc-y1I2DdwqcZn9DWJU_7dSWH-N3pXXlHvA','test','2024-04-21',0,37),(8,'fphjv1N5QpqY5g5yVD5tPo:APA91bGrCfGStZmxvoxM-AUM_-QSVCodj0NZn9F2UDaIa7w1Dhx6jbxzaCWUVi2KmSu1m2cXueeNRlavdPDikYcfUg8U_n-JFYmyRE-ND9eNe7ju1UWCdDxqaj9hFnMRREX0ZSa6k9Oo','JK JK','2024-04-22',0,41);
/*!40000 ALTER TABLE `foodshare_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_food`
--

DROP TABLE IF EXISTS `foodshare_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_food` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `food_name` varchar(100) NOT NULL,
  `food_types` varchar(50) NOT NULL,
  `quantity` int DEFAULT NULL,
  `expire_time` varchar(10) DEFAULT NULL,
  `pick_up_location` varchar(100) NOT NULL,
  `latitude` decimal(22,16) NOT NULL,
  `longitude` decimal(22,16) NOT NULL,
  `descriptions` longtext,
  `stream_url` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` date NOT NULL,
  `modify_by` varchar(50) DEFAULT NULL,
  `modify_date` date DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `donor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foodshare_food_donor_id_7469348b_fk_foodshare_users_id` (`donor_id`),
  CONSTRAINT `foodshare_food_donor_id_7469348b_fk_foodshare_users_id` FOREIGN KEY (`donor_id`) REFERENCES `foodshare_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_food`
--

LOCK TABLES `foodshare_food` WRITE;
/*!40000 ALTER TABLE `foodshare_food` DISABLE KEYS */;
INSERT INTO `foodshare_food` VALUES (40,'Mo:Mo','Others',5,'10:25 PM','Baluwatar',0.0000000000000000,0.0000000000000000,'Momos are a type of steamed filled dumpling in Tibetan and Nepali cuisine that is also popular in neighbouring Bhutan and India. Momos are usually served with a sauce known as achar influenced by the spices and herbs used within many South Asian cuisines.','C:\\Documents\\22015892_SITARAM_THING_FYP\\Developments\\Backend\\Django\\media\\food_images\\1000006144.png','New','Donor','2024-04-16','Self','2024-04-17',1,13),(41,'Pizza','Others',1,'00:00','Kathmandu',0.0000000000000000,0.0000000000000000,'THE RESTAURANT IMMEDIATELY BECAME A VERY POPULAR DESTINATION FOR TRAVELLERS, MOUNTAINEERS AND LOCAL RESIDENTS ALIKE TO SIT BACK AND ENJOY A MORNING CUP OF ITALIAN ESPRESSO, OR LATER IN THE DAY, TO SAVOUR A LITTLE BIT OF ITALY WITH DELICIOUS PIZZAS, PASTAS, ORGANIC SALADS AND DESSERTS WHILST SHARING STORIES AND ADVENTURE TALES WITH FRIENDS.','food_images/Pizza.jpg','Pending','Donor','2024-04-16','volunteer','2024-04-16',1,13),(42,'Dal and Vat','Others',3,'07:32 PM','Baluwatar',0.0000000000000000,0.0000000000000000,'Considered the national dish, Dal Bhat is a staple meal comprising lentil soup (dal) paired with steamed rice (bhat).','food_images/d.jpg','Pending','Donor','2024-04-16','volunteerrr','2024-04-16',1,13),(43,'Milk and Coffee','Others',13,'11:38 PM','Kathmandu',27.7268186000000000,85.3308143000000000,'Benefits Of Drinking Coffee With Milk - Experts Explain It AllOne of the most common queries remains - should we add milk to our cup of coffee? Well, we finally found an answer to it. Read on to know more.Somdatta SahaUpdated: January 31, 2023 18:23 ISTRead Time:2 min\r\n\r\nBenefits Of Drinking Coffee With Milk - Experts Explain It All\r\nCoffee may help prevent inflammationiStockHighlightsCoffee is a popular beverage across the globe.We consume coffee on a regular basis.Here we bust a myth in regard to brewing coffee.\r\nCoffee is one of the most popular beverages across the globe. However, it also brings several myths and confusions along.','food_images/milk.png','New','Donor','2024-04-16','Admin','2024-04-16',0,13),(44,'Water and Cold Dring','Others',4,'00:41 PM','Balawatar',27.7268213000000000,85.3308446000000000,'There is little scientific evidence to suggest that drinking cold water is bad for people. In fact, drinking colder water may improve exercise performance and be better for rehydration when exercising, especially in hotter environments.','food_images/water.jpg','New','Donor','2024-04-16','Donor','2024-04-16',0,13),(45,'Cake','Others',14,'06:44 PM','Kathmandu',27.7268202000000000,85.3308560000000000,'This is a really easy yet exceptional Chocolate Cake that truly tastes of chocolate. The crumb is beautifully tender and moist, and it keeps for up to 5 days','food_images/cake.jpg','Pending','Donor','2024-04-16','volunteerrr','2024-04-16',0,13),(46,'momo','Others',2,'00:00','kamalpokhari',0.0000000000000000,0.0000000000000000,'jsjs','food_images/image.jpg','Completed','Ram Nepali','2024-04-16','volunteerrr','2024-04-16',0,15),(47,'New food Test','Others',5,'09:59 PM','Kathmandu',27.7268253000000000,85.3307940000000000,'App test from pratik','food_images/image_90NEdj5.jpg','Completed','Pratik Dai','2024-04-16','volunteer','2024-04-16',0,16),(48,'Food Testing 1','Others',8,'09:04 PM','Kathamandus',27.7268094000000000,85.3308310000000000,'This is the new food test','food_images/image_Y1NAOlX.jpg','Pending','Donor','2024-04-16','volunteerrr','2024-04-16',0,13),(49,'new food','Others',3,'10:06 PM','jeh',27.7268362000000000,85.3308041000000000,'jshdhd','food_images/image_AvKGtuI.jpg','Completed','Donor','2024-04-16','Donor 1','2024-04-16',1,13),(51,'momo','Others',2,'00:00','ktm',27.7078483000000000,85.3247882000000000,'mabsh','food_images/1000023478.png','New','laxman','2024-04-17',NULL,NULL,1,21),(52,'Laxman laxman','Others',4,'10:25 PM','Ktm',27.7078434000000000,85.3248267000000000,'Hello doxt','food_images/1000006144_XyKuRUv.png','Completed','Donor 1khihib','2024-04-17','Food Donor','2024-04-21',1,13),(53,'coke','Others',1,'00:00','ktm',27.7078144000000000,85.3249258000000000,'abc','food_images/1000023478_SYXJTR3.png','New','laxman','2024-04-17','laxman','2024-04-17',1,21),(54,'Testing food','Others',1,'00:00','Ktm',27.7078342000000000,85.3248365000000000,'Testing food','food_images/1000006144_fTxiDKE.png','New','Donor 1khihib','2024-04-18',NULL,NULL,0,13),(55,'bvhvuc','Others',5,'00:00','kbibi',27.7078344000000000,85.3247844000000000,'iguvuvv','food_images/2f180367-68ed-4b23-b7ae-35a20b884687-1_all_2890.png','New','Donor 1khihib','2024-04-18','Food Donor','2024-04-21',1,13),(56,'ndbdbdbznsbbe','Others',2,'09:33 AM','bdbxh',27.7078358000000000,85.3247871000000000,'xbdbbdv','food_images/1000006223.png','Pending','Donor 1khihib','2024-04-18','test','2024-04-21',0,13),(57,'jsnehd','Others',4,'09:41 AM','ksjdhz',27.7078426000000000,85.3247966000000000,'b,hsbsvsv','food_images/1000006223_9zQEdqw.png','New','Donor 1khihib','2024-04-18','Food Donor','2024-04-21',1,13),(58,'sjdjd','Others',4,'09:53 AM','jzhxbdg',0.0000000000000000,0.0000000000000000,'bbsb','food_images/2f180367-68ed-4b23-b7ae-35a20b884687-1_all_2890_c5HtUbK.png','New','Donor 1khihib','2024-04-18','Food Donor','2024-04-21',1,13),(59,'Nesting box','Others',4,'09:59 AM','jsjdhdh',27.7078977000000000,85.3251996000000000,'kdjd','food_images/2f180367-68ed-4b23-b7ae-35a20b884687-1_all_2890_AlL7x8h.png','New','Donor 1khihib','2024-04-18','Food Donor','2024-04-21',1,13),(60,'Hello bro','Others',4,'08:01 AM','hehdhh',27.7078408000000000,85.3248487000000000,'jdhdb','food_images/2f180367-68ed-4b23-b7ae-35a20b884687-1_all_2890_AEGBTfy.png','New','Donor 1khihib','2024-04-18','Food Donor','2024-04-21',1,13),(61,'hshsb','Others',1,'00:00','jdjd',27.7078235000000000,85.3248724000000000,'ddnhdb','food_images/2f180367-68ed-4b23-b7ae-35a20b884687-1_all_2890_jTZLfOi.png','New','Donor 1khihib','2024-04-18','Food Donor','2024-04-21',1,13),(62,'Testing notice','Others',4,'09:37 PM','Kathamandus',0.0000000000000000,0.0000000000000000,'new notification','food_images/1000006144_JcWEOUg.png','New','Donor 1khihib','2024-04-18',NULL,NULL,0,13),(66,'Foosjv','Others',4,'07:56 AM','igohohibib',0.0000000000000000,0.0000000000000000,'n j j j hvv','food_images/1000006243_VW5WD5d.png','New','Donor 1khihib','2024-04-20','Food Donor','2024-04-21',1,13),(67,'jvibiv','Others',1,'00:00','uviig',27.7078257000000000,85.3248142000000000,'nvjvuv','','New','Donor 1khihib','2024-04-20','Food Donor','2024-04-21',1,13),(68,'New Food Updated foyiu','Others',11,'09:07 AM','Kamalpokhari kathmandu',27.7078773000000000,85.3249663000000000,'Also available','food_images/1000006144_LlE0pLm.png','Completed','Food Donor','2024-04-21','test','2024-04-21',0,13),(69,'Food donatw','Others',3,'00:00','djsshthe 5 the 5é',27.7078423000000000,85.3247852000000000,'food donate','food_images/1000006144_iqVqY8A.png','New','Food Donor','2024-04-21','Food Donor','2024-04-21',1,13),(70,'Sanikan New food donations','Others',4,'10:11 PM','Kathamandus',27.7082993000000000,85.3243467000000000,'This food is for sanikan doxt','food_images/1000006144_GstNHay.png','New','Food Donor','2024-04-21',NULL,NULL,0,13);
/*!40000 ALTER TABLE `foodshare_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_history`
--

DROP TABLE IF EXISTS `foodshare_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descriptions` longtext,
  `distributed_location` varchar(100) DEFAULT NULL,
  `rating_point` int NOT NULL,
  `distributed_date` date DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_date` date NOT NULL,
  `modify_by` varchar(50) DEFAULT NULL,
  `modify_date` date DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `food_id` bigint DEFAULT NULL,
  `volunteer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foodshare_history_food_id_846cbaa2_fk_foodshare_food_id` (`food_id`),
  KEY `foodshare_history_volunteer_id_614cf90b_fk_foodshare_users_id` (`volunteer_id`),
  CONSTRAINT `foodshare_history_food_id_846cbaa2_fk_foodshare_food_id` FOREIGN KEY (`food_id`) REFERENCES `foodshare_food` (`id`),
  CONSTRAINT `foodshare_history_volunteer_id_614cf90b_fk_foodshare_users_id` FOREIGN KEY (`volunteer_id`) REFERENCES `foodshare_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_history`
--

LOCK TABLES `foodshare_history` WRITE;
/*!40000 ALTER TABLE `foodshare_history` DISABLE KEYS */;
INSERT INTO `foodshare_history` VALUES (30,NULL,NULL,0,NULL,'Pending','volunteer','2024-04-16',NULL,NULL,0,41,14),(31,'The food is successfully distributed.','Puthali sadak',5,'2024-04-16','Completed','volunteer','2024-04-16',NULL,NULL,0,47,14),(33,NULL,NULL,0,NULL,'Pending','volunteerrr','2024-04-16',NULL,NULL,0,45,14),(34,'The food is successfully distributed.','jzjdh',3,'2024-04-18','Completed','volunteerrr','2024-04-16',NULL,NULL,0,49,14),(35,NULL,NULL,0,NULL,'Pending','volunteerrr','2024-04-16',NULL,NULL,0,42,14),(36,NULL,NULL,0,NULL,'Pending','volunteerrr','2024-04-16',NULL,NULL,0,48,14),(37,'The food is successfully distributed.','MoMo',5,'2024-04-18','Completed','volunteerrr','2024-04-16','volunteerrr','2024-04-20',1,46,14),(38,'The food is successfully distributed.','ubub,,j',3,'2024-04-17','Completed','volunteerrr','2024-04-17',NULL,NULL,0,52,14),(39,'The food is susvsbsvccessfully distributed.','fhfjrhdh',3,'2024-04-21','Completed','test','2024-04-21',NULL,NULL,0,68,37),(40,NULL,NULL,0,NULL,'Pending','test','2024-04-21',NULL,NULL,0,56,37);
/*!40000 ALTER TABLE `foodshare_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_ngo`
--

DROP TABLE IF EXISTS `foodshare_ngo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_ngo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ngo_name` varchar(100) NOT NULL,
  `ngo_email` varchar(50) NOT NULL,
  `ngo_location` varchar(100) DEFAULT NULL,
  `ngo_contact` varchar(15) NOT NULL,
  `established_date` date NOT NULL,
  `abouts_ngo` longtext,
  `ngo_stream_url` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` date NOT NULL,
  `modify_by` varchar(50) DEFAULT NULL,
  `modify_date` date DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ngo_name` (`ngo_name`),
  UNIQUE KEY `ngo_email` (`ngo_email`),
  UNIQUE KEY `ngo_contact` (`ngo_contact`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_ngo`
--

LOCK TABLES `foodshare_ngo` WRITE;
/*!40000 ALTER TABLE `foodshare_ngo` DISABLE KEYS */;
INSERT INTO `foodshare_ngo` VALUES (1,'ING Food','ingfood@gmail.com','Kamalpokhari, Kathmandu, Nepal','9801042242','2018-02-15','We also harness the power of a wide range of small-scale companies that work in cohesion to bolster our common goal of empowering Nepal.','food_images/ing_food.jpg','Sitaram Thing','2024-04-13','Sitaram Thing','2024-04-13',0);
/*!40000 ALTER TABLE `foodshare_ngo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_notification`
--

DROP TABLE IF EXISTS `foodshare_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` longtext,
  `descriptions` longtext,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` date NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `food_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `foodshare_notification_food_id_5c9f5077_fk_foodshare_food_id` (`food_id`),
  CONSTRAINT `foodshare_notification_food_id_5c9f5077_fk_foodshare_food_id` FOREIGN KEY (`food_id`) REFERENCES `foodshare_food` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_notification`
--

LOCK TABLES `foodshare_notification` WRITE;
/*!40000 ALTER TABLE `foodshare_notification` DISABLE KEYS */;
INSERT INTO `foodshare_notification` VALUES (1,'jsnehd','b,hsbsvsv','Donor 1khihib','2024-04-18',0,57),(2,'sjdjd','bbsb','Donor 1khihib','2024-04-18',1,58),(3,'Nesting box','kdjd','Donor 1khihib','2024-04-18',0,59),(4,'Hello bro','jdhdb','Donor 1khihib','2024-04-18',0,60),(5,'hshsb','ddnhdb','Donor 1khihib','2024-04-18',0,61),(6,'Testing notice','new notification','Donor 1khihib','2024-04-18',0,62),(10,'Foosjv','n j j j hvv','Donor 1khihib','2024-04-20',1,66),(11,'jvibiv','nvjvuv','Donor 1khihib','2024-04-20',1,67),(12,'New Food Updated foyiu','Also available','Food Donor','2024-04-21',0,68),(13,'Food donatw','food donate','Food Donor','2024-04-21',0,69),(14,'Sanikan New food donations','This food is for sanikan doxt','Food Donor','2024-04-21',0,70);
/*!40000 ALTER TABLE `foodshare_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_report`
--

DROP TABLE IF EXISTS `foodshare_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `complaint_to` varchar(30) DEFAULT NULL,
  `descriptions` longtext,
  `is_verify` tinyint(1) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_date` date NOT NULL,
  `modify_by` varchar(50) DEFAULT NULL,
  `modify_date` date DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `food_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `foodshare_report_food_id_5f6b51d8_fk_foodshare_food_id` (`food_id`),
  CONSTRAINT `foodshare_report_food_id_5f6b51d8_fk_foodshare_food_id` FOREIGN KEY (`food_id`) REFERENCES `foodshare_food` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_report`
--

LOCK TABLES `foodshare_report` WRITE;
/*!40000 ALTER TABLE `foodshare_report` DISABLE KEYS */;
INSERT INTO `foodshare_report` VALUES (16,'volunteer','not received food',1,'Donor 1','2024-04-16','Admin','2024-04-17',0,42),(17,'volunteer','report assignment rest test',1,'Donor 1khihib','2024-04-17','Admin','2024-04-17',1,48),(18,'donor','jbubhy',0,'volunteerrr','2024-04-17',NULL,NULL,1,49);
/*!40000 ALTER TABLE `foodshare_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodshare_users`
--

DROP TABLE IF EXISTS `foodshare_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodshare_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(128) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `role` varchar(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `contact_number` varchar(16) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `abouts_user` longtext,
  `photo_url` varchar(500) DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` date NOT NULL,
  `modify_by` varchar(50) DEFAULT NULL,
  `modify_date` date DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `ngo_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `contact_number` (`contact_number`),
  KEY `foodshare_users_ngo_id_35de25ec_fk_foodshare_ngo_id` (`ngo_id`),
  CONSTRAINT `foodshare_users_ngo_id_35de25ec_fk_foodshare_ngo_id` FOREIGN KEY (`ngo_id`) REFERENCES `foodshare_ngo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodshare_users`
--

LOCK TABLES `foodshare_users` WRITE;
/*!40000 ALTER TABLE `foodshare_users` DISABLE KEYS */;
INSERT INTO `foodshare_users` VALUES (1,'pbkdf2_sha256$600000$B2JmI5GcYLmMlgWkZhkqr1$B+J4aR2GdVg0dS/aiDJFw3viU7QjRtue62ZBCYp4AFA=','2024-04-22 01:01:06.721075','admin@gmail.com','Admin','admin','Kathamandus','9828978461','Others','2024-04-11','We are administrator of food donations system.','user_images/android_b596965c863740698a5da86b7fd1ebd8_175695__6ff399f0-2c84-4c4f-93be-11a00ecb6909_175695_.jpg',1,1,'Self','2024-04-11','Admin','2024-04-11',0,1),(13,'pbkdf2_sha256$600000$ofiBBf1g7nVjWtaabzzYQL$d99Ogb+fOwsXuVI60/2rEDUwD1iwNKRfmApxcTJbwgM=','2024-04-20 19:46:42.000000','donor@gmail.com','Food Donor','Donor','Admin','9800000000','Others','2024-04-16','I am a food donor.','C:\\Documents\\22015892_SITARAM_THING_FYP\\Developments\\Backend\\Django\\media\\user_images\\1000006108.png',0,1,'Self','2024-04-16','Admin','2024-04-20',0,1),(14,'pbkdf2_sha256$600000$KgsmARS71tlcoregjVgIVv$lAOC/3MYIDBF0CgvS7UBa2kV03J3BD/2pdIF2OEbPU0=',NULL,'volunteer@gmail.com','volunteerrr','Volunteer','dvdv','987654321','Others','2024-04-16','sdvsdv','C:\\Documents\\22015892_SITARAM_THING_FYP\\Developments\\Backend\\Django\\media\\user_images\\image.jpg',0,1,'Self','2024-04-16','volunteerrr','2024-04-16',0,1),(15,'pbkdf2_sha256$600000$Vts0qSyhRshKISyKqp1VeA$yjJTdEx5cqlIe1j0L7VF34z1QNypUDrvoC7dDtjHBuc=',NULL,'pariyarram@gmail.com','Ram Nepali','Donor',NULL,NULL,NULL,'2024-04-16',NULL,'C:\\Documents\\22015892_SITARAM_THING_FYP\\Developments\\Backend\\Django\\media\\user_images\\IMG_2517.JPG',0,1,'Self','2024-04-16','Self','2024-04-17',1,NULL),(16,'pbkdf2_sha256$600000$cPsmdHm80P3ELNmzLI1YlJ$26WGORUbM6NST4G/ssiOPhY/+rUUO086H8z48E9QoZw=',NULL,'pratik@gmail.com','Pratik Dai','Donor',NULL,NULL,NULL,'2024-04-16',NULL,'',0,1,'Self','2024-04-16',NULL,NULL,1,NULL),(21,'pbkdf2_sha256$600000$HXjNnEOc5NI3KGfFGlTC8k$4bDDldiY+9SimLTiwRxjeyeyXtknt+r/wz6rR6ITiQI=',NULL,'n@gmail.com','laxman','Donor','Manabg','123456','Male','2024-04-17','adad','user_images/img_map.png',0,1,'Self','2024-04-17','Admin','2024-04-20',0,NULL),(37,'pbkdf2_sha256$600000$5XZTIIdtiezrZNuUXbEho3$Ne84EPpoNKwcAKNAQCMhpb14i0ONfoMZ0j8iilMKRyY=',NULL,'test@test.com','test','Volunteer',NULL,NULL,NULL,'2024-04-21',NULL,'',0,1,'Self','2024-04-21',NULL,NULL,1,NULL),(38,'pbkdf2_sha256$600000$gHbkPtyh6HRFkYT8BadJ3m$SicphfNvOjyIokmVIRM6plu6wjinLsJv4FSUv5ffEy8=',NULL,'daimond@gmail.com','Daimand Nepal','donor',NULL,NULL,NULL,'2024-04-22',NULL,'',0,1,'Self','2024-04-22',NULL,NULL,0,NULL),(39,'pbkdf2_sha256$600000$JeV5hF6qs2LIZYeTQh3ah8$wVG56by/OIpSQJeQo+OkBmlPkfY0dDBKgnD+qBrZQas=',NULL,'sita@gmail.com','Sita','volunteer',NULL,NULL,NULL,'2024-04-22',NULL,'',0,0,'Self','2024-04-22',NULL,NULL,0,NULL),(40,'pbkdf2_sha256$600000$FImQYcBLxEcaoDZXSL0E4z$gChBrzuVNTH9lXxFDilv1MmaSPfnBh/EFSoLONvpVIY=',NULL,'ak@gmail.com','Ak','donor',NULL,NULL,NULL,'2024-04-22',NULL,'',0,0,'Self','2024-04-22',NULL,NULL,1,NULL),(41,'pbkdf2_sha256$600000$CIns4QNeP0EgyZOQWrVAY0$jiyOolLDZGrNWplIax4Zao4Ombd/8WPG0UR0/pSt4uw=',NULL,'jj@gmail.com','JK JK','volunteer',NULL,NULL,NULL,'2024-04-22',NULL,'',0,1,'Self','2024-04-22',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `foodshare_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-22 12:02:40
