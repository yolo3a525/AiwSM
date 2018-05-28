/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : yolo

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-05-18 17:19:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_dd
-- ----------------------------
DROP TABLE IF EXISTS `base_dd`;
CREATE TABLE `base_dd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `key` varchar(20) DEFAULT NULL,
  `value` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_dd
-- ----------------------------
INSERT INTO `base_dd` VALUES ('231', 'datatype', '字符串', 'java.lang.String', '2017-05-05 13:45:13', '2017-05-05 13:45:13', 'admin', 'admin');
INSERT INTO `base_dd` VALUES ('232', 'datatype', '日期及时间', 'java.sql.Timestamp', '2017-05-05 13:45:58', '2017-05-05 13:45:58', 'admin', 'admin');
INSERT INTO `base_dd` VALUES ('233', '电脑', '苹果', 'apple', '2017-05-07 20:56:48', '2017-05-07 20:56:48', 'admin', 'admin');

-- ----------------------------
-- Table structure for base_menu
-- ----------------------------
DROP TABLE IF EXISTS `base_menu`;
CREATE TABLE `base_menu` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `pid` int(5) NOT NULL DEFAULT '0',
  `name` varchar(20) NOT NULL,
  `url` varchar(100) NOT NULL,
  `order` int(2) NOT NULL DEFAULT '1',
  `depth` int(2) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(50) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `text` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_menu
-- ----------------------------
INSERT INTO `base_menu` VALUES ('175', '0', '系统设置', '#', '5', '1', '2017-05-18 17:13:34', '2017-05-18 17:13:34', null, 'admin');
INSERT INTO `base_menu` VALUES ('176', '0', 'Demo', '#', '5', '1', '2017-05-18 17:13:34', '2017-05-18 17:13:34', null, '');
INSERT INTO `base_menu` VALUES ('177', '175', '角色管理', 'role/list', '1', '2', '2017-05-18 17:15:16', '2017-05-18 17:15:16', null, 'admin');
INSERT INTO `base_menu` VALUES ('180', '175', '菜单管理', 'menu/tree', '4', '2', '2017-05-18 17:15:23', '2017-05-18 17:15:23', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('181', '175', '权限管理', 'privilege/list', '2', '2', '2017-05-18 17:15:18', '2017-05-18 17:15:18', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('182', '175', '用户管理', 'user/list', '0', '2', '2017-05-18 17:15:05', '2017-05-18 17:15:05', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('183', '175', '数据字典', 'dd/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('186', '175', '代码生成', 'code', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('188', '176', '订单管理', 'order/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('189', '176', '新闻管理', 'news/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('190', '176', '我的计划', 'workplan/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('191', '176', 'DEMO22', 'crud/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('192', '0', 'test', '#', '5', '1', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('193', '192', 'bb', 'bb/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('196', '175', '操作记录', 'requestRecord/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('197', '175', '系统信息', 'sys/info', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('203', '0', '权限测试', '#', '5', '1', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');
INSERT INTO `base_menu` VALUES ('204', '203', '测试', 'crud/list', '5', '2', '2017-05-18 17:13:34', '2017-05-18 17:13:34', 'admin', 'admin');

-- ----------------------------
-- Table structure for base_privilege
-- ----------------------------
DROP TABLE IF EXISTS `base_privilege`;
CREATE TABLE `base_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` varchar(50) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  `depth` int(2) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_privilege
-- ----------------------------
INSERT INTO `base_privilege` VALUES ('32', '测试查询', 'demo.list', null, null, '2017-05-18 16:30:28', '2017-05-18 16:30:28', 'admin', 'admin');
INSERT INTO `base_privilege` VALUES ('33', '测试新增', 'demo.add', null, null, '2017-05-18 16:30:42', '2017-05-18 16:30:42', 'admin', 'admin');
INSERT INTO `base_privilege` VALUES ('34', '测试删除', 'demo.delete', null, null, '2017-05-18 16:30:59', '2017-05-18 16:30:59', 'admin', 'admin');
INSERT INTO `base_privilege` VALUES ('35', '测试修改', 'demo.update', null, null, '2017-05-18 16:31:12', '2017-05-18 16:31:12', 'admin', 'admin');


-- ----------------------------
-- Records of base_request_record
-- ----------------------------

-- ----------------------------
-- Table structure for base_role
-- ----------------------------
DROP TABLE IF EXISTS `base_role`;
CREATE TABLE `base_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_role
-- ----------------------------
INSERT INTO `base_role` VALUES ('227', '管理员', null, '2017-05-18 15:07:30', '2017-05-18 15:07:30', 'admin', 'admin');
INSERT INTO `base_role` VALUES ('228', '一般人员', null, '2017-05-18 15:07:22', '2017-05-18 15:07:22', 'admin', 'admin');

