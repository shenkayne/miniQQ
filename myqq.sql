/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.26 : Database - myqq
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myqq` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `myqq`;

/*Table structure for table `ChatInfo` */

DROP TABLE IF EXISTS `ChatInfo`;

CREATE TABLE `ChatInfo` (
  `cno` int(2) NOT NULL AUTO_INCREMENT,
  `csendqq` int(5) NOT NULL,
  `creceiveqq` int(5) NOT NULL,
  `cdate` date DEFAULT NULL,
  `tno` int(3) NOT NULL,
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

/*Data for the table `ChatInfo` */

insert  into `ChatInfo`(`cno`,`csendqq`,`creceiveqq`,`cdate`,`tno`) values (1,10003,10004,'2022-11-07',0),(2,10004,10003,'2022-11-07',0),(3,10003,10004,'2022-11-07',0),(4,10004,10003,'2022-11-07',0),(5,10003,10004,'2022-11-07',0),(6,10004,10003,'2022-11-07',0),(7,10003,10004,'2022-11-07',0),(8,10003,10005,'2022-12-02',0),(9,10008,10005,'2022-12-12',0),(10,10005,10008,'2022-12-12',0),(11,10008,10005,'2022-12-12',0),(12,10008,10005,'2022-12-12',0),(13,10008,10005,'2022-12-12',0),(14,10005,10008,'2022-12-12',0),(15,10005,10008,'2022-12-12',0),(16,10008,10005,'2022-12-12',0),(17,10008,10005,'2022-12-12',0),(18,10005,10008,'2022-12-12',0),(19,10006,10005,'2022-12-12',0),(20,10005,10006,'2022-12-12',0),(21,10006,10005,'2022-12-12',0),(22,10005,10002,'2022-12-12',0),(23,10004,10005,'2022-12-12',0),(24,10004,10005,'2022-12-12',0),(25,10008,10005,'2022-12-12',0),(26,10005,10008,'2022-12-12',0),(27,10008,10005,'2022-12-12',0),(28,10005,10008,'2022-12-12',0),(29,10005,10008,'2022-12-12',0),(30,10008,10005,'2022-12-12',0),(31,10008,10005,'2022-12-12',0),(32,10005,10008,'2022-12-12',0),(33,10008,10005,'2022-12-12',0);

/*Table structure for table `Friends` */

DROP TABLE IF EXISTS `Friends`;

CREATE TABLE `Friends` (
  `Fno` int(2) NOT NULL AUTO_INCREMENT,
  `fqq` int(5) DEFAULT NULL,
  `fsno` int(2) NOT NULL,
  `fdate` date DEFAULT NULL,
  `fstatus` int(2) DEFAULT NULL,
  `qq` int(5) DEFAULT NULL,
  PRIMARY KEY (`Fno`),
  KEY `fk_friends_fqq` (`qq`),
  KEY `fk_friends_fsno` (`fsno`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;

/*Data for the table `Friends` */

insert  into `Friends`(`Fno`,`fqq`,`fsno`,`fdate`,`fstatus`,`qq`) values (41,10005,0,'2022-12-02',0,10006),(42,10006,0,'2022-12-02',0,10005),(43,10004,0,'2022-12-02',0,10006),(44,10006,0,'2022-12-02',0,10004),(45,10003,0,'2022-12-02',0,10006),(46,10006,0,'2022-12-02',0,10003),(47,10004,0,'2022-12-02',0,10005),(48,10005,0,'2022-12-02',0,10004),(49,10003,0,'2022-12-02',0,10005),(50,10005,0,'2022-12-02',0,10003),(51,10002,0,'2022-12-02',0,10005),(53,10005,0,'2022-12-02',0,10007),(55,10007,0,'2022-12-11',0,10006),(56,10006,0,'2022-12-11',0,10007),(57,10004,0,'2022-12-11',0,10007),(58,10007,0,'2022-12-11',0,10004),(59,10007,0,'2022-12-11',0,10005),(60,10005,0,'2022-12-11',0,10007),(61,10005,0,'2022-12-12',0,10008),(62,10008,0,'2022-12-12',0,10005),(63,10005,0,'2022-12-12',0,10002),(64,10002,0,'2022-12-12',0,10005),(65,10008,0,'2022-12-12',0,10002);

/*Table structure for table `GetPwdInfo` */

DROP TABLE IF EXISTS `GetPwdInfo`;

CREATE TABLE `GetPwdInfo` (
  `gqq` int(11) NOT NULL AUTO_INCREMENT,
  `question` int(11) DEFAULT NULL,
  `answer` varchar(50) DEFAULT NULL,
  `qq` int(11) DEFAULT NULL,
  PRIMARY KEY (`gqq`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `GetPwdInfo` */

insert  into `GetPwdInfo`(`gqq`,`question`,`answer`,`qq`) values (1,0,'胡朝霞',10002),(2,4,'',10002),(3,8,'',10002),(13,0,'HZX',10003),(14,4,'ZA',10003),(15,8,'香蕉',10003),(16,0,'',10004),(17,4,'',10004),(18,8,'',10004),(19,0,'hzx',10005),(20,4,'za',10005),(21,8,'',10005),(22,0,'框架，，',10006),(23,4,'',10006),(24,8,'',10006),(25,1,'2020310220108',10007),(26,4,'',10007),(27,8,'',10007),(28,2,'',10008),(29,6,'',10008),(30,11,'',10008);

/*Table structure for table `GroupTable` */

DROP TABLE IF EXISTS `GroupTable`;

CREATE TABLE `GroupTable` (
  `gno` int(5) NOT NULL,
  `gname` varchar(20) NOT NULL,
  `gdate` date DEFAULT NULL,
  PRIMARY KEY (`gno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `GroupTable` */

