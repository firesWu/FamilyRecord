/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : familyrecord

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-03-28 14:45:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for album_name
-- ----------------------------
DROP TABLE IF EXISTS `album_name`;
CREATE TABLE `album_name` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) DEFAULT NULL COMMENT '家庭组Id',
  `albumName` varchar(255) DEFAULT NULL COMMENT '相册名称',
  `type` int(11) DEFAULT NULL COMMENT '1.相册  2.视频相册',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `isDelete` tinyint(4) DEFAULT '0',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '帖子主题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `isDelete` tinyint(4) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for common_file
-- ----------------------------
DROP TABLE IF EXISTS `common_file`;
CREATE TABLE `common_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rId` int(11) DEFAULT NULL COMMENT '相册/视频相册主键',
  `filePath` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `fileName` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `type` int(11) DEFAULT NULL COMMENT '1.照片、2.视频',
  `creator` varchar(255) DEFAULT NULL COMMENT '上传人',
  `isDelete` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for family_group
-- ----------------------------
DROP TABLE IF EXISTS `family_group`;
CREATE TABLE `family_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '家庭组名称',
  `creator` varchar(255) NOT NULL COMMENT '创建人',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for family_user
-- ----------------------------
DROP TABLE IF EXISTS `family_user`;
CREATE TABLE `family_user` (
  `familyId` int(11) NOT NULL COMMENT '家庭组主键',
  `userId` varchar(255) NOT NULL COMMENT '用户主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `account` varchar(255) NOT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickName` varchar(255) NOT NULL COMMENT '昵称',
  `birthday` date NOT NULL COMMENT '生日',
  `headImageUrl` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
