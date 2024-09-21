-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: springdb
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `local_user`
--

DROP TABLE IF EXISTS `local_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `local_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(320) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `refresh_token` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK46f7ufu7j9nkhuyfly98to4u1` (`email`),
  UNIQUE KEY `UK93d93k106ik2383youkc9bixl` (`username`),
  KEY `FK9f2k1acdlkq3tx4iy5uv9hqji` (`role_id`),
  CONSTRAINT `FK9f2k1acdlkq3tx4iy5uv9hqji` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local_user`
--

LOCK TABLES `local_user` WRITE;
/*!40000 ALTER TABLE `local_user` DISABLE KEYS */;
INSERT INTO `local_user` VALUES (11,'test3@gmail.com','$2a$10$9JywMoYwU.dzqFwdnrQk0.9KIh2vgiHLg0OjNFrH.QokK5MrUI2eW','test3',2,NULL),(14,'dong@gmail.com','$2a$10$JzCfqlGIxHW7FbRKcjhxo.e/1sV7h0DVrzKumkjweLbreadHaCETi','dong',2,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25nIiwiZXhwIjoxNzMzOTg2MjYwLCJpYXQiOjE3MjUzNDYyNjAsIkFVVEhPUklUWSI6IlJPTEVfVVNFUiJ9.EhUPw3cqqOdZmR5AYIslPFcYcNBCfux_RAnw_KMQZRQ'),(15,'admin@gmail.com','$2a$10$qXPeYhlL4JE3xP6dPTpXOuNbwdMX9/l2mAMOoj54CGYG1/ddhclze','admin',1,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczMzk4NjAxMCwiaWF0IjoxNzI1MzQ2MDEwLCJBVVRIT1JJVFkiOiJST0xFX0FETUlOIn0.iUNzuVUIg1HKq3EZJygx8Q-7BBG1XOhylLrZ2Qv7WsA');
/*!40000 ALTER TABLE `local_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-21 22:36:06
