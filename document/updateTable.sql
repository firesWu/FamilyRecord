/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : familyrecord

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-03-30 01:51:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rId` int(11) DEFAULT NULL COMMENT '家庭组ID',
  `title` varchar(255) NOT NULL COMMENT '帖子主题',
  `content` text COMMENT '内容',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `isDelete` tinyint(4) DEFAULT '0',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` int(11) DEFAULT NULL COMMENT '帖子id',
  `parentId` varchar(11) DEFAULT NULL,
  `replyId` varchar(11) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL COMMENT '评论',
  `floor` int(11) DEFAULT NULL COMMENT '楼层',
  `read` tinyint(4) DEFAULT '0' COMMENT '是否已读',
  `isDelete` tinyint(4) DEFAULT '0',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
