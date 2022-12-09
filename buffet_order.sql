/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : buffet_order

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2022-12-09 17:36:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `address`
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `address_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `address_user_id` (`user_id`),
  CONSTRAINT `address_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO address VALUES ('1', '23', '郑州');

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `password` char(60) NOT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `is_enable` tinyint(1) NOT NULL DEFAULT '1',
  `role` int(10) NOT NULL DEFAULT '1',
  PRIMARY KEY (`admin_id`),
  KEY `role` (`role`),
  CONSTRAINT `role` FOREIGN KEY (`role`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO admin VALUES ('1', 'admin', '$2a$10$Bfpq.06kCG8/INuOc2oEKuzryrjVsa0pMH9XAH1cLV7WojAecDH/C', 'http://localhost:8080/BuffetOrder/img/avatar/idea.png', '1', '2');
INSERT INTO admin VALUES ('2', 'assistant', '$2a$10$tTpn6nfCRXMWH//ile.YJ.e4DyDnp6SO0Yi6wWp4W6c/sf5Bxx3uC', 'http://localhost:8080/BuffetOrder/img/avatar/pycharm.png', '1', '1');

-- ----------------------------
-- Table structure for `cate`
-- ----------------------------
DROP TABLE IF EXISTS `cate`;
CREATE TABLE `cate` (
  `cate_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '分类Id',
  `cate_name` varchar(20) DEFAULT NULL COMMENT '分类名',
  `cate_weight` int(3) NOT NULL DEFAULT '0' COMMENT '分类权重',
  `is_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cate
-- ----------------------------
INSERT INTO cate VALUES ('1', '未分类', '0', '1');
INSERT INTO cate VALUES ('2', '测试分类1', '0', '1');
INSERT INTO cate VALUES ('3', '测试2', '0', '1');
INSERT INTO cate VALUES ('4', '测试3', '0', '1');
INSERT INTO cate VALUES ('5', '测试4', '0', '1');
INSERT INTO cate VALUES ('6', '测试5', '0', '1');
INSERT INTO cate VALUES ('7', '测试6', '0', '1');
INSERT INTO cate VALUES ('8', '假设这个分类被删除了', '0', '0');

-- ----------------------------
-- Table structure for `detail`
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
  `detail_id` int(10) NOT NULL AUTO_INCREMENT,
  `detail_name` varchar(20) DEFAULT NULL,
  `detail_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `detail_type` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of detail
-- ----------------------------
INSERT INTO detail VALUES ('1', '这是配料A', '1.00', '0');
INSERT INTO detail VALUES ('2', '这是配料B', '2.00', '0');
INSERT INTO detail VALUES ('3', '这是配料C', '3.00', '0');
INSERT INTO detail VALUES ('4', '不辣', '0.00', '1');
INSERT INTO detail VALUES ('5', '微辣', '0.00', '1');
INSERT INTO detail VALUES ('6', '特辣', '0.00', '1');
INSERT INTO detail VALUES ('7', '小份', '0.00', '1');
INSERT INTO detail VALUES ('8', '中份', '1.00', '1');
INSERT INTO detail VALUES ('9', '大份', '2.00', '1');
INSERT INTO detail VALUES ('10', '这是配料D', '4.00', '0');

-- ----------------------------
-- Table structure for `food`
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `food_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '食物id',
  `food_name` varchar(20) DEFAULT NULL COMMENT '食物名',
  `food_img` varchar(200) DEFAULT NULL COMMENT '食物图片地址',
  `food_cate` int(10) NOT NULL DEFAULT '1' COMMENT '食物分类 外键',
  `have_detail` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有细节',
  `food_detail` varchar(512) DEFAULT NULL COMMENT '细节 json',
  `food_note` varchar(20) DEFAULT NULL COMMENT '食物注释',
  `food_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `food_weight` int(3) NOT NULL DEFAULT '0' COMMENT '权重',
  `is_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`food_id`),
  KEY `food_cate` (`food_cate`) USING BTREE,
  CONSTRAINT `food_cate` FOREIGN KEY (`food_cate`) REFERENCES `cate` (`cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO food VALUES ('1', '测试食1', 'http://localhost:8080/BuffetOrder/img/food/1.png', '2', '1', '{\"dM\":{\"mI\":\"选择配料\",\"mS\":[{\"n\":\"这是配料A\",\"s\":0,\"v\":1},{\"n\":\"这是配料B\",\"s\":0,\"v\":2},{\"n\":\"这是配料C\",\"s\":0,\"v\":3},{\"n\":\"这是配料D\",\"s\":0,\"v\":4}]},\"dR\":[{\"rI\":\"选择大小\",\"rS\":[{\"n\":\"小份\",\"s\":1,\"v\":0},{\"n\":\"中份\",\"s\":0,\"v\":1},{\"n\":\"大份\",\"s\":0,\"v\":2}]}]}', '测试备注1', '1.01', '0', '1');
INSERT INTO food VALUES ('2', '测试食物2', 'http://localhost:8080/BuffetOrder/img/food/2.png', '1', '1', '{\"dM\":{\"mI\":\"选择配料\",\"mS\":[{\"n\":\"这是配料A\",\"s\":0,\"v\":1},{\"n\":\"这是配料B\",\"s\":0,\"v\":2},{\"n\":\"这是配料C\",\"s\":0,\"v\":3}]},\"dR\":[{\"rI\":\"选择大小\",\"rS\":[{\"n\":\"小份\",\"s\":1,\"v\":0},{\"n\":\"中份\",\"s\":0,\"v\":1},{\"n\":\"大份\",\"s\":0,\"v\":2}]},{\"rI\":\"选择辣\",\"rS\":[{\"n\":\"不辣\",\"s\":1,\"v\":0},{\"n\":\"微辣\",\"s\":0,\"v\":0},{\"n\":\"特辣\",\"s\":0,\"v\":0}]}]}', '测试备注2', '3.32', '999', '1');
INSERT INTO food VALUES ('3', '测试3', 'http://localhost:8080/BuffetOrder/img/food/3.png', '1', '1', '{\"dM\":{\"mI\":\"选择配料\",\"mS\":[{\"n\":\"这是配料A\",\"s\":0,\"v\":1},{\"n\":\"这是配料B\",\"s\":0,\"v\":2},{\"n\":\"这是配料C\",\"s\":0,\"v\":3}]},\"dR\":[]}', '测试备注3', '5.00', '0', '1');
INSERT INTO food VALUES ('4', '测试4', 'http://localhost:8080/BuffetOrder/img/food/4.png', '1', '0', null, null, '2.22', '0', '1');
INSERT INTO food VALUES ('5', '测试5', 'http://localhost:8080/BuffetOrder/img/food/5.png', '2', '0', null, null, '1.00', '0', '1');
INSERT INTO food VALUES ('6', '测试食物6', 'http://localhost:8080/BuffetOrder/img/food/6.png', '7', '0', null, '436', '2.00', '0', '1');
INSERT INTO food VALUES ('7', '测试食物7', 'http://localhost:8080/BuffetOrder/img/food/7.png', '1', '1', '{\"dM\":{\"mS\":[],\"mId\":1,\"mI\":\"选择配料\"},\"dR\":[{\"rI\":\"选择大小\",\"rS\":[{\"n\":\"小份\",\"s\":1,\"v\":0},{\"n\":\"中份\",\"s\":0,\"v\":1},{\"n\":\"大份\",\"s\":0,\"v\":2}]}]}', '', '2.00', '0', '1');
INSERT INTO food VALUES ('8', '测试食物8', 'http://localhost:8080/BuffetOrder/img/food/8.png?time=1668236404436', '2', '1', '{\"dM\":{\"mI\":\"选择配料\",\"mS\":[{\"n\":\"这是配料A\",\"s\":0,\"v\":1},{\"n\":\"这是配料B\",\"s\":0,\"v\":2}]},\"dR\":[]}', '', '1.00', '0', '1');
INSERT INTO food VALUES ('9', '测试食物9', 'http://localhost:8080/BuffetOrder/img/food/9.png', '1', '1', '{\"dM\":{\"mI\":\"选择配料\",\"mS\":[{\"n\":\"这是配料A\",\"s\":0,\"v\":1},{\"n\":\"这是配料B\",\"s\":0,\"v\":2}]},\"dR\":[{\"rI\":\"辣度\",\"rS\":[{\"n\":\"不辣\",\"s\":1,\"v\":0},{\"n\":\"微辣\",\"s\":0,\"v\":0},{\"n\":\"特辣\",\"s\":0,\"v\":0}]}]}', '1', '4.00', '0', '1');

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(10) NOT NULL COMMENT '用户id 外键',
  `order_way` tinyint(1) NOT NULL DEFAULT '0' COMMENT '配送方式',
  `order_address` varchar(255) DEFAULT NULL COMMENT '地址',
  `order_create_time` datetime NOT NULL COMMENT '创建时间',
  `order_json_body` text COMMENT '订单内容 json',
  `order_get_numb` varchar(4) DEFAULT NULL COMMENT '取餐号',
  `order_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `order_note` varchar(255) DEFAULT NULL COMMENT '订单注释',
  `order_should_pay` decimal(10,2) DEFAULT NULL COMMENT '应付金额',
  `order_real_pay` decimal(10,2) DEFAULT NULL COMMENT '实付金额',
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO order VALUES ('45', '23', '0', '地址', '2022-10-24 16:06:39', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":4,\"pr\":4.04},{\"de\":\"这是配料B 中份 微辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32},{\"de\":\"这是配料A 这是配料B 这是配料C \",\"id\":3,\"na\":\"测试3\",\"nu\":1,\"pr\":11.00}]', '1', '1', '无', '21.36', '21.36');
INSERT INTO order VALUES ('46', '23', '0', '地址', '2022-10-24 16:07:19', '[{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":2,\"pr\":4.44},{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', '1', '2', '无', '6.46', null);
INSERT INTO order VALUES ('47', '23', '0', '地址', '2022-10-25 13:45:52', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', '1', '2', '无', '2.02', '2.02');
INSERT INTO order VALUES ('48', '23', '0', '地址', '2022-10-25 13:48:03', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', '1', '2', '无', '2.02', '2.02');
INSERT INTO order VALUES ('49', '23', '0', '地址', '2022-10-25 14:17:48', '[{\"de\":\"这是配料B 中份 特辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32},{\"de\":\"这是配料B 中份 特辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32}]', '1', '2', '无', '12.64', '12.64');
INSERT INTO order VALUES ('50', '23', '0', '地址', '2022-10-26 02:13:34', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '1', '2', '无', '18.66', null);
INSERT INTO order VALUES ('51', '23', '0', '地址', '2022-10-26 03:50:03', '[{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '1', '2', '无', '16.64', null);
INSERT INTO order VALUES ('52', '23', '0', '地址', '2022-10-26 03:50:12', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02},{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":2,\"pr\":4.44}]', '1', '0', '无', '6.46', null);
INSERT INTO order VALUES ('53', '23', '0', '地址', '2022-10-26 03:50:19', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02},{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":3,\"pr\":6.66}]', '1', '0', '无', '8.68', null);
INSERT INTO order VALUES ('54', '23', '0', '地址', '2022-10-26 09:23:49', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', '1', '0', '无', '2.02', null);
INSERT INTO order VALUES ('55', '23', '0', '地址', '2022-10-26 09:23:54', '[{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":1,\"pr\":2.22}]', '1', '0', '无', '2.22', null);
INSERT INTO order VALUES ('56', '23', '0', '地址', '2022-10-26 09:24:02', '[{\"de\":\"这是配料C 大份 特辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":1,\"pr\":2.22}]', '1', '0', '无', '10.54', null);
INSERT INTO order VALUES ('57', '23', '0', '地址', '2022-10-26 09:24:09', '[{\"de\":\"这是配料B 中份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32}]', '1', '0', '无', '6.32', null);
INSERT INTO order VALUES ('58', '23', '0', '地址', '2022-10-26 09:24:14', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', '1', '0', '无', '2.02', null);
INSERT INTO order VALUES ('59', '23', '0', '地址', '2022-10-26 09:24:20', '[{\"de\":\"这是配料B 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":7.32},{\"de\":\"这是配料B 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":7.32}]', '1', '0', '无', '14.64', null);
INSERT INTO order VALUES ('60', '23', '0', '地址', '2022-10-26 14:10:54', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料B 这是配料C \",\"id\":3,\"na\":\"测试3\",\"nu\":1,\"pr\":10.00},{\"de\":\"这是配料B \",\"id\":3,\"na\":\"测试3\",\"nu\":1,\"pr\":7.00}]', '1', '3', '无', '27.34', '27.34');
INSERT INTO order VALUES ('61', '23', '0', '地址', '2022-10-30 21:21:47', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '1', '0', '无', '10.34', null);
INSERT INTO order VALUES ('62', '23', '0', null, '2022-10-30 23:36:47', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', '1', '0', '无', '2.02', null);
INSERT INTO order VALUES ('63', '23', '1', '这是一个测试地址', '2022-10-31 00:12:19', '[{\"de\":\"无\",\"id\":1,\"na\":\"测试食物1\",\"nu\":2,\"pr\":2.02}]', null, '4', '无', '2.02', null);
INSERT INTO order VALUES ('64', '23', '0', null, '2022-11-07 17:06:30', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32},{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '4', '无', '10.64', null);
INSERT INTO order VALUES ('65', '23', '0', null, '2022-11-07 17:07:12', '[{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":2,\"pr\":4.44}]', '0002', '2', '无', '4.44', '4.44');
INSERT INTO order VALUES ('66', '23', '0', null, '2022-11-07 19:11:59', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32},{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '0', '无', '10.64', null);
INSERT INTO order VALUES ('67', '23', '0', null, '2022-11-07 19:13:20', '[{\"de\":\"这是配料B 这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料B 这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '0002', '4', '无', '16.64', null);
INSERT INTO order VALUES ('68', '23', '0', null, '2022-11-07 20:37:18', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32},{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '0', '无', '10.64', null);
INSERT INTO order VALUES ('69', '23', '0', null, '2022-11-07 20:38:46', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0002', '0', '无', '5.32', null);
INSERT INTO order VALUES ('70', '23', '0', null, '2022-11-07 20:53:04', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '0', '无', '5.32', null);
INSERT INTO order VALUES ('71', '23', '0', null, '2022-11-07 20:53:34', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0002', '0', '无', '5.32', null);
INSERT INTO order VALUES ('72', '23', '0', null, '2022-11-07 20:57:42', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '0', '无', '5.32', null);
INSERT INTO order VALUES ('73', '23', '0', null, '2022-11-07 21:08:00', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0002', '0', '无', '5.32', null);
INSERT INTO order VALUES ('74', '23', '0', null, '2022-11-07 21:20:56', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '0', '无', '5.32', null);
INSERT INTO order VALUES ('75', '23', '0', null, '2022-11-07 21:21:03', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0002', '0', '无', '5.32', null);
INSERT INTO order VALUES ('76', '23', '0', null, '2022-11-07 21:21:10', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0003', '0', '无', '5.32', null);
INSERT INTO order VALUES ('77', '23', '1', '郑州', '2022-11-07 21:21:41', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', null, '0', '无', '5.32', null);
INSERT INTO order VALUES ('78', '23', '0', null, '2022-11-07 22:19:13', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0001', '1', '无', '5.32', '5.32');
INSERT INTO order VALUES ('79', '23', '0', null, '2022-11-07 22:23:44', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0002', '1', '无', '5.32', '5.32');
INSERT INTO order VALUES ('80', '23', '0', null, '2022-11-07 22:27:37', '[{\"de\":\"这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":5.32}]', '0003', '1', '无', '5.32', '5.32');
INSERT INTO order VALUES ('81', '23', '0', null, '2022-11-07 22:28:41', '[{\"de\":\"这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32}]', '0004', '2', '无', '6.32', '6.32');
INSERT INTO order VALUES ('82', '23', '0', null, '2022-11-07 22:33:05', '[{\"de\":\"这是配料B 这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '0001', '3', '无', '8.32', '8.32');
INSERT INTO order VALUES ('83', '23', '0', null, '2022-11-11 20:48:50', '[{\"de\":\"无\",\"id\":5,\"na\":\"测试5\",\"nu\":2,\"pr\":2.00}]', '0001', '2', '无', '2.00', '2.00');
INSERT INTO order VALUES ('84', '23', '0', null, '2022-11-11 20:49:08', '[{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":2,\"pr\":4.44}]', '0002', '4', '无', '4.44', null);
INSERT INTO order VALUES ('85', '23', '0', null, '2022-12-08 14:10:31', '[{\"de\":\"这是配料A 这是配料B 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料A 这是配料B 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '0001', '0', '无', '16.64', null);
INSERT INTO order VALUES ('86', '23', '0', null, '2022-12-08 14:29:25', '[{\"de\":\"这是配料A 这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32},{\"de\":\"这是配料A 这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32}]', '0001', '4', '无', '12.64', null);
INSERT INTO order VALUES ('87', '23', '0', null, '2022-12-08 15:46:23', '[{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '0001', '4', '无', '16.64', null);
INSERT INTO order VALUES ('88', '23', '0', null, '2022-12-08 15:59:09', '[{\"de\":\"这是配料B 这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料B 这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32}]', '0002', '4', '无', '16.64', null);
INSERT INTO order VALUES ('89', '23', '0', null, '2022-12-08 16:04:54', '[{\"de\":\"这是配料A 这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32},{\"de\":\"这是配料A 这是配料B 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":6.32}]', '0001', '4', '无', '12.64', null);
INSERT INTO order VALUES ('90', '23', '0', null, '2022-12-08 16:41:29', '[{\"de\":\"这是配料B 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":7.32},{\"de\":\"这是配料B 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":7.32}]', '0002', '1', '无', '14.64', '14.64');
INSERT INTO order VALUES ('91', '23', '0', null, '2022-12-08 16:42:16', '[{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料B 这是配料C \",\"id\":3,\"na\":\"测试3\",\"nu\":1,\"pr\":10.00}]', '0003', '4', '无', '26.64', null);
INSERT INTO order VALUES ('92', '23', '0', null, '2022-12-08 16:42:46', '[{\"de\":\"这是配料B 这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":10.32},{\"de\":\"这是配料B 这是配料C 大份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":10.32},{\"de\":\"这是配料A 这是配料B \",\"id\":3,\"na\":\"测试3\",\"nu\":1,\"pr\":8.00},{\"de\":\"这是配料A 这是配料B \",\"id\":3,\"na\":\"测试3\",\"nu\":1,\"pr\":8.00},{\"de\":\"无\",\"id\":7,\"na\":\"\",\"nu\":2,\"pr\":2.00},{\"de\":\"无\",\"id\":9,\"na\":\"1\",\"nu\":2,\"pr\":2.00}]', '0004', '3', '无', '40.64', '40.64');
INSERT INTO order VALUES ('93', '23', '0', null, '2022-12-08 16:49:47', '[{\"de\":\"w q \",\"id\":1,\"na\":\"测试食12\",\"nu\":1,\"pr\":1.01},{\"de\":\"w q \",\"id\":1,\"na\":\"测试食12\",\"nu\":1,\"pr\":1.01}]', '0005', '1', '无', '2.02', '2.02');
INSERT INTO order VALUES ('94', '23', '0', null, '2022-12-08 19:22:54', '[{\"de\":\"无\",\"id\":9,\"na\":\"1\",\"nu\":3,\"pr\":3.00}]', '0001', '4', '无', '3.00', null);
INSERT INTO order VALUES ('95', '23', '0', null, '2022-12-08 22:05:25', '[{\"de\":\"这是配料A 这是配料B 这是配料C 这是配料D 大份 \",\"id\":1,\"na\":\"测试食1\",\"nu\":1,\"pr\":13.01},{\"de\":\"这是配料A 这是配料B 大份 特辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"这是配料A 这是配料B 大份 特辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":8.32},{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":2,\"pr\":4.44}]', '0001', '3', '无', '34.09', '34.09');
INSERT INTO order VALUES ('96', '23', '0', null, '2022-12-09 17:19:13', '[{\"de\":\"无\",\"id\":4,\"na\":\"测试4\",\"nu\":3,\"pr\":6.66},{\"de\":\"这是配料A 这是配料B 这是配料C 小份 不辣 \",\"id\":2,\"na\":\"测试食物2\",\"nu\":1,\"pr\":9.32},{\"de\":\"这是配料A 这是配料B 不辣 \",\"id\":9,\"na\":\"测试食物9\",\"nu\":1,\"pr\":7.00},{\"de\":\"这是配料A 特辣 \",\"id\":9,\"na\":\"测试食物9\",\"nu\":1,\"pr\":5.00}]', '0001', '0', '无', '27.98', null);

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('1', 'assistant');
INSERT INTO role VALUES ('2', 'admin');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `open_id` varchar(128) NOT NULL COMMENT '用户识别',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `avatar_path` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `is_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '钱',
  PRIMARY KEY (`user_id`,`open_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('22', 'odvvT5r44tZtpeQDjVAI6yQkC8Fk1', '微信用户', 'http://localhost:8080/BuffetOrder/img/avatar/8bca5dc5-5cc9-488a-9ae8-d94a047236af.png', '1', '0.00');
INSERT INTO user VALUES ('23', 'odvvT5r44tZtpeQDjVAI6yQkC8Fk', '???', 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0', '1', '1195.55');
