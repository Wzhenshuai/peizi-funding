/*
Navicat MySQL Data Transfer

Source Server         : icaopan
Source Server Version : 50524
Source Host           : 10.10.173.150:3306
Source Database       : icaopan_rebuld_test

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2016-11-29 19:15:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_menu`
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_parent` varchar(100) DEFAULT NULL COMMENT '菜单父ID',
  `menu_code` varchar(100) DEFAULT NULL COMMENT '菜单Code',
  `menu_name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单连接',
  `menu_clazz` varchar(100) DEFAULT NULL COMMENT '菜单等级',
  `menu_order` varchar(100) DEFAULT NULL COMMENT '菜单顺序',
  `menu_hidden` int(11) DEFAULT NULL COMMENT '是否显示 1 显示 0隐藏',
  `menu_style` varchar(100) DEFAULT NULL COMMENT '菜单样式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES ('2', '1', 'custom', '客户信息', '/custom/custom', '1', '1.1', '1', 'glyphicon glyphicon-lock');
INSERT INTO `admin_menu` VALUES ('3', '0', 'system_', '系统管理', '', '0', '2', '1', 'glyphicon glyphicon-cog');
INSERT INTO `admin_menu` VALUES ('4', '3', 'role', '角色管理', 'role/role', '1', '2.3', '1', ' glyphicon glyphicon-education');
INSERT INTO `admin_menu` VALUES ('5', '3', 'menu', '菜单管理', '/menu/menu', '1', '2.4', '1', 'glyphicon glyphicon-th-list');
INSERT INTO `admin_menu` VALUES ('6', '3', 'user', '系统用户', '/user/user', '1', '2.1', '1', 'glyphicon glyphicon-user');
INSERT INTO `admin_menu` VALUES ('7', '0', 'back_', '后台管理', '', '0', '2', '1', 'glyphicon glyphicon-th');
INSERT INTO `admin_menu` VALUES ('20', '7', 'custom', '客户管理', '/user/test', '1', '0.2', '1', 'glyphicon glyphicon-briefcase');
INSERT INTO `admin_menu` VALUES ('30', '3', 'druid', '系统监控', '/sys/goDruid', '1', '2', '1', 'glyphicon glyphicon-facetime-video');

-- ----------------------------
-- Table structure for `admin_permission`
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `menu_id` varchar(50) DEFAULT NULL COMMENT '权限连接',
  `permission_desc` varchar(50) DEFAULT NULL COMMENT '权限描述',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '权限码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_permission
-- ----------------------------
INSERT INTO `admin_permission` VALUES ('1', '菜单管理', '5', '菜单查询', 'find');
INSERT INTO `admin_permission` VALUES ('2', '菜单管理', '5', '菜单添加', 'save');
INSERT INTO `admin_permission` VALUES ('3', '菜单管理', '5', '菜单修改', 'update');
INSERT INTO `admin_permission` VALUES ('4', '菜单管理', '5', '菜单删除', 'delete');
INSERT INTO `admin_permission` VALUES ('5', '系统管理', '3', '一级菜单查看权限', 'find');
INSERT INTO `admin_permission` VALUES ('6', '系统用户', '6', '查询权限', 'find');
INSERT INTO `admin_permission` VALUES ('7', '系统用户', '6', '添加权限', 'save');
INSERT INTO `admin_permission` VALUES ('9', '系统用户', '6', '删除权限', 'delete');
INSERT INTO `admin_permission` VALUES ('10', '角色管理', '4', '添加权限', 'save');
INSERT INTO `admin_permission` VALUES ('11', '角色管理', '4', '删除权限', 'delete');
INSERT INTO `admin_permission` VALUES ('12', '角色修改', '4', '角色修改', 'update');
INSERT INTO `admin_permission` VALUES ('13', '角色管理', '4', '角色查询', 'find');
INSERT INTO `admin_permission` VALUES ('14', '后台管理', '7', '一级菜单查看权限', 'find');
INSERT INTO `admin_permission` VALUES ('38', '系统用户', '6', '删除权限', 'update');
INSERT INTO `admin_permission` VALUES ('47', '系统监控', '30', '查询权限', 'find');
INSERT INTO `admin_permission` VALUES ('48', '客户管理', '20', '', 'find');

-- ----------------------------
-- Table structure for `admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `roledesc` varchar(50) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '管理员', '管理员');
INSERT INTO `admin_role` VALUES ('15', '客服', '客服组');

-- ----------------------------
-- Table structure for `admin_role_relate_permission`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_relate_permission`;
CREATE TABLE `admin_role_relate_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色ID',
  `permission_id` varchar(50) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role_relate_permission
-- ----------------------------
INSERT INTO `admin_role_relate_permission` VALUES ('183', '15', '14');
INSERT INTO `admin_role_relate_permission` VALUES ('184', '15', '31');
INSERT INTO `admin_role_relate_permission` VALUES ('217', '1', '1');
INSERT INTO `admin_role_relate_permission` VALUES ('218', '1', '2');
INSERT INTO `admin_role_relate_permission` VALUES ('219', '1', '3');
INSERT INTO `admin_role_relate_permission` VALUES ('220', '1', '4');
INSERT INTO `admin_role_relate_permission` VALUES ('221', '1', '5');
INSERT INTO `admin_role_relate_permission` VALUES ('222', '1', '6');
INSERT INTO `admin_role_relate_permission` VALUES ('223', '1', '7');
INSERT INTO `admin_role_relate_permission` VALUES ('224', '1', '9');
INSERT INTO `admin_role_relate_permission` VALUES ('225', '1', '10');
INSERT INTO `admin_role_relate_permission` VALUES ('226', '1', '11');
INSERT INTO `admin_role_relate_permission` VALUES ('227', '1', '12');
INSERT INTO `admin_role_relate_permission` VALUES ('228', '1', '13');
INSERT INTO `admin_role_relate_permission` VALUES ('229', '1', '14');
INSERT INTO `admin_role_relate_permission` VALUES ('230', '1', '38');
INSERT INTO `admin_role_relate_permission` VALUES ('231', '1', '47');
INSERT INTO `admin_role_relate_permission` VALUES ('232', '1', '48');

-- ----------------------------
-- Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(200) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `status` char(1) DEFAULT NULL COMMENT '0正常，1锁定',
  `notes` varchar(45) DEFAULT NULL COMMENT '备注',
  `customer_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ号',
  `weixin` varchar(50) DEFAULT NULL COMMENT '微信号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `modify_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('999', 'zhangsan', 'zhangsan', null, null, null, null, null, '254983885', null, 'zhang@email.com', null, null);

-- ----------------------------
-- Table structure for `admin_user_relate_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_relate_role`;
CREATE TABLE `admin_user_relate_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user_relate_role
-- ----------------------------
INSERT INTO `admin_user_relate_role` VALUES ('9', '1', '1');

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '客户名称',
  `notes` varchar(1000) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '正常,停用,锁定',
  `balance` decimal(20,2) DEFAULT NULL COMMENT '帐户余额',
  `cost_pattern` varchar(45) DEFAULT NULL COMMENT '低消模式：1通道，2公司',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `min_cost` decimal(20,2) DEFAULT NULL COMMENT '最低消费',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改日期',
  `default_tatio` decimal(18,8) DEFAULT NULL COMMENT '用户默认费率',
  `default_min_cost` decimal(20,2) DEFAULT NULL COMMENT '用户默认低消',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('101', 'wahaha', '客户管理', '1', '300000.00', 'CHANNL', null, '200.00', null, '0.00030000', '1000000.00');

-- ----------------------------
-- Table structure for `customer_bill`
-- ----------------------------
DROP TABLE IF EXISTS `customer_bill`;
CREATE TABLE `customer_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `channel_id` varchar(45) DEFAULT NULL COMMENT '通道的主键',
  `operation_type` varchar(45) DEFAULT NULL COMMENT '操作类型：交易成交，月费扣除，增加，减少',
  `balance` decimal(20,2) DEFAULT NULL COMMENT '操作后的金额',
  `fill_amount` decimal(20,0) DEFAULT NULL COMMENT '成交金额',
  `fee` decimal(20,3) DEFAULT NULL COMMENT '费用',
  `operation_user` varchar(45) DEFAULT NULL COMMENT '操作用户',
  `operation_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_c_customer_bill_c_customer1_idx` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=utf8 COMMENT='客户账单';

-- ----------------------------
-- Records of customer_bill
-- ----------------------------
INSERT INTO `customer_bill` VALUES ('338', '20001', 'DEDUCTIONBYMONTH', '99740.00', '3000', '-300.000', '张三', null, '101');

-- ----------------------------
-- Table structure for `stock_pool`
-- ----------------------------
DROP TABLE IF EXISTS `stock_pool`;
CREATE TABLE `stock_pool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(45) DEFAULT NULL COMMENT '股票code',
  `stock_name` varchar(45) DEFAULT NULL COMMENT '股票name',
  `modify_time` datetime DEFAULT NULL,
  `type` char(1) DEFAULT NULL COMMENT '0禁买股，1中小板，2创业板',
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_disable_stock_c_customer1_idx` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8 COMMENT='股票池';

-- ----------------------------
-- Records of stock_pool
-- ----------------------------
INSERT INTO `stock_pool` VALUES ('1', '062000', 'iopqw', '2016-11-18 17:21:56', '1', '2');
INSERT INTO `stock_pool` VALUES ('2', '061000', 'sadsa', '2016-11-15 17:21:56', '2', '2');

-- ----------------------------
-- Table structure for `stock_security`
-- ----------------------------
DROP TABLE IF EXISTS `stock_security`;
CREATE TABLE `stock_security` (
  `internal_security_id` varchar(45) NOT NULL COMMENT '2市唯一',
  `exchange_code` varchar(45) DEFAULT NULL COMMENT '所属交易所',
  `code` varchar(45) DEFAULT NULL COMMENT '股票代码',
  `name` varchar(45) DEFAULT NULL COMMENT '股票名称',
  `modify_time` datetime DEFAULT NULL COMMENT '更新日期',
  `issue_date` datetime DEFAULT NULL COMMENT '上市日期',
  `suspension_flag` varchar(45) DEFAULT NULL COMMENT '是否停牌',
  `first_letter` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`internal_security_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='证券管理';

-- ----------------------------
-- Records of stock_security
-- ----------------------------
INSERT INTO `stock_security` VALUES ('603308', '600010', '000888', '峨眉山', null, null, '0', 'EMS');
INSERT INTO `stock_security` VALUES ('603309', '609010', '000088', '盐田港', null, null, '1', 'YTG');

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '数据值',
  `label` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '标签名',
  `type` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `description` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) COLLATE utf8_bin DEFAULT '0' COMMENT '父级编号',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for `trade_channel`
-- ----------------------------
DROP TABLE IF EXISTS `trade_channel`;
CREATE TABLE `trade_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通道ID',
  `code` varchar(45) DEFAULT NULL COMMENT '通道code',
  `name` varchar(45) DEFAULT NULL COMMENT '通道name',
  `tatio` decimal(10,6) DEFAULT NULL COMMENT '通道收费比例',
  `min_cost` decimal(20,2) DEFAULT NULL COMMENT '最低消费',
  `status` char(1) DEFAULT NULL COMMENT '是否可用',
  `notes` varchar(45) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `creat_time` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '通道类型、恒生pb、个人等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8 COMMENT='通道表';

-- ----------------------------
-- Records of trade_channel
-- ----------------------------
INSERT INTO `trade_channel` VALUES ('1001', '10080', '张三', '0.003000', '100000.00', '1', '通道管理', null, null, 'PB');

-- ----------------------------
-- Table structure for `trade_channel_placement`
-- ----------------------------
DROP TABLE IF EXISTS `trade_channel_placement`;
CREATE TABLE `trade_channel_placement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `security_id` varchar(45) DEFAULT NULL COMMENT '证券ID',
  `side` varchar(45) DEFAULT NULL COMMENT '委托方向',
  `quantity` decimal(20,0) DEFAULT NULL COMMENT '委托数量',
  `price` decimal(20,4) DEFAULT NULL COMMENT '委托单价',
  `amount` decimal(20,4) DEFAULT NULL COMMENT '委托金额',
  `fill_quantity` decimal(20,2) DEFAULT NULL COMMENT '成交数量',
  `fill_amount` decimal(20,4) NOT NULL COMMENT '成交金额',
  `fill_price` decimal(20,4) DEFAULT NULL COMMENT '成交价格',
  `status` varchar(45) DEFAULT NULL COMMENT '委托状态',
  `broker_account_code` varchar(45) DEFAULT NULL COMMENT '券商资金账号',
  `broker_placement_code` varchar(45) DEFAULT NULL COMMENT '券商委托编号',
  `reject_message` varchar(1000) DEFAULT NULL COMMENT '拒绝原因',
  `update_time` datetime DEFAULT NULL COMMENT '创建日期',
  `placement_id` int(11) NOT NULL,
  `trade_channel_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_u_channel_placement_u_placement1_idx` (`placement_id`),
  KEY `fk_u_channel_placement_trade_channel1_idx` (`trade_channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1137 DEFAULT CHARSET=utf8 COMMENT='通道委托';

-- ----------------------------
-- Records of trade_channel_placement
-- ----------------------------
INSERT INTO `trade_channel_placement` VALUES ('1136', '600022', 'BUY', '1000', '12.2300', '12230.0000', '1000.00', '12230.0000', '12.2300', 'CANCELLED', '1002003001', '200161128', null, null, '20001', '2313');

-- ----------------------------
-- Table structure for `trade_channel_placement_history`
-- ----------------------------
DROP TABLE IF EXISTS `trade_channel_placement_history`;
CREATE TABLE `trade_channel_placement_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `security_id` varchar(45) DEFAULT NULL COMMENT '证券ID',
  `side` varchar(45) DEFAULT NULL COMMENT '委托方向',
  `quantity` decimal(20,0) DEFAULT NULL COMMENT '委托数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '委托单价',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '委托金额',
  `fill_quantity` decimal(20,0) DEFAULT NULL COMMENT '成交数量',
  `fill_amount` decimal(20,2) DEFAULT NULL COMMENT '成交金额',
  `fill_price` decimal(20,2) DEFAULT NULL COMMENT '成交价格',
  `status` varchar(45) DEFAULT NULL COMMENT '委托状态',
  `trade_account_code` varchar(45) DEFAULT NULL COMMENT '券商资金账号',
  `trade_placement_code` varchar(45) DEFAULT NULL COMMENT '券商委托编号',
  `reject_message` varchar(1000) DEFAULT NULL COMMENT '拒绝原因',
  `date_time` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `trade_channel_id` int(11) NOT NULL COMMENT '通道id',
  `trade_channel_placement_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_u_placement_relation_trade_channel1_idx` (`trade_channel_id`),
  KEY `fk_u_channel_placement_history_u_channel_placement1_idx` (`trade_channel_placement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1071 DEFAULT CHARSET=utf8 COMMENT='通道历史委托';

-- ----------------------------
-- Records of trade_channel_placement_history
-- ----------------------------
INSERT INTO `trade_channel_placement_history` VALUES ('1069', '600022', 'BUY', '1000', '12.23', '122300.00', '1000', '122300.00', '12.23', 'FILLED', '1002003001', '200161128', null, null, '101', '2313');
INSERT INTO `trade_channel_placement_history` VALUES ('1070', '600023', 'BUY', '1000', '12.23', '122300.00', '1000', '122300.00', '12.23', 'FILLED', '1002003001', '200161128', null, null, '101', '2313');

-- ----------------------------
-- Table structure for `trade_fill`
-- ----------------------------
DROP TABLE IF EXISTS `trade_fill`;
CREATE TABLE `trade_fill` (
  `id` int(45) NOT NULL AUTO_INCREMENT,
  `internal_security_id` varchar(45) DEFAULT NULL COMMENT '股票id',
  `quantity` decimal(20,0) DEFAULT NULL COMMENT '成交数量',
  `side` varchar(45) DEFAULT NULL COMMENT '方向',
  `price` decimal(20,2) DEFAULT NULL COMMENT '成交单价',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '成交金额',
  `fill_time` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  `broker_account_id` varchar(45) DEFAULT NULL COMMENT '券商的资金账号',
  `broker_placment_code` varchar(45) DEFAULT NULL COMMENT '券商委托编号',
  `broker_fill_code` varchar(45) DEFAULT NULL COMMENT '券商成交编号',
  `user_id` int(11) NOT NULL,
  `channel_placement_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_u_fill_u_user1_idx` (`user_id`),
  KEY `fk_u_fill_u_channel_placement1_idx` (`channel_placement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30020 DEFAULT CHARSET=utf8 COMMENT='成交表';

-- ----------------------------
-- Records of trade_fill
-- ----------------------------
INSERT INTO `trade_fill` VALUES ('30001', '600022', '1000', 'BUY', '12.23', '12230.00', null, '1002003001', '200161128', '110200161128', '101', '20001');

-- ----------------------------
-- Table structure for `trade_fill_history`
-- ----------------------------
DROP TABLE IF EXISTS `trade_fill_history`;
CREATE TABLE `trade_fill_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `internal_security_id` varchar(45) DEFAULT NULL COMMENT '股票id',
  `quantity` decimal(20,0) DEFAULT NULL COMMENT '成交数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '成交单价',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '成交金额',
  `side` varchar(45) DEFAULT NULL COMMENT '方向',
  `fill_time` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  `broker_account_id` varchar(45) DEFAULT NULL COMMENT '券商的资金账号',
  `broker_placment_code` varchar(45) DEFAULT NULL COMMENT '券商委托编号',
  `broker_fill_code` varchar(45) DEFAULT NULL COMMENT '券商成交编号',
  `user_id` int(11) NOT NULL,
  `channel_placement_id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_u_fill_history_u_user1_idx` (`user_id`),
  KEY `fk_u_fill_history_u_channel_placement1_idx` (`channel_placement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=366 DEFAULT CHARSET=utf8 COMMENT='历史成交表';

-- ----------------------------
-- Records of trade_fill_history
-- ----------------------------
INSERT INTO `trade_fill_history` VALUES ('365', '600022', '1000', '12.23', '12230.00', 'SELL', null, '1002003001', '200161128', '110200161128', '101', '20001', '40001');

-- ----------------------------
-- Table structure for `trade_flow`
-- ----------------------------
DROP TABLE IF EXISTS `trade_flow`;
CREATE TABLE `trade_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `internal_security_id` varchar(45) DEFAULT NULL COMMENT '股票',
  `adjust_quantity` decimal(20,0) DEFAULT NULL COMMENT '发生数量',
  `adjust_amount` decimal(20,2) DEFAULT NULL COMMENT '发生金额',
  `type` varchar(45) DEFAULT NULL COMMENT '0证券买入，1证券卖出，2资金减少，3资金增加，4新红配',
  `cash` decimal(20,2) DEFAULT NULL COMMENT '本金金额',
  `financing` decimal(20,2) DEFAULT NULL COMMENT '融资金额',
  `cash_amount` decimal(20,2) DEFAULT NULL COMMENT '本金总计',
  `financing_amount` decimal(20,2) DEFAULT NULL COMMENT '融资总计',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `user_id` int(11) NOT NULL,
  `notes` varchar(45) DEFAULT NULL COMMENT '备注：增加(手工)，减少(手工)，交易成交',
  PRIMARY KEY (`id`),
  KEY `fk_u_position_flow_u_user1_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6049 DEFAULT CHARSET=utf8 COMMENT='用户头寸流水';

-- ----------------------------
-- Records of trade_flow
-- ----------------------------
INSERT INTO `trade_flow` VALUES ('6048', '600022', '1000', '12230.00', 'TRADE_TRANSACTION', '1000000.00', '500000.00', '1000000.00', '500000.00', null, '101', null);

-- ----------------------------
-- Table structure for `trade_placement`
-- ----------------------------
DROP TABLE IF EXISTS `trade_placement`;
CREATE TABLE `trade_placement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表ID',
  `internal_security_id` varchar(45) DEFAULT NULL COMMENT '股票2市唯一表示',
  `side` varchar(45) DEFAULT NULL COMMENT '交易方向,买入,卖出',
  `quantity` decimal(20,0) DEFAULT NULL COMMENT '委托数量',
  `price` decimal(20,4) DEFAULT NULL COMMENT '委托价格',
  `amount` decimal(20,4) DEFAULT NULL COMMENT '委托金额',
  `status` varchar(45) DEFAULT NULL COMMENT '委托状态',
  `is_sz_transfer_fee` char(1) DEFAULT NULL COMMENT '是否收取深市过户费',
  `fill_quantity` decimal(20,0) DEFAULT NULL COMMENT '成交数量',
  `fill_amount` decimal(20,4) DEFAULT NULL COMMENT '成交金额',
  `fill_price` decimal(20,4) DEFAULT NULL COMMENT '成交价格',
  `ratio_fee` decimal(20,8) DEFAULT NULL COMMENT '佣金费率',
  `min_cost` decimal(20,2) DEFAULT NULL COMMENT '佣金低消',
  `user_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `time` datetime DEFAULT NULL COMMENT '委托时间',
  PRIMARY KEY (`id`),
  KEY `fk_u_placement_u_user1_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11021 DEFAULT CHARSET=utf8 COMMENT='用户委托表';

-- ----------------------------
-- Records of trade_placement
-- ----------------------------
INSERT INTO `trade_placement` VALUES ('11001', '600022', 'BUY', '1000', '12.2300', '12230.0000', 'SENDACK', '1', '2000', '24530.0000', '12.2650', '0.00080000', '4000.00', '101', '60001', null);

-- ----------------------------
-- Table structure for `trade_placement_history`
-- ----------------------------
DROP TABLE IF EXISTS `trade_placement_history`;
CREATE TABLE `trade_placement_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表ID',
  `internal_security_id` varchar(45) DEFAULT NULL COMMENT '股票2市唯一表示',
  `side` varchar(45) DEFAULT NULL COMMENT '交易方向,买入,卖出',
  `quantity` decimal(20,0) DEFAULT NULL COMMENT '委托数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '委托价格',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '委托金额',
  `status` varchar(45) DEFAULT NULL COMMENT '委托状态',
  `commission_fee` decimal(20,2) DEFAULT NULL COMMENT '佣金',
  `stampduty_fee` decimal(20,2) DEFAULT NULL COMMENT '印花税',
  `transfer_fee` decimal(20,2) DEFAULT NULL COMMENT '过户费',
  `fill_quantity` decimal(20,0) DEFAULT NULL COMMENT '成交数量',
  `fill_amount` decimal(20,2) DEFAULT NULL COMMENT '成交金额',
  `fill_price` decimal(20,2) DEFAULT NULL COMMENT '成交价格',
  `user_id` int(11) NOT NULL,
  `date_time` datetime DEFAULT NULL COMMENT '委托时间',
  PRIMARY KEY (`id`),
  KEY `fk_u_placement_history_u_user1_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1051 DEFAULT CHARSET=utf8 COMMENT='用户历史委托表';

-- ----------------------------
-- Records of trade_placement_history
-- ----------------------------
INSERT INTO `trade_placement_history` VALUES ('1050', '600022', 'BUY', '1000', '12.23', '12230.00', 'CANCELLING', '5.10', '5.10', '5.10', '1000', '12230.00', '12.23', '1011', null);

-- ----------------------------
-- Table structure for `trade_tdx_connect_info`
-- ----------------------------
DROP TABLE IF EXISTS `trade_tdx_connect_info`;
CREATE TABLE `trade_tdx_connect_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_no` varchar(45) DEFAULT NULL COMMENT '登录账号',
  `trade_account` varchar(45) DEFAULT NULL COMMENT '交易账号',
  `gddm_sz` varchar(45) DEFAULT NULL COMMENT '深市股东代码',
  `gddm_sh` varchar(45) DEFAULT NULL COMMENT '沪市股东代码',
  `jy_password` varchar(45) DEFAULT NULL COMMENT '交易代码',
  `tx_password` varchar(45) DEFAULT NULL COMMENT '通信密码',
  `ip` varchar(45) DEFAULT NULL COMMENT '券商ip',
  `port` varchar(45) DEFAULT NULL COMMENT '券商端口',
  `version` varchar(45) DEFAULT NULL COMMENT '版本',
  `yyb_id` varchar(45) DEFAULT NULL COMMENT '营业部代码',
  `exchange_id_sh` varchar(45) DEFAULT '1' COMMENT '上海交易所ID',
  `exchange_id_sz` varchar(45) DEFAULT '0' COMMENT '深圳交易所ID（招商证券普通账户是2）',
  `o_name` varchar(45) DEFAULT NULL COMMENT '名称，例如xx证券-xxx',
  `description` varchar(45) DEFAULT NULL COMMENT '其他描述信息',
  `dll_name` varchar(45) DEFAULT NULL COMMENT 'dll名称',
  `up_dateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20018 DEFAULT CHARSET=utf8 COMMENT='客户端连接信息';

-- ----------------------------
-- Records of trade_tdx_connect_info
-- ----------------------------
INSERT INTO `trade_tdx_connect_info` VALUES ('20002', '00000000', '00000000', '00000000', '00000000', '00000000', '00000000', '00.00.00.00', '0000', '1.1', '0', '0', '0', '太仓启宸', '单侧', 'caicang_qichen', '2016-11-29 18:57:52');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '注册用户ID',
  `user_name` varchar(45) NOT NULL COMMENT '登录用户名',
  `real_name` varchar(45) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(45) NOT NULL COMMENT '登录密码',
  `warn_line` decimal(20,2) DEFAULT NULL COMMENT '警戒线',
  `open_line` decimal(20,2) DEFAULT NULL COMMENT '平仓线',
  `single_stock_scale` decimal(5,2) DEFAULT '1.00' COMMENT '单支股票持仓最大比例',
  `small_stock_scale` decimal(5,2) DEFAULT '1.00' COMMENT '中小板持仓总市值最大比例',
  `create_stock_scale` decimal(5,2) DEFAULT '1.00' COMMENT '创业板总市值最大比例',
  `small_single_stock_scale` decimal(5,2) DEFAULT '1.00' COMMENT '中小板单支股票市值最大比例',
  `create_single_stock_scale` decimal(5,2) DEFAULT '1.00' COMMENT '创业板单支股票市值最大比例',
  `status` int(2) DEFAULT NULL COMMENT '0:正常,1:已锁定,2:已注销',
  `login_count` int(11) DEFAULT '0' COMMENT '登陆次数',
  `related_uuid` varchar(45) DEFAULT NULL COMMENT '关联客户相同uuid',
  `is_default_fee` char(1) NOT NULL COMMENT '是否使用默认费率',
  `channel_id` int(11) NOT NULL COMMENT '通道id',
  `customer_id` int(11) NOT NULL COMMENT '客户id',
  `ratio_fee` decimal(4,2) DEFAULT NULL COMMENT '收费比例',
  `min_cost` decimal(4,2) DEFAULT NULL COMMENT '最低消费',
  `amount` decimal(4,2) DEFAULT NULL COMMENT '帐户总金额',
  `available` decimal(4,2) DEFAULT NULL COMMENT '可用金额',
  `cash_amount` decimal(4,2) DEFAULT NULL COMMENT '本金总额',
  `finance_amount` decimal(4,2) DEFAULT NULL COMMENT '融资总额',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  KEY `fk_u_userinfo_trade_channel1_idx` (`channel_id`),
  KEY `fk_u_userinfo_c_cash_account1_idx` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'royLCO-A', 'Clo-A', 'Adas21#$A', '0.00', '0.00', '1.00', '1.00', '1.00', '1.00', '1.00', '2', '0', null, '0', '2', '0', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', null, null, null);
INSERT INTO `user` VALUES ('2', 'royLCO-D', 'Clo-D', 'Ddas21#$D', '0.00', '0.00', '1.00', '1.00', '1.00', '1.00', '1.00', '0', '0', '04df208b-fb67-4968-baf2-4e5a42a3e73e', '1', '1', '1', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', null, null, null);
INSERT INTO `user` VALUES ('3', 'royLCO-G', 'Clo-G', 'Gdas21#$G', '0.00', '0.00', '1.00', '1.00', '1.00', '1.00', '1.00', '1', '0', null, '1', '2', '2', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', null, null, null);
INSERT INTO `user` VALUES ('4', 'royLCO-J', 'Clo-J', 'Jdas21#$J', '0.00', '0.00', '1.00', '1.00', '1.00', '1.00', '1.00', '2', '0', '04df208b-fb67-4968-baf2-4e5a42a3e73e', '1', '1', '3', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', null, null, null);
INSERT INTO `user` VALUES ('5', 'royLCO-M', 'Clo-M', 'Mdas21#$M', '0.00', '0.00', '1.00', '1.00', '1.00', '1.00', '1.00', '0', '7', null, '0', '2', '1', '0.50', '20.00', '0.00', '0.00', '20.00', '21.00', null, null, null);
INSERT INTO `user` VALUES ('89', 'royLCO-E', 'Clo-E', 'Edas21#$E', null, null, '1.00', '1.00', '1.00', '1.00', '1.00', '2', null, null, '1', '2', '3', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `user_security_position`
-- ----------------------------
DROP TABLE IF EXISTS `user_security_position`;
CREATE TABLE `user_security_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `internal_security_id` varchar(45) DEFAULT NULL COMMENT '股票id',
  `available` decimal(10,2) DEFAULT NULL COMMENT '可用头寸',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '总计头寸',
  `channel_id` varchar(45) DEFAULT NULL COMMENT '通道id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `cost_price` decimal(18,2) DEFAULT NULL COMMENT '成本价',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_u_security_position_u_user1_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='证券头寸';

-- ----------------------------
-- Records of user_security_position
-- ----------------------------
INSERT INTO `user_security_position` VALUES ('1', '603309', '1000.00', '0.00', '2', '2016-11-21 19:14:34', '2.00', '1');
INSERT INTO `user_security_position` VALUES ('2', '603308', '0.00', '2020.00', '1', '2016-11-21 19:14:34', '1.00', '2');
INSERT INTO `user_security_position` VALUES ('3', '603432', '1200.00', '2090.00', '2', '2016-11-21 19:14:34', '1.00', '3');
INSERT INTO `user_security_position` VALUES ('4', '690433', '0.00', '0.00', '2', '2016-11-21 19:14:34', '2.00', '2');