-- ----------------------------
-- Table structure for base_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `base_role_privilege`;
CREATE TABLE `base_role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  UNIQUE KEY `roleId` (`role_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_role_privilege
-- ----------------------------
INSERT INTO `base_role_privilege` VALUES ('103', '8');
INSERT INTO `base_role_privilege` VALUES ('103', '10');
INSERT INTO `base_role_privilege` VALUES ('103', '11');
INSERT INTO `base_role_privilege` VALUES ('193', '8');
INSERT INTO `base_role_privilege` VALUES ('193', '10');
INSERT INTO `base_role_privilege` VALUES ('193', '11');
INSERT INTO `base_role_privilege` VALUES ('195', '8');
INSERT INTO `base_role_privilege` VALUES ('195', '10');
INSERT INTO `base_role_privilege` VALUES ('195', '11');
INSERT INTO `base_role_privilege` VALUES ('202', '8');
INSERT INTO `base_role_privilege` VALUES ('202', '10');
INSERT INTO `base_role_privilege` VALUES ('202', '11');
INSERT INTO `base_role_privilege` VALUES ('203', '8');
INSERT INTO `base_role_privilege` VALUES ('203', '10');
INSERT INTO `base_role_privilege` VALUES ('203', '11');
INSERT INTO `base_role_privilege` VALUES ('226', '8');
INSERT INTO `base_role_privilege` VALUES ('226', '10');
INSERT INTO `base_role_privilege` VALUES ('226', '11');
INSERT INTO `base_role_privilege` VALUES ('226', '12');
INSERT INTO `base_role_privilege` VALUES ('227', '32');
INSERT INTO `base_role_privilege` VALUES ('227', '33');
INSERT INTO `base_role_privilege` VALUES ('227', '34');
INSERT INTO `base_role_privilege` VALUES ('227', '35');

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(50) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES ('111', 'admin', '111111', '2017-05-08 19:00:04', '2017-05-08 19:00:04', 'admin', 'admin');
INSERT INTO `base_user` VALUES ('117', 'whs', '1111111', '2017-05-18 15:28:55', '2017-05-18 15:28:55', 'admin', 'admin');
INSERT INTO `base_user` VALUES ('118', '15933133', '111111', '2017-05-18 15:29:30', '2017-05-18 15:29:30', 'admin', 'admin');

