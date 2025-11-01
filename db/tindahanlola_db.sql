-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: tindahanlola_db
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (9,'Bingo','/uploads/1761740094268_download.jpg','Bingo',8,'Biscuits'),(10,'sunsilk','/uploads/1761740052117_sunsilk-removebg-preview.png','sunsilk',7,'Shampoo'),(11,'Sardines','/uploads/1761740152076_Mega-Sardines-in-Tomato-sauce-155g-1.jpg','Mega',27,'Canned Goods'),(16,'Mismo','/uploads/1761903267930_cokke_mismo-removebg-preview.png','Coke Mismo',25,'Drinks'),(17,'1.5','/uploads/1761903300246_coke_1_5-removebg-preview.png','Coke 1.5',75,'Drinks'),(18,'Light','/uploads/1761903589938_alfonso.jpg','Alfonso',350,'Liquor'),(19,'1pc','/uploads/1761905874730_rd.jpg','RedHorse',120,'Liquor'),(20,'1 case','/uploads/1761906847316_rd_case-removebg-preview.png','Red Horse 1 case',720,'Liquor'),(21,'fudgee bars','/uploads/1761906245923_fudgeebar.jpg','Fudgee bars',8,'Biscuits'),(22,'Mismo','/uploads/1761906513361_Sprite.jpg','Sprite',25,'Drinks'),(23,'small','/uploads/1761906552709_sting.jpg','Sting',25,'Drinks'),(24,'Mismo','/uploads/1761906600960_Royal.jpg','Royal',25,'Drinks'),(25,'Small','/uploads/1761906641532_cobra.jpg','Cobra',25,'Drinks'),(26,'1pc','/uploads/1761906678149_Yakult.jpg','Yakult',15,'Drinks'),(27,'1pc','/uploads/1761906721109_dutchmill.jpg','Dutch Mill',15,'Drinks'),(28,'C2','/uploads/1761906754258_c2.jpg','C2',15,'Drinks'),(29,'Mismo','/uploads/1761906810251_Mountain dew.jpg','mountain Dew',25,'Drinks'),(30,'Gin','/uploads/1761906900368_kwatro.jpg','Kwatro',145,'Liquor'),(31,'Gin','/uploads/1761906935564_bilog.jpg','Gin Bilog',75,'Liquor'),(32,'1pc','/uploads/1761907043218_Sky Flakes.jpg','SkyFlakes',8,'Biscuits'),(33,'1pc','/uploads/1761907087641_Hnasel.jpg','Hansel',8,'Biscuits'),(34,'1pc','/uploads/1761907135733_Camel.jpg','Camel',7,'Cigarettes'),(35,'1 Kaha','/uploads/1761907310776_Camel.jpg','Camel 1 Kaha',140,'Cigarettes'),(36,'1pc','/uploads/1761907360163_Mighty.jpg','Mighty',8,'Cigarettes'),(37,'1 Kaha','/uploads/1761907382283_Mighty.jpg','Mighty 1 kaha',180,'Cigarettes'),(38,'1pc','/uploads/1761907432564_Malboro.jpg','Marlboro',10,'Cigarettes'),(39,'1 Kaha','/uploads/1761907460204_Malboro.jpg','Marlboro 1 Kaha',200,'Cigarettes'),(40,'Nova','/uploads/1761907532271_nova.jpg','Nova',20,'Snacks'),(41,'Piattos','/uploads/1761907572996_Piattos.jpg','Piattos',20,'Snacks'),(42,'Oishi','/uploads/1761907615424_Oishi.jpg','Oishi',10,'Snacks'),(44,'Big','/uploads/1761907970071_corned beef.jpg','Corn Beef',45,'Canned Goods'),(45,'tuna','/uploads/1761908019170_century.jpg','Century Tuna',42,'Canned Goods'),(46,'Tuna','/uploads/1761908061292_555.jpg','555 Tuna',28,'Canned Goods'),(47,'Tuna','/uploads/1761908097620_Bluebay.jpg','Bluebay',32,'Canned Goods'),(48,'small','/uploads/1761908164461_mega squid.jpg','Mega Squid ',32,'Canned Goods'),(49,'Big','/uploads/1761908186113_mega squid.jpg','Mega Squid',75,'Canned Goods'),(50,'Big','/uploads/1761908224765_maling.jpg','Maling',145,'Canned Goods'),(51,'wow ulam','/uploads/1761908262117_wow ulam.jpg','Wow ulam',28,'Canned Goods'),(52,'Small','/uploads/1761908344564_meatloaf s.jpg','Meat Loaf',18,'Canned Goods'),(53,'Big','/uploads/1761908375728_meatloaf.jpg','Meat Loaf',32,'Canned Goods'),(54,'1pc','/uploads/1761908474240_magic.jpg','Magic',6,'Condiments'),(55,'small','/uploads/1761908518464_vetsin.jpg','Vetsin',9,'Condiments'),(56,'Sachet','/uploads/1761908607072_toyo pack.jpg','Datu Puti Toyo (Pack)',18,'Condiments'),(57,'Bottle','/uploads/1761908665316_toyooo.jpg','Datu Puti Toyo (Bote)',25,'Condiments'),(58,'Bottle','/uploads/1761908756844_ds.jpg','D.S (Bote)',20,'Condiments'),(59,'Sachet','/uploads/1761908795410_ds pack.jpg','D.S (Pack)',12,'Condiments'),(60,'Mango, Dalandan, Pineapple, Orange','/uploads/1761908852012_Tang.jpg','Tang',25,'Juice'),(61,'Sachet','/uploads/1761908897914_nestea.jpg','Nestea',25,'Juice'),(62,'Sachet','/uploads/1761909013883_3in1.jpg','3in1',15,'Coffee and Sugar'),(63,'Twin pack','/uploads/1761909055566_kopiko.jpg','Kopiko',15,'Coffee and Sugar'),(64,'1pc','/uploads/1761909098725_supremo.jpg','Kopiko Supremo',5,'Coffee and Sugar'),(65,'Twin pack','/uploads/1761909149718_great taste.jpg','Great Taste',15,'Coffee and Sugar'),(66,'25 grams','/uploads/1761909200489_nescafe.jpg','Nescafe 25grams',30,'Coffee and Sugar'),(67,'Brown/washed','/uploads/1761909337107_sugar.jpg','Brown Sugar 1/4',23,'Coffee and Sugar'),(68,'White/Puti. 1/4','/uploads/1761909373165_asukal w.jpg','White Sugar',25,'Coffee and Sugar'),(69,'1/2','/uploads/1761909412604_sugar.jpg','Brown Sugar 1/2',46,'Coffee and Sugar'),(70,'1/2','/uploads/1761909436187_asukal w.jpg','White Sugar 1/2',50,'Coffee and Sugar'),(71,'small','/uploads/1761909486018_Safeguard.jpg','Safeguard',28,'Soap and Downy'),(72,'powder','/uploads/1761909539682_tide.jpg','Tide Powder',14,'Soap and Downy'),(73,'Powder','/uploads/1761909573266_ariel.jpg','Ariel',16,'Soap and Downy'),(74,'Powder','/uploads/1761909606508_surf.jpg','Surf Powder',9,'Soap and Downy'),(75,'Powder','/uploads/1761909660770_breeze.jpg','Breeze Powder',15,'Soap and Downy'),(76,'Sachet','/uploads/1761909719117_head.jpg','Head and shoulders',8,'Shampoo'),(77,'Sachet','/uploads/1761909774716_downy.jpg','Downy',9,'Soap and Downy'),(78,'1 putol','/uploads/1761909831286_tide bar.jpg','Tide Bar',7,'Soap and Downy'),(79,'1 putol','/uploads/1761909876348_tops.jpg','Tops Bar',6,'Soap and Downy'),(80,'Small','/uploads/1761909933235_zonrox.jpg','Zonrox / Clorox',18,'Soap and Downy'),(81,'Big','/uploads/1761909953646_zonrox.jpg','Zonrox / Clorox',25,'Soap and Downy'),(82,'1 pc','/uploads/1761910024912_posporo.jpg','Posporo',4,'Others..'),(83,'1 magkatagpi','/uploads/1761910073985_salonpas.png','Salonpas',12,'Others..'),(84,'1pc','/uploads/1761910126753_paminta.jpg','Paminta',1,'Condiments'),(85,'1 sachet','/uploads/1761910175388_recona.jpg','Rexona',9,'Soap and Downy'),(86,'1pc','/uploads/1761910218835_Lighter.jpg','Lighter',15,'Others..'),(87,'1 pc','/uploads/1761910253355_sibuyas.jpg','Sibuyas',10,'Condiments'),(88,'1 pc','/uploads/1761910297992_bawang.jpg','Bawang',10,'Condiments'),(89,'1pc','/uploads/1761910355532_grande.jpg','Grande',115,'Liquor'),(90,'1 Case','/uploads/1761910463096_g_case-removebg-preview.png','Grande 1 case',690,'Liquor');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-01 17:36:12
