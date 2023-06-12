/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : coach_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-02-21 22:13:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `agerange`
-- ----------------------------
DROP TABLE IF EXISTS `agerange`;
CREATE TABLE `agerange` (
  `arId` int(11) NOT NULL auto_increment,
  `startAge` int(11) default NULL,
  `endAge` int(11) default NULL,
  `showText` varchar(20) default NULL,
  PRIMARY KEY  (`arId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agerange
-- ----------------------------
INSERT INTO `agerange` VALUES ('1', '0', '5', '0到5岁');
INSERT INTO `agerange` VALUES ('2', '6', '12', '6到12岁');
INSERT INTO `agerange` VALUES ('3', '12', '18', '12到18岁');
INSERT INTO `agerange` VALUES ('4', '18', '30', '18岁到30岁');
INSERT INTO `agerange` VALUES ('5', '30', '45', '30到45岁');
INSERT INTO `agerange` VALUES ('6', '45', '100', '45岁以上');

-- ----------------------------
-- Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `cityNo` varchar(20) NOT NULL,
  `cityName` varchar(20) default NULL,
  PRIMARY KEY  (`cityNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('010', '北京');
INSERT INTO `city` VALUES ('028', '成都');

-- ----------------------------
-- Table structure for `coach`
-- ----------------------------
DROP TABLE IF EXISTS `coach`;
CREATE TABLE `coach` (
  `coachUserName` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `sex` varchar(20) default NULL,
  `ageRangeObj` int(11) default NULL,
  `age` int(11) default NULL,
  `telephone` varchar(20) default NULL,
  `cityObj` varchar(20) default NULL,
  `nowStateObj` int(11) default NULL,
  `priceRangeObj` int(11) default NULL,
  `price` int(11) default NULL,
  `coachPhoto` varchar(50) default NULL,
  `coachDesc` longtext,
  `shzt` varchar(20) default NULL,
  `regTime` varchar(20) default NULL,
  PRIMARY KEY  (`coachUserName`),
  KEY `FK3E4147A4BF6EE19` (`cityObj`),
  KEY `FK3E4147AA81932F9` (`nowStateObj`),
  KEY `FK3E4147ABD721C19` (`ageRangeObj`),
  KEY `FK3E4147A1460D7D9` (`priceRangeObj`),
  CONSTRAINT `FK3E4147A1460D7D9` FOREIGN KEY (`priceRangeObj`) REFERENCES `pricerange` (`prId`),
  CONSTRAINT `FK3E4147A4BF6EE19` FOREIGN KEY (`cityObj`) REFERENCES `city` (`cityNo`),
  CONSTRAINT `FK3E4147AA81932F9` FOREIGN KEY (`nowStateObj`) REFERENCES `nowstate` (`stateId`),
  CONSTRAINT `FK3E4147ABD721C19` FOREIGN KEY (`ageRangeObj`) REFERENCES `agerange` (`arId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coach
-- ----------------------------
INSERT INTO `coach` VALUES ('sj001', '123', '李明杰', '男', '5', '32', '13598349934', '028', '1', '2', '80', 'upload/0A5764AFBE603E5B1082E65C217579A4.jpg', '1111', '审核通过', '2017-12-25 14:15:33');
INSERT INTO `coach` VALUES ('sj002', '123', 'a', 'b', '1', '2', '3', '010', '1', '1', '5', 'upload/noimage.jpg', '6', '审核通过', '2017-12-30 18:07:11');

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `managerUserName` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `telephone` varchar(20) default NULL,
  PRIMARY KEY  (`managerUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('gl001', '123', '王中兴', '男', '2017-12-06 00:00:00', '13598324324');

-- ----------------------------
-- Table structure for `nowstate`
-- ----------------------------
DROP TABLE IF EXISTS `nowstate`;
CREATE TABLE `nowstate` (
  `stateId` int(11) NOT NULL auto_increment,
  `stateName` varchar(20) default NULL,
  PRIMARY KEY  (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of nowstate
-- ----------------------------
INSERT INTO `nowstate` VALUES ('1', '空闲');
INSERT INTO `nowstate` VALUES ('2', '在忙');

-- ----------------------------
-- Table structure for `pricerange`
-- ----------------------------
DROP TABLE IF EXISTS `pricerange`;
CREATE TABLE `pricerange` (
  `prId` int(11) NOT NULL auto_increment,
  `startPrice` int(11) default NULL,
  `endPrice` int(11) default NULL,
  `showText` varchar(20) default NULL,
  PRIMARY KEY  (`prId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pricerange
-- ----------------------------
INSERT INTO `pricerange` VALUES ('1', '0', '50', '50元/小时以下');
INSERT INTO `pricerange` VALUES ('2', '50', '120', '50-120元/小时');
INSERT INTO `pricerange` VALUES ('3', '120', '300', '120-300元/小时');

-- ----------------------------
-- Table structure for `studentparent`
-- ----------------------------
DROP TABLE IF EXISTS `studentparent`;
CREATE TABLE `studentparent` (
  `spUserName` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `parentName` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `cityObj` varchar(20) default NULL,
  `studentSex` varchar(4) default NULL,
  `ageRangeObj` int(11) default NULL,
  `age` int(11) default NULL,
  `school` varchar(50) default NULL,
  `nowStateObj` int(11) default NULL,
  `studentPhoto` varchar(50) default NULL,
  `studentDesc` longtext,
  `shzt` varchar(20) default NULL,
  `regTime` varchar(20) default NULL,
  PRIMARY KEY  (`spUserName`),
  KEY `FKF253CEA54BF6EE19` (`cityObj`),
  KEY `FKF253CEA5A81932F9` (`nowStateObj`),
  KEY `FKF253CEA5BD721C19` (`ageRangeObj`),
  CONSTRAINT `FKF253CEA54BF6EE19` FOREIGN KEY (`cityObj`) REFERENCES `city` (`cityNo`),
  CONSTRAINT `FKF253CEA5A81932F9` FOREIGN KEY (`nowStateObj`) REFERENCES `nowstate` (`stateId`),
  CONSTRAINT `FKF253CEA5BD721C19` FOREIGN KEY (`ageRangeObj`) REFERENCES `agerange` (`arId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studentparent
-- ----------------------------
INSERT INTO `studentparent` VALUES ('jz001', '123', '李晓丽', '13598393984', '028', '女', '3', '13', '华林小学', '1', 'upload/78F94DD03D05D61C7D9CAAE5DF649AE8.jpg', '数学不好', '审核通过', '2017-12-25 14:15:16');
INSERT INTO `studentparent` VALUES ('jz002', '123', '1', '2', '010', '3', '3', '4', '5', '1', 'upload/noimage.jpg', '6', '审核通过', '2017-12-30 18:06:37');
