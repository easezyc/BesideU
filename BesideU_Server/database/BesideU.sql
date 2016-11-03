/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.28 : Database - android
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`android` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `android`;

/*Table structure for table `besideu` */

DROP TABLE IF EXISTS `besideu`;

CREATE TABLE `besideu` (
  `email1` varchar(30) NOT NULL,
  `email2` varchar(30) NOT NULL,
  `mark` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `besideu` */

insert  into `besideu`(`email1`,`email2`,`mark`) values ('zhuyongchunzyc@163.com','1299192934@qq.com','1');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `phone` varchar(20) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `user` */

insert  into `user`(`phone`,`pwd`) values ('17888842296','11111');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
