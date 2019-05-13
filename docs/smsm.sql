/*
Navicat MySQL Data Transfer

Source Server         : localMysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : smsm

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-05-13 16:48:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_ID` varchar(36) NOT NULL,
  `USER_NAME` varchar(56) DEFAULT NULL,
  `USER_CAPTION` varchar(56) DEFAULT NULL,
  `PASSWORD` varchar(56) DEFAULT NULL,
  `AGE` int(11) DEFAULT NULL,
  `ADDRESS` varchar(128) DEFAULT NULL,
  `TELEPHONE` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(56) DEFAULT NULL,
  `STATUS` char(2) DEFAULT NULL,
  `MEMO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('24bfce0442e640af9bb4b8a22b3096ef', 'zs', '张三', '123456', '22', '湖北省武汉市xxx', '13512091221', 'zs@abc.com', '1', null);
INSERT INTO `user` VALUES ('3cfe8db455d7484bb83c130520b08cb3', 'ls', '李四', '123456', '23', null, '13512091222', 'ls@abc.com', '1', null);
INSERT INTO `user` VALUES ('52fbf70a0e6344a58cf89d0307770f77', 'ww', '王五', '123456', '24', null, '13512091223', 'ww@abc.com', '1', null);
INSERT INTO `user` VALUES ('7c4de6baeb404671b5ac5cf5418d279b', 'admin', '管理员', '123456', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('8e656bb426a94648bd58a82ebbe73c66', 'tom', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for role_perm
-- ----------------------------
DROP TABLE IF EXISTS `role_perm`;
CREATE TABLE `role_perm` (
  `role_id` varchar(36) NOT NULL,
  `perm_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_perm
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(36) NOT NULL,
  `role_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for perm
-- ----------------------------
DROP TABLE IF EXISTS `perm`;
CREATE TABLE `perm` (
  `perm_id` varchar(36) NOT NULL,
  `perm` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of perm
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` varchar(36) NOT NULL,
  `menu_name` varchar(56) DEFAULT NULL,
  `parent_menu` varchar(36) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  `style` varchar(128) DEFAULT NULL,
  `leaf` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0100', '系统管理', null, null, 'ios-navigate', '0');
INSERT INTO `menu` VALUES ('0101', '用户管理', '0100', 'user', null, '1');
INSERT INTO `menu` VALUES ('0102', '机构管理', '0100', null, null, '1');
INSERT INTO `menu` VALUES ('0103', '角色管理', '0100', null, null, '1');
INSERT INTO `menu` VALUES ('0104', '菜单管理', '0100', null, null, '1');
INSERT INTO `menu` VALUES ('0200', '配置管理', null, null, 'ios-keypad', '0');
INSERT INTO `menu` VALUES ('9900', '辅助管理', null, null, 'ios-keypad', '0');
INSERT INTO `menu` VALUES ('9901', '日志管理', '9900', null, null, '1');
INSERT INTO `menu` VALUES ('9902', '消息管理', '9900', null, null, '1');
INSERT INTO `menu` VALUES ('9903', '邮件管理', '9900', null, null, '1');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `ID` varchar(36) NOT NULL,
  `DEPT_NO` varchar(24) DEFAULT NULL,
  `DEPT_NAME` varchar(56) DEFAULT NULL,
  `DEPT_PARENT_NO` varchar(24) DEFAULT NULL,
  `DUTY` varchar(56) DEFAULT NULL,
  `CONTACT` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
