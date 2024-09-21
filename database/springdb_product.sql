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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `sub_category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjmivyxk9rmgysrmsqw15lqr5b` (`name`),
  KEY `FKd9gfnsjgfwjtaxl57udrbocsp` (`sub_category_id`),
  CONSTRAINT `FKd9gfnsjgfwjtaxl57udrbocsp` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (32,'Áo thun tay ngắn S.Cafe Routine x Brown','https://routine.vn/media/amasty/amoptmobile/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-tay-ngan-08-10f24tss061_brown_rice_1__1_1_jpg.webp',397000,2),(33,'Áo thun tay ngắn phối nhãn trang trí','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-05-10f23tss014r1_mint_1__2_1_jpg.webp',372000,2),(34,'Áo thun nam. Loose','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-27-10s24tss018_1__1_1_jpg.webp',392000,2),(35,'Áo Thun Tay Ngắn Unisex Cotton In Hình Form Loose','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-unisex-22-10s24tssu006_black-_2__1_1_jpg.webp',392000,2),(36,'Áo Thun Nam Tay Ngắn Premium Jacquard Trơn Form Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-tay-ngan-22-10s24tss037p_black-_1__1_1_jpg.webp',441000,2),(37,'Áo Thun Nam Tay Ngắn Cổ Tròn Phối Rib Trơn Form Loose','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-tay-phoi-13-10s24tss011_black-_5__1_1_jpg.webp',441000,2),(38,'Áo Thun Nam Tay Ngắn Sọc Ngang Cotton Pique Form Loose','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-02-10s24tss012_forest-biome-_1__1_jpg.webp',441000,2),(39,'Áo Thun Nam Tay Ngắn Cotton Cổ Tròn In Hình Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-10s23tss017-rubber-_1__2_jpg.webp',392000,2),(40,'Áo Thun Nam Tay Ngắn Họa Tiết Thêu Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-18-10s23tss019-bright-white_1__1_jpg.webp',392000,2),(41,'Áo Thun Nam Tay Ngắn Carbon In Họa Tiết Form Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nam-10f22tss014_black_beauty_1__1_1_jpg.webp',247000,2),(42,'Áo Polo Nam Tay Ngắn Cotton Pique Phối Màu Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-polo-nam-6-10s24pol008_agave-green-_1__1_1_jpg.webp',471000,1),(43,'Áo Polo Nam Rút Dây Phối Túi Dây Kéo Form Regular','https://routine.vn/media/amasty/amoptmobile/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-polo-nam-29-10f23pol034_beige-_1__1_jpg.webp',471000,1),(44,'Áo Polo Nam Interlock Pique Phối Bo Và Tay Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/1/0/10s23pol037_rain_forest-ao-polo-nam_17_1__2_1_jpg.webp',471000,1),(45,'Áo Polo Nam Tay Ngắn Phối Sọc Form Fitted','https://routine.vn/media/amasty/amoptmobile/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/1/0/10s23pol060_blue_fog_1__1_1_jpg.webp',471000,1),(46,'Áo Polo Nam Tay Ngắn Họa Tiết In Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/1/0/10s23pol026_bright_white-ao-polo-nam-17_1__1_jpg.webp',308000,1),(47,'Áo Polo Nam Premium Cổ V Trơn Form Slim','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-polo-nam-10s23pol047_black_2__1_1_jpg.webp',569000,1),(49,'Áo Polo Nam Tay Bo Trơn Form Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-polo-nam-10s21pol033cr1_crocodile_88_1__1_jpg.webp',569000,1),(50,'Áo Polo Nam Tay Bo Họa Tiết Trang Trí Form Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/1/0/10s23pol029_tinsel-ao-polo-nam_1__1_jpg.webp',249000,1),(51,'Áo Thun Nữ Tay Ngắn Trơn 100% Cotton Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nu-15-10s24tssw001_tea-_1__1_1_jpg.webp',245000,4),(52,'Áo Thun Nữ Tay Lỡ Cotton Cổ Cao Sọc Gân Form Slim','https://routine.vn/media/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nu-14-10f23tssw006-beige-_1__2_1.jpg',324000,4),(54,'Áo Polo Nữ Lửng Tay Ngắn Cotton Trơn Form Regular Cropped','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-polo-nu-13-10f23polw004-rose-smoke-_1__1_1_jpg.webp',424000,4),(55,'Áo Polo Nữ Lửng Tay Dài Cotton Trơn Form Fitted Cropped','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-polo-nu-13-10f23polw005-bright-white-_1__2_1_jpg.webp',441000,4),(56,'Áo Thun Tay Dài Nữ Vải Jacquard Trơn Form Fitted','https://routine.vn/media/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/1/0/10s23tslw001-white-alyssum-ao-thun-nu_1__1_1.jpg',441000,4),(57,'Áo Thun Nữ Tay Ngắn Trơn Vải Dệt Lỗ Form Slim','https://routine.vn/media/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/1/0/10s23tssw010-white-alyssum-ao-thun-nu_1__7_1.jpg',441000,4),(58,'Áo Thun Nữ Lửng Tay Ngắn In Hình Phối Chỉ Form Fitted Crop','https://routine.vn/media/amasty/amoptmobile/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-thun-nu-10s23tssw013_black_beauty-_3__2.jpg',226000,4),(59,'Áo Sơ Mi Nữ Sát Nách Trơn Dây Rút Vai Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-11-10f23shsw006_black_1__2_1_jpg.webp',226000,5),(60,'Áo Sơ Mi Nữ Tay Dài Polyester Trơn Form Relax','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-11-10f23shlw009-black-_1__1_1_jpg.webp',520000,5),(61,'Áo Sơ Mi Nữ Lửng Kẻ Sọc Xếp Ly Lai Form Regular Cropped','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-10-10f23shsw001-green-_1__1_1_jpg.webp',392000,5),(62,'Áo Sơ Mi Nữ Tay Dài Nhún Sau Rút Dây Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-10s23shlw004-celery-green_3__1_1_jpg.webp',592000,5),(63,'Áo Sơ Mi Nữ Tay Dài Trơn Cổ Bẻ Xẻ Sau Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-10s23shlw006_blue_charm_strp_1__2_1_jpg.webp',592000,5),(64,'Áo Sơ Mi Nữ Tay Dài Satin Phối Vải Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-10s23shlw022-black-_2__2_jpg.webp',542000,5),(65,'Áo Sơ Mi Nữ Tay Dài Satin Cổ Đức Trơn Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-10s23shlw023-black-_2__3_jpg.webp',542000,5),(66,'Áo Sơ Mi Nữ Viscose Tay Dài Trơn Xẻ Sau Form Loose','https://routine.vn/media/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nu-10f22shlw010-rifle-green-_2__2.jpg',522000,5),(69,' Áo sơ mi sọc dài tay có thêu. Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nam-10s24shl022_blue-_1__1_1_jpg.webp',540000,3),(70,'Áo sơ mi sọc ngắn tay. Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nam-tay-ngan-26-10s24shs010_grey-_1__1_jpg.webp',441000,3),(71,'Áo sơ mi nam dài tay. Fitted','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nam-8-10s24shl006_beige_1__1_jpg.webp',441000,3),(72,'Áo Sơ Mi Nam Tay Ngắn Cotton Túi Đắp Trơn Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nam-30-10s24shs002_green-_1__1_jpg.webp',491000,3),(73,'Áo Sơ Mi Denim Nam Tay Ngắn Thêu Chữ Form Regular','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-nam-denim-2-10s24dsh001_d_grey_1__2_jpg.webp',540000,3),(74,'Áo Sơ Mi Jean Unisex 100% Cotton Trơn Form Loose','https://routine.vn/media/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-jean-unisex-16-10s24dshu001_d-indigo-_1__2.jpg',540000,3),(75,'Áo Sơ Mi Nam Tay Dài Flannel Túi Đắp Kẻ Caro Form Regular','https://routine.vn/media/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-15-10f23shl029-navy-_1__2_1.jpg',589000,3),(76,' Áo Sơ Mi Nam Tay Dài Flannel Túi Đắp Kẻ Caro Form Oversize','https://routine.vn/media/amasty/webp/catalog/product/cache/5de180fdba0e830d350bd2803a0413e8/a/o/ao-so-mi-15-10f23shl030-green-_1__1_1_jpg.webp',589000,3),(77,' Áo Sơ Mi Nam Tay Dài Flannel Kẻ Caro Thêu Chữ Form Loos','https://routine.vn/media/amasty/webp/catalog/product/cache/5b5632a96492396f42c72e22fdd64763/a/o/ao-so-mi-15-10f23shl035-green-_1__1_1_jpg.webp',650000,3);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-21 22:36:05
