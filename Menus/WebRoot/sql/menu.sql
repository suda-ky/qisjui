/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : menu

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2020-12-19 00:26:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '所有商品目录', '0');
INSERT INTO `t_menu` VALUES ('3', '童书', '2');
INSERT INTO `t_menu` VALUES ('4', '教育', '2');
INSERT INTO `t_menu` VALUES ('7', '科技1', '2');
INSERT INTO `t_menu` VALUES ('8', '创意文具/拍卖', '1');
INSERT INTO `t_menu` VALUES ('9', '学生文具', '8');
INSERT INTO `t_menu` VALUES ('10', '画具/画材', '8');
INSERT INTO `t_menu` VALUES ('11', '文房用品', '8');
INSERT INTO `t_menu` VALUES ('12', '中国书画拍卖', '8');
INSERT INTO `t_menu` VALUES ('13', '西画雕塑拍卖', '8');
INSERT INTO `t_menu` VALUES ('14', '手机/数码/电脑办公', '1');
INSERT INTO `t_menu` VALUES ('15', '手机配件', '14');
INSERT INTO `t_menu` VALUES ('16', '视听影音', '14');
INSERT INTO `t_menu` VALUES ('17', '摄像摄影', '14');
INSERT INTO `t_menu` VALUES ('18', '外设产品', '14');
INSERT INTO `t_menu` VALUES ('19', '办公设备', '14');
INSERT INTO `t_menu` VALUES ('20', '当当礼品卡', '1');
INSERT INTO `t_menu` VALUES ('21', 'V卡', '20');
INSERT INTO `t_menu` VALUES ('22', '经典卡', '20');
INSERT INTO `t_menu` VALUES ('23', '主题卡', '20');
INSERT INTO `t_menu` VALUES ('24', '节日卡', '20');
INSERT INTO `t_menu` VALUES ('27', '图书/童书', '1');
INSERT INTO `t_menu` VALUES ('28', '运动户外', '1');
INSERT INTO `t_menu` VALUES ('29', '户外服饰', '28');
INSERT INTO `t_menu` VALUES ('30', '健身器材', '28');
INSERT INTO `t_menu` VALUES ('31', '体育运动', '28');
