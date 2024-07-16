-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: aws_communication_app
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `ec2_instances`
--

DROP TABLE IF EXISTS `ec2_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ec2_instances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `instance_id` varchar(20) DEFAULT NULL,
  `instance_type` varchar(20) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `launch_time` timestamp NULL DEFAULT NULL,
  `private_ip` varchar(15) DEFAULT NULL,
  `public_ip` varchar(15) DEFAULT NULL,
  `availability_zone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ec2_instances`
--

LOCK TABLES `ec2_instances` WRITE;
/*!40000 ALTER TABLE `ec2_instances` DISABLE KEYS */;
INSERT INTO `ec2_instances` VALUES (1,'i-017e6519856f51d35','t2.micro','stopped','2023-11-16 14:12:24','172.31.1.30',NULL,'ap-south-1b'),(2,'i-0d84e2f42c1069be6','t2.micro','stopped','2023-11-16 14:12:24','172.31.3.7',NULL,'ap-south-1b'),(3,'i-04f8748ebcc51cf75','t2.micro','stopped','2023-11-16 14:12:24','172.31.2.151',NULL,'ap-south-1b'),(4,'i-049ba798a61b88a0b','t2.micro','stopped','2023-11-16 14:12:24','172.31.12.183',NULL,'ap-south-1b'),(5,'i-01d5daae2fbe5ed9d','t2.micro','stopped','2023-11-16 14:12:24','172.31.12.236',NULL,'ap-south-1b');
/*!40000 ALTER TABLE `ec2_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` text,
  `aws_services_type` text,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'975e53f6-a797-4655-b9b8-d0c6ca8383d1',NULL,'In Progress'),(2,'7da80bf3-656a-4881-a769-560a6d7bacf5','S3','Success'),(3,'320affda-3694-4432-b24a-ee6be3a53fb1','EC2','Success'),(4,'1b73292f-cb0e-4903-872e-303a45e71a0a','nimesaassignmentbucket1','In Progress'),(5,'09357295-e04f-4154-bd20-b13901c52889','nimesaassignmentbucket1','Success'),(6,'2c95bfe4-3c95-4bfc-b035-8507e1764eba','nimesaassignmentbucket2','In Progress'),(7,'ceeed2bb-f511-4014-afb8-d5cd4714444d','nimesaassignmentbucket2','Success'),(8,'8712696d-8e0b-4d9b-bb5d-b2cebe95bfb2','nimesaassignmentbucket3','In Progress'),(9,'20f45813-e3d1-491d-a10a-ba9ca7d15546','nimesaassignmentbucket3','In Progress'),(10,'20d45a77-7be2-4afb-a8c8-059170df714e','nimesaassignmentbucket3','In Progress'),(11,'0e9f1de9-9571-4cf1-b3d8-df130e1e9086','nimesaassignmentbucket4','In Progress');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s3_buckets`
--

DROP TABLE IF EXISTS `s3_buckets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `s3_buckets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `creation_date` datetime NOT NULL,
  `owner_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s3_buckets`
--

LOCK TABLES `s3_buckets` WRITE;
/*!40000 ALTER TABLE `s3_buckets` DISABLE KEYS */;
INSERT INTO `s3_buckets` VALUES (1,'nimesaassignmentbucket1','2023-11-16 19:43:27','e59d7b9c7a7599a888b37755182c7fbbb8fda44498b6a6be5462b96b3ebf0992'),(2,'nimesaassignmentbucket2','2023-11-16 19:43:39','e59d7b9c7a7599a888b37755182c7fbbb8fda44498b6a6be5462b96b3ebf0992'),(3,'nimesaassignmentbucket3','2023-11-16 19:44:08','e59d7b9c7a7599a888b37755182c7fbbb8fda44498b6a6be5462b96b3ebf0992'),(4,'nimesaassignmentbucket4','2023-11-16 19:44:43','e59d7b9c7a7599a888b37755182c7fbbb8fda44498b6a6be5462b96b3ebf0992');
/*!40000 ALTER TABLE `s3_buckets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s3_objects`
--

DROP TABLE IF EXISTS `s3_objects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `s3_objects` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bucket_name` varchar(255) DEFAULT NULL,
  `object_key` varchar(1024) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s3_objects`
--

LOCK TABLES `s3_objects` WRITE;
/*!40000 ALTER TABLE `s3_objects` DISABLE KEYS */;
INSERT INTO `s3_objects` VALUES (1,'nimesaassignmentbucket1','New folder/MQL5 Source File.mq5',0),(2,'nimesaassignmentbucket1','New folder/Sample Image.bmp',0),(3,'nimesaassignmentbucket1','New folder/Test-File - 2.txt',0),(4,'nimesaassignmentbucket1','New folder/Test-File - 3.txt',0),(5,'nimesaassignmentbucket1','New folder/Test-File - 4.txt',0),(6,'nimesaassignmentbucket1','New folder/Test-File.txt',0),(7,'nimesaassignmentbucket2','New folder/New Microsoft Excel Worksheet.xlsx',6185),(8,'nimesaassignmentbucket2','New folder/New Microsoft PowerPoint Presentation.pptx',0),(9,'nimesaassignmentbucket2','New folder/New Microsoft Publisher Document.pub',59904),(10,'nimesaassignmentbucket2','New folder/New Microsoft Word Document.docx',0);
/*!40000 ALTER TABLE `s3_objects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'aws_communication_app'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-17  1:53:54
