/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : suda

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2020-10-11 21:25:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_downloadlist`
-- ----------------------------
DROP TABLE IF EXISTS `t_downloadlist`;
CREATE TABLE `t_downloadlist` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `size` mediumtext CHARACTER SET utf8,
  `star` int(255) DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_downloadlist
-- ----------------------------
INSERT INTO `t_downloadlist` VALUES ('1', 'Java教案', 'download/Java教案.docx', '编程王国的强者-Java，教案由聂老师倾心撰写，图文并茂，内容翔实，引导初学者一步步步入Java精彩大世界', '1677721.6', '4', 'img/dlppt.png');
INSERT INTO `t_downloadlist` VALUES ('2', 'Java教案', 'download/Java教案.pdf', '编程王国的强者-Java，教案由聂老师倾心撰写，图文并茂，内容翔实，引导初学者一步步步入Java精彩大世界', '971100.16', '4', 'img/dlppt.png');
INSERT INTO `t_downloadlist` VALUES ('3', 'web应用开发教案', 'download/web应用开发教案.docx', '蒹葭苍苍，白露为霜。所谓依人，在水一方...，新技术、新知识宛如一位美貌典雅的妙龄女子，让人 怦然心动。教案由聂老师倾心撰写，图文并茂，内容详实，引导初学者一步步步入JavaWeb开发精彩大世界', '11114905.6', '4', 'img/dlppm.png');

-- ----------------------------
-- Table structure for `t_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceName` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('1', '首页', '');
INSERT INTO `t_resource` VALUES ('2', '资源下载', 'MainController.do?download=select');
INSERT INTO `t_resource` VALUES ('3', '用户管理', 'MainController.do?user=manage');
INSERT INTO `t_resource` VALUES ('4', '资源管理', 'MainController.do?download=manage');
INSERT INTO `t_resource` VALUES ('5', '个人中心', 'MainController.do?user=center');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员');
INSERT INTO `t_role` VALUES ('2', '普通用户');

-- ----------------------------
-- Table structure for `t_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `resource_id` (`resource_id`),
  KEY `user_role_id` (`role_id`),
  CONSTRAINT `resource_id` FOREIGN KEY (`resource_id`) REFERENCES `t_resource` (`id`),
  CONSTRAINT `user_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_user_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('1', '2', '1');
INSERT INTO `t_role_resource` VALUES ('2', '3', '1');
INSERT INTO `t_role_resource` VALUES ('3', '4', '1');
INSERT INTO `t_role_resource` VALUES ('4', '5', '1');
INSERT INTO `t_role_resource` VALUES ('5', '2', '2');
INSERT INTO `t_role_resource` VALUES ('6', '3', '2');
INSERT INTO `t_role_resource` VALUES ('7', '4', '2');
INSERT INTO `t_role_resource` VALUES ('8', '5', '2');
INSERT INTO `t_role_resource` VALUES ('9', '2', '3');
INSERT INTO `t_role_resource` VALUES ('10', '5', '3');
INSERT INTO `t_role_resource` VALUES ('11', '2', '4');
INSERT INTO `t_role_resource` VALUES ('12', '5', '4');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `passWord` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `chiName` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('niegang', '123456', '超级管理员', '管理员');
INSERT INTO `t_user` VALUES ('suda', '962464', '管理员', '管理员');
INSERT INTO `t_user` VALUES ('user1', '123', '用户1', '普通用户');
INSERT INTO `t_user` VALUES ('user3', '123', '用户2', '普通用户');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_name` (`user_name`),
  CONSTRAINT `user_name` FOREIGN KEY (`user_name`) REFERENCES `t_user` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', 'suda');
INSERT INTO `t_user_role` VALUES ('2', '1', 'niegang');
INSERT INTO `t_user_role` VALUES ('3', '2', 'user1');
INSERT INTO `t_user_role` VALUES ('4', '2', 'user3');
