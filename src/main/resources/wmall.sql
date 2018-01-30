DROP TABLE IF EXISTS `t_deliver`;
CREATE TABLE `t_deliver` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proxy_id` int(11) NOT NULL DEFAULT '0' COMMENT '代理ID',
  `parent_proxy_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级代理ID',
  `authorization_code` varchar(20) DEFAULT NULL COMMENT '授权码',
  `receive_name` varchar(10) NOT NULL COMMENT '收件人姓名',
  `receive_phone` varchar(11) NOT NULL COMMENT '收件人电话',
  `receive_address` varchar(200) NOT NULL COMMENT '收件人地址',
  `courier_price` int(11) NOT NULL DEFAULT '0' COMMENT '快递费',
  `deliver_type` tinyint(4) NOT NULL COMMENT '发货类型：1自己发货，2老大发货，3工厂发货',
  `deliver_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发货状态：0未发货，1已发货',
  `inventory_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '对货状态：0未对货，1已对货',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_deliver` (`proxy_id`,`parent_proxy_id`,`deliver_type`,`deliver_status`,`inventory_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发货';


DROP TABLE IF EXISTS `t_deliver_detail`;
CREATE TABLE `t_deliver_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deliver_id` int(11) NOT NULL COMMENT '发货单ID',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `amount` int(11) NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `idx_deliver_detail` (`deliver_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发货明细';


DROP TABLE IF EXISTS `t_inventory`;
CREATE TABLE `t_inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proxy_id` int(11) NOT NULL COMMENT '代理id',
  `details` varchar(200) NOT NULL COMMENT '对货详情',
  `balance` int(11) NOT NULL DEFAULT '0' COMMENT '钱包余额',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_proxy_id` (`proxy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对货单';


DROP TABLE IF EXISTS `t_logistics`;
CREATE TABLE `t_logistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deliver_id` int(11) NOT NULL COMMENT '发货单ID',
  `company` varchar(20) DEFAULT NULL COMMENT '公司',
  `number` varchar(50) DEFAULT NULL COMMENT '单号',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_logistics` (`deliver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流';


DROP TABLE IF EXISTS `t_memo`;
CREATE TABLE `t_memo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1提醒，2警告，3重要，4完成',
  `title` varchar(20) NOT NULL COMMENT '标题',
  `content` varchar(500) NOT NULL COMMENT '内容',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_memo` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备忘录';


DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proxy_id` int(11) NOT NULL DEFAULT '0' COMMENT '代理ID',
  `parent_proxy_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级代理ID',
  `order_type` tinyint(4) NOT NULL COMMENT '订单类型：1代理订单，2零售订单',
  `order_price` int(11) NOT NULL COMMENT '订单价格',
  `preferential_price` int(11) NOT NULL DEFAULT '0' COMMENT '优惠价格',
  `is_accumulate` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否累积：1是，0否',
  `nature_month` varchar(7) NOT NULL COMMENT '自然月（yyyy-mm）',
  `order_status` tinyint(4) NOT NULL DEFAULT '4' COMMENT '订单状态：1未支付，2已支付，3已取消，4已完成',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_order` (`proxy_id`,`parent_proxy_id`,`order_type`,`is_accumulate`,`nature_month`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `amount` int(11) NOT NULL COMMENT '数量',
  `unit_price` decimal(11,1) NOT NULL COMMENT '单价',
  `total_price` int(11) NOT NULL COMMENT '总价',
  `give` int(11) NOT NULL DEFAULT '0' COMMENT '赠送',
  PRIMARY KEY (`id`),
  KEY `idx_order_detail` (`order_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细';


DROP TABLE IF EXISTS `t_price`;
CREATE TABLE `t_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `price_type` tinyint(4) NOT NULL COMMENT '价格类型：1代理价，2零售价',
  `amount` int(11) NOT NULL COMMENT '数量',
  `unit_price` decimal(11,1) NOT NULL COMMENT '单价',
  `total_price` int(11) NOT NULL COMMENT '总价',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='价格';

INSERT INTO `t_price` VALUES ('1', '2', '2', '1', '60.0', '60', '2', '2017-05-28 21:29:50', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('2', '2', '1', '20', '44.0', '880', '2', '2017-05-28 21:30:15', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('3', '2', '1', '50', '42.0', '2100', '2', '2017-05-28 21:30:22', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('4', '2', '1', '100', '40.0', '4000', '2', '2017-05-28 21:30:30', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('5', '2', '1', '300', '37.0', '11100', '2', '2017-05-28 21:30:40', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('6', '2', '1', '500', '34.0', '17000', '2', '2017-05-28 21:30:48', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('7', '2', '1', '1000', '30.0', '30000', '2', '2017-05-28 21:30:56', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('8', '3', '2', '1', '68.0', '68', '2', '2017-05-28 21:31:13', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('9', '3', '1', '20', '48.0', '960', '2', '2017-05-28 21:31:24', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('10', '3', '1', '50', '45.0', '2250', '2', '2017-05-28 21:31:34', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('11', '3', '1', '100', '42.0', '4200', '2', '2017-05-28 21:31:43', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('12', '3', '1', '300', '40.0', '12000', '2', '2017-05-28 21:31:52', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('13', '3', '1', '500', '38.0', '19000', '2', '2017-05-28 21:31:59', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('14', '3', '1', '1000', '35.0', '35000', '2', '2017-05-28 21:32:07', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('15', '4', '2', '1', '138.0', '138', '2', '2017-05-28 21:32:27', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('16', '4', '1', '10', '98.0', '980', '2', '2017-05-28 21:32:48', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('17', '4', '1', '50', '90.0', '4500', '2', '2017-05-28 21:32:59', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('18', '4', '1', '100', '82.0', '8200', '2', '2017-05-28 21:33:07', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('19', '4', '1', '300', '78.0', '23400', '2', '2017-05-28 21:33:16', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('20', '4', '1', '500', '74.0', '37000', '2', '2017-05-28 21:33:25', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('21', '4', '1', '1000', '66.0', '66000', '2', '2017-05-28 21:33:33', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('22', '5', '2', '1', '158.0', '158', '2', '2017-05-28 21:33:52', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('23', '5', '1', '10', '98.0', '980', '2', '2017-05-28 21:34:01', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('24', '5', '1', '50', '88.0', '4400', '2', '2017-05-28 21:34:12', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('25', '5', '1', '100', '78.0', '7800', '2', '2017-05-28 21:34:20', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('26', '5', '1', '300', '68.0', '20400', '2', '2017-05-28 21:34:31', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('27', '5', '1', '500', '58.0', '29000', '2', '2017-05-28 21:34:38', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('28', '5', '1', '1000', '50.0', '50000', '2', '2017-05-28 21:34:48', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('29', '6', '2', '1', '168.0', '168', '2', '2017-05-28 21:35:06', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('30', '6', '1', '10', '118.0', '1180', '2', '2017-05-28 21:35:26', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('31', '6', '1', '50', '108.0', '5400', '2', '2017-05-28 21:35:34', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('32', '6', '1', '100', '98.0', '9800', '2', '2017-05-28 21:35:42', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('33', '6', '1', '300', '88.0', '26400', '2', '2017-05-28 21:35:49', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('34', '6', '1', '500', '78.0', '39000', '2', '2017-05-28 21:35:54', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('35', '6', '1', '1000', '68.0', '68000', '2', '2017-05-28 21:36:02', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('36', '7', '2', '1', '198.0', '198', '2', '2017-05-28 21:36:21', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('37', '7', '1', '10', '138.0', '1380', '2', '2017-05-28 21:36:28', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('38', '7', '1', '50', '128.0', '6400', '2', '2017-05-28 21:36:36', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('39', '7', '1', '100', '118.0', '11800', '2', '2017-05-28 21:36:43', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('40', '7', '1', '300', '113.0', '33900', '2', '2017-05-28 21:36:50', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('41', '7', '1', '500', '108.0', '54000', '2', '2017-05-28 21:36:59', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('42', '7', '1', '1000', '98.0', '98000', '2', '2017-05-28 21:37:11', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('43', '8', '2', '1', '158.0', '158', '2', '2017-05-28 21:37:27', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('44', '8', '1', '10', '108.0', '1080', '2', '2017-05-28 21:37:39', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('45', '8', '1', '50', '100.0', '5000', '2', '2017-05-28 21:37:50', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('46', '8', '1', '100', '92.0', '9200', '2', '2017-05-28 21:37:58', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('47', '8', '1', '300', '88.0', '26400', '2', '2017-05-28 21:38:08', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('48', '8', '1', '500', '84.0', '42000', '2', '2017-05-28 21:38:16', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('49', '8', '1', '1000', '76.0', '76000', '2', '2017-05-28 21:38:24', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('50', '9', '2', '1', '88.0', '88', '2', '2017-05-28 21:38:40', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('51', '9', '1', '10', '68.0', '680', '2', '2017-05-28 21:38:49', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('52', '9', '1', '50', '63.0', '3150', '2', '2017-05-28 21:38:58', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('53', '9', '1', '100', '58.0', '5800', '2', '2017-05-28 21:39:08', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('54', '9', '1', '300', '55.5', '16650', '2', '2017-05-28 21:39:16', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('55', '9', '1', '500', '53.0', '26500', '2', '2017-05-28 21:39:27', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('56', '9', '1', '1000', '48.0', '48000', '2', '2017-05-28 21:39:34', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('57', '10', '2', '1', '188.0', '188', '2', '2017-05-28 21:39:52', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('58', '10', '1', '10', '130.0', '1300', '2', '2017-05-28 21:40:01', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('59', '10', '1', '50', '120.0', '6000', '2', '2017-05-28 21:40:08', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('60', '10', '1', '100', '100.0', '10000', '2', '2017-05-28 21:40:17', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('61', '10', '1', '300', '95.0', '28500', '2', '2017-05-28 21:40:24', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('62', '10', '1', '500', '90.0', '45000', '2', '2017-05-28 21:40:32', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('63', '10', '1', '1000', '80.0', '80000', '2', '2017-05-28 21:40:39', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('64', '11', '2', '1', '78.0', '78', '2', '2017-05-28 21:40:53', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('65', '11', '1', '20', '48.0', '960', '2', '2017-05-28 21:41:00', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('66', '11', '1', '30', '46.0', '1380', '2', '2017-05-28 21:41:22', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('67', '11', '1', '50', '44.0', '2200', '2', '2017-05-28 21:41:28', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('68', '11', '1', '100', '42.0', '4200', '2', '2017-05-28 21:41:36', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('69', '11', '1', '300', '40.0', '12000', '2', '2017-05-28 21:41:43', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('70', '11', '1', '500', '38.0', '19000', '2', '2017-05-28 21:41:51', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('71', '11', '1', '1000', '35.0', '35000', '2', '2017-05-28 21:42:00', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('72', '12', '2', '1', '68.0', '68', '2', '2017-06-10 20:45:42', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('73', '12', '1', '20', '44.0', '880', '2', '2017-06-10 20:45:56', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('74', '12', '1', '50', '42.0', '2100', '2', '2017-06-10 20:46:04', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('75', '12', '1', '100', '40.0', '4000', '2', '2017-06-10 20:46:14', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('76', '12', '1', '300', '37.0', '11100', '2', '2017-06-10 20:46:24', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('77', '12', '1', '500', '34.0', '17000', '2', '2017-06-10 20:46:32', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('78', '12', '1', '1000', '30.0', '30000', '2', '2017-06-10 20:46:42', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('79', '12', '1', '2000', '29.5', '59000', '2', '2017-06-10 20:47:39', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('80', '12', '1', '3000', '29.0', '87000', '2', '2017-06-10 20:47:48', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('81', '12', '1', '4000', '28.0', '112000', '2', '2017-06-10 20:47:59', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('82', '12', '1', '5000', '27.0', '135000', '2', '2017-06-10 20:48:09', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('83', '12', '1', '8000', '26.0', '208000', '2', '2017-06-10 20:48:50', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('84', '12', '1', '10000', '25.0', '250000', '2', '2017-06-10 20:49:00', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('85', '11', '1', '3000', '33.0', '99000', '2', '2017-06-10 20:50:18', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('86', '11', '1', '5000', '31.0', '155000', '2', '2017-06-10 20:50:28', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('87', '11', '1', '10000', '28.0', '280000', '2', '2017-06-10 20:50:37', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('88', '11', '1', '30000', '26.0', '780000', '2', '2017-06-10 20:50:48', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('89', '11', '1', '50000', '24.0', '1200000', '2', '2017-06-10 20:50:59', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('90', '11', '1', '100000', '22.0', '2200000', '2', '2017-06-10 20:51:11', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('91', '11', '1', '200000', '20.0', '4000000', '2', '2017-06-10 20:51:23', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('92', '13', '2', '1', '68.0', '68', '2', '2017-07-23 16:26:54', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('93', '13', '1', '20', '48.0', '960', '2', '2017-07-23 16:27:07', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('94', '13', '1', '50', '45.0', '2250', '2', '2017-07-23 16:27:12', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('95', '13', '1', '100', '42.0', '4200', '2', '2017-07-23 16:27:18', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('96', '13', '1', '300', '40.0', '12000', '2', '2017-07-23 16:27:23', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('97', '13', '1', '500', '38.0', '19000', '2', '2017-07-23 16:27:28', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('98', '13', '1', '1000', '35.0', '35000', '2', '2017-07-23 16:27:34', '2', '2017-11-25 21:07:23', '0');
INSERT INTO `t_price` VALUES ('99', '12', '1', '20000', '24.5', '490000', '2', '2017-12-03 21:48:40', '2', '2017-12-03 21:48:40', '0');
INSERT INTO `t_price` VALUES ('100', '12', '1', '30000', '24.0', '720000', '2', '2017-12-03 21:49:00', '2', '2017-12-03 21:49:00', '0');
INSERT INTO `t_price` VALUES ('101', '12', '1', '40000', '23.0', '920000', '2', '2017-12-03 21:49:15', '2', '2017-12-03 21:49:15', '0');
INSERT INTO `t_price` VALUES ('102', '12', '1', '50000', '22.0', '1100000', '2', '2017-12-03 21:49:27', '2', '2017-12-03 21:49:27', '0');
INSERT INTO `t_price` VALUES ('103', '12', '1', '80000', '21.0', '1680000', '2', '2017-12-03 21:49:40', '2', '2017-12-03 21:49:40', '0');
INSERT INTO `t_price` VALUES ('104', '12', '1', '100000', '20.0', '2000000', '2', '2017-12-03 21:49:53', '2', '2017-12-03 21:49:53', '0');


DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_type` tinyint(4) NOT NULL COMMENT '产品类型：1代理产品，2赠品',
  `product_name` varchar(20) NOT NULL COMMENT '产品名称',
  `product_desc` varchar(200) DEFAULT NULL COMMENT '产品描述',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_product_type` (`product_type`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='产品';

INSERT INTO `t_product` VALUES ('1', '2', '项链', '项链', '2', '2017-05-28 21:27:01', '2', '2017-05-28 21:27:01', '0');
INSERT INTO `t_product` VALUES ('2', '1', '嗖啦', '珍嗖啦酵素果冻', '2', '2017-05-28 21:27:02', '2', '2017-05-28 21:27:02', '0');
INSERT INTO `t_product` VALUES ('3', '1', '米昔', '米昔营养代餐粉', '2', '2017-05-28 21:27:03', '2', '2017-05-28 21:27:03', '0');
INSERT INTO `t_product` VALUES ('4', '1', '按摩膏', '毛孔清洁按摩膏', '2', '2017-05-28 21:27:04', '2', '2017-05-28 21:27:04', '0');
INSERT INTO `t_product` VALUES ('5', '1', '洁面乳', '全能美颜深层洁面乳', '2', '2017-05-28 21:28:09', '2', '2017-05-28 21:28:09', '0');
INSERT INTO `t_product` VALUES ('6', '1', '修护水', '冰岛蓝湖之泉万能修护水', '2', '2017-05-28 21:28:17', '2', '2017-05-28 21:28:17', '0');
INSERT INTO `t_product` VALUES ('7', '1', '丝滑乳', '水光肌蜜丝滑乳', '2', '2017-05-28 21:28:25', '2', '2017-05-28 21:28:25', '0');
INSERT INTO `t_product` VALUES ('8', '1', '气垫', '水光凝采气垫BB霜', '2', '2017-05-28 21:28:33', '2', '2017-05-28 21:28:33', '0');
INSERT INTO `t_product` VALUES ('9', '1', '隔离乳', '芭比冰肌防晒隔离乳', '2', '2017-05-28 21:28:41', '2', '2017-05-28 21:28:41', '0');
INSERT INTO `t_product` VALUES ('10', '1', '面膜', '水肌透润面膜', '2', '2017-05-28 21:28:47', '2', '2017-05-28 21:28:47', '0');
INSERT INTO `t_product` VALUES ('11', '1', '牙美', '牙美清新洁白慕斯', '2', '2017-05-28 21:28:56', '2', '2017-05-28 21:28:56', '0');
INSERT INTO `t_product` VALUES ('12', '1', '女皇', '一代女皇益生菌酵母软糖', '2', '2017-06-10 20:45:21', '2', '2017-06-10 20:45:21', '0');
INSERT INTO `t_product` VALUES ('13', '1', '代餐棒', '燕窝代餐棒', '2', '2017-07-23 16:25:48', '2', '2017-07-23 16:25:48', '0');
INSERT INTO `t_product` VALUES ('14', '2', '玻尿酸', '玻尿酸', '2', '2017-11-11 12:20:36', '2', '2017-11-11 12:20:36', '0');
INSERT INTO `t_product` VALUES ('15', '2', '护手霜', '护手霜', '2', '2017-11-11 12:20:43', '2', '2017-11-11 12:20:43', '0');


DROP TABLE IF EXISTS `t_proxy`;
CREATE TABLE `t_proxy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `wechat_number` varchar(20) DEFAULT NULL COMMENT '微信号',
  `wechat_name` varchar(20) NOT NULL COMMENT '微信昵称',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别：1男，0女',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `address` varchar(200) NOT NULL COMMENT '地址',
  `proxy_price` int(11) NOT NULL DEFAULT '0' COMMENT '代理价',
  `id_number` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `authorization_code` varchar(20) DEFAULT NULL COMMENT '授权码',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE,
  KEY `idx_proxy` (`parent_id`,`wechat_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理';


DROP TABLE IF EXISTS `t_proxy_level`;
CREATE TABLE `t_proxy_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proxy_id` int(11) NOT NULL DEFAULT '0' COMMENT '代理ID',
  `price` int(11) NOT NULL COMMENT '累计货款',
  `level` tinyint(4) NOT NULL COMMENT '级别：1小咖，2中咖，3大咖，4顶级大咖，5带砖大咖',
  `month` varchar(7) NOT NULL COMMENT '月份',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_proxy_level` (`proxy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理级别';


DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proxy_id` int(11) NOT NULL COMMENT '代理人ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `type` tinyint(4) NOT NULL COMMENT '用户类型：1管理员，2代理人员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) USING BTREE,
  UNIQUE KEY `uk_proxy_id` (`proxy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户';

INSERT INTO `t_user` VALUES ('1', '0', 'root', '63a9f0ea7bb98050796b649e85481845', '1', '2017-10-27 16:39:32', '2017-10-27 16:39:40', '0');
INSERT INTO `t_user` VALUES ('2', '1', 'shanshan', '806af51386341a2981ce0c0de163fda9', '2', '2017-10-30 21:47:02', '2017-10-30 21:47:16', '0');


DROP TABLE IF EXISTS `t_wallet`;
CREATE TABLE `t_wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proxy_id` int(11) NOT NULL COMMENT '代理ID',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID',
  `price` int(11) NOT NULL COMMENT '金额',
  `type` tinyint(4) NOT NULL COMMENT '类型：1存入，2支出',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` int(11) NOT NULL COMMENT '修改人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除：1删除，0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_wallet` (`proxy_id`,`order_id`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='钱包';