-- ----------------------------
-- Table structure for base_user_role
-- ----------------------------
DROP TABLE IF EXISTS `base_user_role`;
CREATE TABLE `base_user_role` (
  `user_Id` int(11) NOT NULL,
  `role_Id` int(11) NOT NULL,
  UNIQUE KEY `userId` (`user_Id`,`role_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_user_role
-- ----------------------------
INSERT INTO `base_user_role` VALUES ('51', '103');
INSERT INTO `base_user_role` VALUES ('78', '203');
INSERT INTO `base_user_role` VALUES ('88', '203');
INSERT INTO `base_user_role` VALUES ('88', '204');
INSERT INTO `base_user_role` VALUES ('111', '227');

-- ----------------------------
-- Table structure for b_abc
-- ----------------------------
DROP TABLE IF EXISTS `b_abc`;
CREATE TABLE `b_abc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_abc
-- ----------------------------
INSERT INTO `b_abc` VALUES ('231', '2017-05-03 18:56:54', '2017-05-05 12:26:07', '2017-05-05 12:26:07', 'admin', 'admin');
INSERT INTO `b_abc` VALUES ('232', '2017-05-03 18:56:54', '2017-05-05 12:27:04', '2017-05-05 12:27:04', 'admin', 'admin');
INSERT INTO `b_abc` VALUES ('233', '2017-05-03 18:56:54', '2017-05-05 12:27:50', '2017-05-05 12:27:50', 'admin', 'admin');
INSERT INTO `b_abc` VALUES ('234', '2017-05-03 18:56:54', '2017-05-05 12:28:22', '2017-05-05 12:28:22', 'admin', 'admin');
INSERT INTO `b_abc` VALUES ('236', '2017-05-03 18:56:54', '2017-05-05 12:36:08', '2017-05-05 12:36:08', 'admin', 'admin');
INSERT INTO `b_abc` VALUES ('237', '2017-05-03 18:56:54', '2017-05-05 12:37:10', '2017-05-05 12:37:10', 'admin', 'admin');

-- ----------------------------
-- Table structure for b_bb
-- ----------------------------
DROP TABLE IF EXISTS `b_bb`;
CREATE TABLE `b_bb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `b` int(11) DEFAULT NULL,
  `c` varchar(20) DEFAULT NULL,
  `a` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_bb
-- ----------------------------
INSERT INTO `b_bb` VALUES ('230', '31112', '2017-05-25', 'gg12121', '2017-05-08 00:25:06', '2017-05-08 00:25:06', 'admin', 'admin');

-- ----------------------------
-- Table structure for b_bg
-- ----------------------------
DROP TABLE IF EXISTS `b_bg`;
CREATE TABLE `b_bg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `b` int(11) DEFAULT NULL,
  `c` varchar(20) DEFAULT NULL,
  `a` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_bg
-- ----------------------------

-- ----------------------------
-- Table structure for b_crud
-- ----------------------------
DROP TABLE IF EXISTS `b_crud`;
CREATE TABLE `b_crud` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `birthday` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_crud
-- ----------------------------
INSERT INTO `b_crud` VALUES ('231', '2017-05-12', 'babyzn@126.com', '首饰盒112', '5644', '2017-05-08 00:43:10', '2017-05-08 00:43:10', 'admin', 'admin');
INSERT INTO `b_crud` VALUES ('239', '2017-05-12', 'babyzn@126.com', '首饰盒112', '133', '2017-05-13 15:57:55', '2017-05-13 15:57:55', 'admin', 'admin');
INSERT INTO `b_crud` VALUES ('241', '2017-05-01', 'babyzn@126.com', '1000', '11', '2017-05-13 15:58:42', '2017-05-13 15:58:42', 'admin', 'admin');
INSERT INTO `b_crud` VALUES ('242', '2017-05-12', 'babyzn@126.com', '首饰盒112', '11', '2017-05-13 16:03:07', '2017-05-13 16:03:07', 'admin', 'admin');
INSERT INTO `b_crud` VALUES ('243', '2017-05-18', 'babyzn@126.com', '333', '33', '2017-05-13 16:37:20', '2017-05-13 16:37:20', 'admin', 'admin');
INSERT INTO `b_crud` VALUES ('244', '2017-05-09', '3684170@qq.com', '1000', '11', '2017-05-18 00:38:21', '2017-05-18 00:38:21', 'admin', 'admin');

-- ----------------------------
-- Table structure for b_customer
-- ----------------------------
DROP TABLE IF EXISTS `b_customer`;
CREATE TABLE `b_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_customer
-- ----------------------------
INSERT INTO `b_customer` VALUES ('230', '香儿', '2017-05-05 16:28:06', '2017-05-05 16:28:06', 'admin', 'admin');

-- ----------------------------
-- Table structure for b_news
-- ----------------------------
DROP TABLE IF EXISTS `b_news`;
CREATE TABLE `b_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `module` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_news
-- ----------------------------

-- ----------------------------
-- Table structure for b_order
-- ----------------------------
DROP TABLE IF EXISTS `b_order`;
CREATE TABLE `b_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_order
-- ----------------------------
INSERT INTO `b_order` VALUES ('230', '111的', '2017-05-05 18:06:45', '2017-05-05 18:06:45', 'admin', 'admin');

-- ----------------------------
-- Table structure for b_workplan
-- ----------------------------
DROP TABLE IF EXISTS `b_workplan`;
CREATE TABLE `b_workplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `progress` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_workplan
-- ----------------------------
INSERT INTO `b_workplan` VALUES ('239', '90', 'UI', '2017-05-08 17:15:49', '2017-05-08 17:15:49', 'admin', 'admin');
INSERT INTO `b_workplan` VALUES ('240', '50', 'ajax', '2017-05-08 17:16:00', '2017-05-08 17:16:00', 'admin', 'admin');
INSERT INTO `b_workplan` VALUES ('242', '11', '登录', '2017-05-13 16:33:52', '2017-05-13 16:33:52', 'admin', 'admin');
INSERT INTO `b_workplan` VALUES ('243', '222', '111', '2017-05-13 16:35:25', '2017-05-13 16:35:25', 'admin', 'admin');
