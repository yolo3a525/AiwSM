/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : yolo

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-05-05 11:36:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_dd
-- ----------------------------
DROP TABLE IF EXISTS `${module}_${componentLower}`;
CREATE TABLE `${module}_${componentLower}` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
  <#list entity?keys as key>
  	<#if entity[key] == "Integer">
    `${key}` int(11),
    <#elseif entity[key] == "java.sql.Timestamp"> 
    `${key}` timestamp NULL DEFAULT NULL,
    <#else>
    `${key}` varchar(20) DEFAULT NULL,
	</#if>
  </#list> 
    
  `createTime` timestamp NOT NULL,
  `lastUpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createUser` varchar(255) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8;