/*Table structure for table `Login` */

DROP TABLE IF EXISTS `Login`;

CREATE TABLE `Login` (
  `lno` int(5) NOT NULL AUTO_INCREMENT,
  `lip` varchar(20) DEFAULT NULL,
  `lport` int(5) DEFAULT NULL,
  `ldate` date DEFAULT NULL,
  `lstatus` int(1) DEFAULT NULL,
  `lqq` int(5) NOT NULL,
  PRIMARY KEY (`lno`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=latin1;

/*Data for the table `Login` */

/*Table structure for table `SubGroup` */

DROP TABLE IF EXISTS `SubGroup`;

CREATE TABLE `SubGroup` (
  `sno` int(2) NOT NULL,
  `sname` varchar(255) NOT NULL,
  `sdate` date DEFAULT NULL,
  `qq` int(5) NOT NULL,
  PRIMARY KEY (`sno`),
  KEY `fk_subgroup_qq` (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `SubGroup` */

insert  into `SubGroup`(`sno`,`sname`,`sdate`,`qq`) values (0,'我的好友','2022-11-01',1),(1,'好友','2022-10-30',10003),(2,'陌生人','2022-10-29',10002),(3,'好友',NULL,10002);

/*Table structure for table `Text` */

DROP TABLE IF EXISTS `Text`;

CREATE TABLE `Text` (
  `tno` int(3) NOT NULL AUTO_INCREMENT,
  `tcontext` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `tfonttype` varchar(10) DEFAULT NULL,
  `tfontsize` int(5) DEFAULT NULL,
  `tfontcolor` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `isBold` int(11) DEFAULT NULL,
  `isItatic` int(11) DEFAULT NULL,
  `isUnderline` int(11) DEFAULT NULL,
  PRIMARY KEY (`tno`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

/*Data for the table `Text` */

insert  into `Text`(`tno`,`tcontext`,`tfonttype`,`tfontsize`,`tfontcolor`,`isBold`,`isItatic`,`isUnderline`) values (10,'52435','楷体',14,'0*0*255',1,0,0),(11,'3454','楷体',14,'0*0*255',1,0,0),(12,'4','楷体',14,'0*0*255',1,0,0),(13,'45345','楷体',14,'0*0*255',1,0,0),(14,'43534','楷体',14,'0*0*255',1,0,0),(15,'5424','楷体',14,'0*0*255',1,0,0),(16,'254','楷体',14,'0*0*255',1,0,0),(17,'54335','楷体',14,'0*0*255',1,0,0),(18,'43545','楷体',14,'0*0*255',1,0,0),(19,'43534','楷体',14,'0*0*255',1,0,0),(20,'4','楷体',14,'0*0*255',1,0,0),(21,'543','楷体',14,'0*0*255',1,0,0),(22,'453','楷体',14,'0*0*255',1,0,0),(23,'534453','楷体',14,'0*0*255',1,0,0),(24,'53444','楷体',14,'0*0*255',1,0,0),(25,'4254','楷体',14,'0*0*255',1,0,0),(26,'2542345','楷体',14,'0*0*255',1,0,0),(27,'254','楷体',14,'0*0*255',1,0,0),(28,'5434','楷体',14,'0*0*255',1,0,0),(29,'5252','楷体',14,'0*0*255',1,0,0),(30,'41421','楷体',14,'0*0*255',1,0,0),(31,'25324','楷体',14,'0*0*255',1,0,0),(32,'25234','楷体',14,'0*0*255',1,0,0),(33,'254','楷体',14,'0*0*255',1,0,0),(34,'535','楷体',14,'0*0*255',1,0,0),(35,'12315','楷体',14,'0*0*255',1,0,0),(36,'212315314','楷体',14,'0*0*255',1,0,0),(37,'541531','楷体',14,'0*0*255',1,0,0),(38,'54531','楷体',14,'0*0*255',1,0,0),(39,'4545634','楷体',14,'0*0*255',1,0,0),(40,'53156','楷体',14,'0*0*255',1,0,0),(41,'466534','楷体',14,'0*0*255',1,0,0),(42,'15313','楷体',14,'0*0*255',1,0,0),(43,'485413','楷体',14,'0*0*255',1,0,0),(44,'153415','楷体',14,'0*0*255',1,0,0),(45,'15153','楷体',14,'0*0*255',1,0,0),(46,'35153','楷体',14,'0*0*255',1,0,0),(47,'45153','楷体',14,'0*0*255',1,0,0),(48,'nihao scr','楷体',14,'0*0*255',1,0,0),(49,'5135','楷体',14,'0*0*255',1,0,0),(50,'4241534','楷体',14,'0*0*255',1,0,0),(51,'15346','楷体',14,'0*0*255',1,0,0),(52,'456456','楷体',14,'0*0*255',1,0,0),(53,'45345','楷体',14,'0*0*255',1,0,0),(54,'435456','楷体',14,'0*0*255',1,0,0),(55,'545341356','楷体',14,'0*0*255',1,0,0),(56,'413515','楷体',14,'0*0*255',1,0,0),(57,'45645','楷体',14,'0*0*255',1,0,0),(58,'454634','楷体',14,'0*0*255',1,0,0),(59,'4545634','楷体',14,'0*0*255',1,0,0);

/*Table structure for table `UserInfo` */

DROP TABLE IF EXISTS `UserInfo`;

CREATE TABLE `UserInfo` (
  `qq` int(5) NOT NULL COMMENT 'qq',
  `pwd` varchar(10) CHARACTER SET latin1 NOT NULL COMMENT '密码',
  `sign` varchar(30) DEFAULT NULL COMMENT '签名',
  `photoID` int(2) NOT NULL COMMENT '头像id',
  `nickname` varchar(10) NOT NULL COMMENT '昵称',
  `sex` char(2) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `telephone` varchar(15) CHARACTER SET latin1 DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '邮箱',
  `address` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `UserInfo` */

insert  into `UserInfo`(`qq`,`pwd`,`sign`,`photoID`,`nickname`,`sex`,`birthday`,`telephone`,`email`,`address`) values (10001,'123',NULL,2,'shen','男',NULL,NULL,NULL,NULL),(10002,'123','沈才人',1,'scr','na','1992-03-16','134398','1196734@qq.com','hubei'),(10003,'123','我刚申请了QQ,欢迎大家加我QQ!',1,'沈才人','男','2002-07-01','13797220670','2073609597@qq.com','湖北'),(10004,'123','我刚申请了QQ,欢迎大家加我QQ!',1,'小沈','男','2010-12-31','','',''),(10005,'123','我刚申请了QQ,欢迎大家加我QQ!',1,'小小西瓜呀','男','2013-12-31','','',''),(10006,'123','我刚申请了QQ,欢迎大家加我QQ!',1,'才人','男','2013-12-31','','',''),(10007,'123','我刚申请了QQ,欢迎大家加我QQ!',4,'小明','男','2011-06-03','13797220670','','湖北'),(10008,'123','我刚申请了QQ,欢迎大家加我QQ!',1,'沈才人','男','2002-06-05','13797220670','2073609597@qq.com','湖北');

/*Table structure for table `User_Group` */

DROP TABLE IF EXISTS `User_Group`;

CREATE TABLE `User_Group` (
  `ugno` int(2) NOT NULL,
  `qq` int(5) NOT NULL,
  `gno` int(5) NOT NULL,
  PRIMARY KEY (`ugno`),
  KEY `fk_usergroup_qq` (`qq`),
  KEY `fk_usergroup_gno` (`gno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `User_Group` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
