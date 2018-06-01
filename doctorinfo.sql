/*
Navicat MySQL Data Transfer

Source Server         : server
Source Server Version : 50721
Source Host           : 47.98.154.107:3306
Source Database       : doctorinfo

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-01 18:35:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `db_doctor_department`
-- ----------------------------
DROP TABLE IF EXISTS `db_doctor_department`;
CREATE TABLE `db_doctor_department` (
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `department_id` varchar(64) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`user_id`,`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门-人员 关联表';

-- ----------------------------
-- Records of db_doctor_department
-- ----------------------------
INSERT INTO `db_doctor_department` VALUES ('2c906f0163a4ac6c0163a4ce9bd50007', '1');
INSERT INTO `db_doctor_department` VALUES ('2c906f0163b02c270163ba05319100c0', '1');
INSERT INTO `db_doctor_department` VALUES ('2c906f0163b02c270163ba088f9700c3', '1');
INSERT INTO `db_doctor_department` VALUES ('2c906f0163b02c270163ba088f9700c3', '3ecf48b7656011e88b6b00163e1068c4');
INSERT INTO `db_doctor_department` VALUES ('2c906f0163b02c270163ba088f9700c3', 'acab4df4656311e88b6b00163e1068c4');
INSERT INTO `db_doctor_department` VALUES ('2c906f0163b02c270163ba89def300d3', '3ecf48b7656011e88b6b00163e1068c4');
INSERT INTO `db_doctor_department` VALUES ('2c906f0163b02c270163ba89def300d3', 'acab4df4656311e88b6b00163e1068c4');

-- ----------------------------
-- Table structure for `db_doctor_info`
-- ----------------------------
DROP TABLE IF EXISTS `db_doctor_info`;
CREATE TABLE `db_doctor_info` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标记（0 有效，1删除，2停用）',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `userface` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int(2) DEFAULT NULL COMMENT '性别（0女，1男）',
  `birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `idCard` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `subject` varchar(255) DEFAULT NULL COMMENT '科室（所在部门）',
  `position` varchar(255) DEFAULT NULL COMMENT '岗位',
  `status` tinyint(1) DEFAULT NULL COMMENT '人员状态(0停用，1启用)',
  `option` varchar(64) DEFAULT 'false' COMMENT '是否选中',
  `certificate_type` int(11) DEFAULT NULL COMMENT '证件类型',
  `certificate_number` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `id_certificate_photo` varchar(512) DEFAULT NULL COMMENT '证件照片',
  `registration_certificate_photo` varchar(1000) DEFAULT NULL COMMENT '医师执业注册证',
  `title_certificate_photo` varchar(1000) DEFAULT NULL COMMENT '医生职称证',
  `certificate_status` int(11) DEFAULT NULL COMMENT '认证状态',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户关联uuid',
  `is_register` int(64) DEFAULT NULL,
  `is_temporary` int(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='医生信息表';

-- ----------------------------
-- Records of db_doctor_info
-- ----------------------------
INSERT INTO `db_doctor_info` VALUES ('01f41d01656511e88b6b00163e1068c4', null, null, null, null, null, null, null, '0', null, null, null, null, null, null, null, null, null, 'false', null, null, null, null, null, null, '2c906f0163b02c270163ba088f9700c3', '1', '0');
INSERT INTO `db_doctor_info` VALUES ('1', null, null, null, null, null, null, null, '0', null, '徐颂梁', null, '1', '1987-03-20 00:00:00', '412326198703203934', null, '心内科医生', '1', 'false', null, '412326198703203938', null, null, null, '0', '2c906f0163a4ac6c0163a4ce9bd50007', '1', '0');
INSERT INTO `db_doctor_info` VALUES ('7e74b39c656411e88b6b00163e1068c4', '2c906f0163a4ac6c0163a4ce9bd50007', '2018-06-01 14:24:52.734', null, null, null, null, null, '1', null, '杨党伟', null, '1', '2018-06-01 00:00:00', null, null, '', '1', 'false', '0', '412326198703203934', null, null, null, null, '2c906f0163b02c270163ba05319100c0', null, null);
INSERT INTO `db_doctor_info` VALUES ('bd8f8748657811e88b6b00163e1068c4', '2c906f0163a4ac6c0163a4ce9bd50007', '2018-06-01 16:49:48.552', null, null, null, null, null, '0', null, '路飞', null, '1', '2018-06-13 00:00:00', null, null, '', '1', 'false', '0', '411526199209095178', null, null, null, null, '2c906f0163b02c270163ba89def300d3', null, null);

-- ----------------------------
-- Table structure for `db_doctor_review`
-- ----------------------------
DROP TABLE IF EXISTS `db_doctor_review`;
CREATE TABLE `db_doctor_review` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL COMMENT '申请人id',
  `user_real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `certificate_type` varchar(255) DEFAULT NULL COMMENT '证件类型',
  `certificate_number` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `id_certificate_photo` varchar(512) DEFAULT NULL COMMENT '证件照片',
  `registration_certificate_photo` varchar(1000) DEFAULT NULL COMMENT '医师执业注册证',
  `title_certificate_photo` varchar(1000) DEFAULT NULL COMMENT '医生职称证',
  `department_id` varchar(64) DEFAULT NULL COMMENT '部门id,认证成功时，填入关联表',
  `subject` varchar(255) DEFAULT NULL COMMENT '科室（所在部门）',
  `position` varchar(255) DEFAULT NULL COMMENT '岗位',
  `review_state` int(11) DEFAULT NULL COMMENT '审核状态：0正在审核，1验证成功，2验证失败，3正在填写',
  `review_id` varchar(64) DEFAULT NULL COMMENT '审核人id',
  `review_name` varchar(255) DEFAULT NULL COMMENT '审核人姓名',
  `review_remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生认证审核记录表';

-- ----------------------------
-- Records of db_doctor_review
-- ----------------------------
INSERT INTO `db_doctor_review` VALUES ('1599f45f656611e88b6b00163e1068c4', '2c906f0163b02c270163ba088f9700c3', '杨党伟', '0', '412326198703203934', '/doctor/upload/0dfd13c1cece44ea9307bee17788796f.jpg', '/doctor/registration/fb21b617ef674ea880f23820a11ef6b9.jpg', '/doctor/certificate/0f6fbb6c39704555815620ee1e85504f.jpg', '1', null, null, '1', '2c906f0163a4ac6c0163a4ce9bd50007', '徐颂梁', '给你通过了吧', '2c906f0163b02c270163ba088f9700c3', '2018-06-01 14:36:15.826', '2c906f0163a4ac6c0163a4ce9bd50007', '2018-06-01 14:37:00.933', null, null, null, '0', null);
INSERT INTO `db_doctor_review` VALUES ('2006410d656511e88b6b00163e1068c4', '2c906f0163b02c270163ba088f9700c3', '杨党伟', '0', '412326198703203934', '/doctor/upload/c0123eda1256423d917218f00f9c3627.jpg', '/doctor/registration/6c00d05fc58a4b1cabd5362dd90f0373.jpg', '/doctor/certificate/6962464295974397b3ba26e8546c17e3.jpg', '1', null, null, '2', '2c906f0163a4ac6c0163a4ce9bd50007', '徐颂梁', '不通过', '2c906f0163b02c270163ba088f9700c3', '2018-06-01 14:29:23.814', '2c906f0163a4ac6c0163a4ce9bd50007', '2018-06-01 14:33:23.840', null, null, null, '0', null);

-- ----------------------------
-- Table structure for `db_doctor_role`
-- ----------------------------
DROP TABLE IF EXISTS `db_doctor_role`;
CREATE TABLE `db_doctor_role` (
  `user_id` varchar(64) NOT NULL,
  `role_id` varchar(64) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生角色表';

-- ----------------------------
-- Records of db_doctor_role
-- ----------------------------
INSERT INTO `db_doctor_role` VALUES ('2c906f0163a4ac6c0163a4ce9bd50007', '1');
INSERT INTO `db_doctor_role` VALUES ('2c906f0163a4ac6c0163a4ce9bd50007', '8c925c9d656211e88b6b00163e1068c4');
INSERT INTO `db_doctor_role` VALUES ('2c906f0163b02c270163ba088f9700c3', '8c925c9d656211e88b6b00163e1068c4');
INSERT INTO `db_doctor_role` VALUES ('2c906f0163b02c270163ba89def300d3', '1');

-- ----------------------------
-- Table structure for `db_order`
-- ----------------------------
DROP TABLE IF EXISTS `db_order`;
CREATE TABLE `db_order` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `main_user_id` varchar(64) DEFAULT NULL COMMENT '关联用户（关联圈）',
  `order_amount` decimal(8,2) DEFAULT NULL COMMENT '总金额',
  `order_status` tinyint(4) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Records of db_order
-- ----------------------------

-- ----------------------------
-- Table structure for `db_product`
-- ----------------------------
DROP TABLE IF EXISTS `db_product`;
CREATE TABLE `db_product` (
  `id` varchar(64) NOT NULL,
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `category_type` int(11) DEFAULT NULL COMMENT '类目编号',
  `product_name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) DEFAULT NULL COMMENT '商品价格',
  `product_description` varchar(64) DEFAULT NULL COMMENT '商品描述',
  `product_specification` varchar(64) DEFAULT NULL COMMENT '商品规格',
  `product_usage_count` int(11) DEFAULT NULL COMMENT '使用次数',
  `product_range` varchar(64) DEFAULT NULL COMMENT '使用范围',
  `product_valid` int(11) DEFAULT NULL COMMENT '有效期(天数)',
  `product_service` varchar(64) DEFAULT NULL COMMENT '服务',
  `product_keyword` varchar(128) DEFAULT NULL COMMENT '关键字',
  `product_growth_value` int(11) DEFAULT NULL COMMENT '赠送等级成长值',
  `product_bonus` varchar(128) DEFAULT NULL COMMENT '赠送服务',
  `product_purchase_limitation` int(11) DEFAULT NULL COMMENT '商品限购',
  `product_vip_price` decimal(8,2) DEFAULT NULL COMMENT 'vip价格',
  `product_detail_pc` varchar(512) DEFAULT NULL COMMENT '商品详情电脑端',
  `product_detail_mobile` varchar(512) DEFAULT NULL COMMENT '商品详情移动端',
  `product_stock` int(11) DEFAULT NULL COMMENT '商品库存',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
  `product_status` tinyint(4) DEFAULT NULL COMMENT '商品状态（0默认下架）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_product
-- ----------------------------

-- ----------------------------
-- Table structure for `db_product_category`
-- ----------------------------
DROP TABLE IF EXISTS `db_product_category`;
CREATE TABLE `db_product_category` (
  `id` varchar(64) NOT NULL,
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `category_type` int(11) DEFAULT NULL COMMENT '类目编号',
  `category_name` varchar(64) DEFAULT NULL COMMENT '类目名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品类目表';

-- ----------------------------
-- Records of db_product_category
-- ----------------------------

-- ----------------------------
-- Table structure for `db_report_unscramble`
-- ----------------------------
DROP TABLE IF EXISTS `db_report_unscramble`;
CREATE TABLE `db_report_unscramble` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `report_name` varchar(64) DEFAULT NULL COMMENT '报告名称',
  `report_type` tinyint(1) DEFAULT NULL COMMENT '报告类型',
  `patient_account` varchar(32) DEFAULT NULL COMMENT '患者账号',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `report_time` datetime DEFAULT NULL COMMENT '报告时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `timeliness` int(11) DEFAULT NULL COMMENT '解读及时性',
  `reader` varchar(255) DEFAULT NULL COMMENT '解读人',
  `user_id` varchar(255) DEFAULT NULL COMMENT '医生关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_report_unscramble
-- ----------------------------
INSERT INTO `db_report_unscramble` VALUES ('099d879c657711e88b6b00163e1068c4', '1514736009999', '1', '15144172716', '1', '0', '13', '2018-04-17 20:16:46', '2018-06-01 16:37:37', '1', '徐颂梁', '1');
INSERT INTO `db_report_unscramble` VALUES ('7f12af34657411e88b6b00163e1068c4', '1514736009999', '0', '15144172716', '1', '0', '23', '2018-04-11 14:12:10', '2018-06-01 16:19:26', '1', '徐颂梁', '1');
INSERT INTO `db_report_unscramble` VALUES ('8650b5eb656911e88b6b00163e1068c4', '1514736009999', '1', '15144172716', '1', '1', '12', '2018-04-17 20:16:46', '2018-06-01 15:00:53', '1', '徐颂梁', '1');
INSERT INTO `db_report_unscramble` VALUES ('a425f260656711e88b6b00163e1068c4', '1514736009999', '1', '15144172716', '1', '1', '10', '2018-04-17 20:16:46', '2018-06-01 14:47:24', '1', '杨党伟', '30776a6b656611e88b6b00163e1068c4');
INSERT INTO `db_report_unscramble` VALUES ('d090bdff656711e88b6b00163e1068c4', '1514736009999', '1', '15144172716', '1', '1', '11', '2018-04-17 20:16:46', '2018-06-01 14:48:39', '1', '杨党伟', '30776a6b656611e88b6b00163e1068c4');

-- ----------------------------
-- Table structure for `db_system_affiche`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_affiche`;
CREATE TABLE `db_system_affiche` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `affiche_name` varchar(64) DEFAULT NULL COMMENT '公告名称',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `send_number` varchar(32) DEFAULT NULL COMMENT '发送人数',
  `send_man` varchar(32) DEFAULT NULL COMMENT '发布人',
  `send_time` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_system_affiche
-- ----------------------------

-- ----------------------------
-- Table structure for `db_system_department`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_department`;
CREATE TABLE `db_system_department` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `department_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父id',
  `principle` varchar(255) DEFAULT NULL COMMENT '负责人',
  `department_phone` varchar(255) DEFAULT NULL COMMENT '部门电话',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态(0启用，1停用)',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `operation` varchar(255) DEFAULT NULL COMMENT '操作',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_system_department
-- ----------------------------
INSERT INTO `db_system_department` VALUES ('1', '浙江智柔科技有限公司', '0', '李四sss', '0571-98635415', '0', '1号楼412室', '英文名Hum', '编辑删除');
INSERT INTO `db_system_department` VALUES ('3ecf48b7656011e88b6b00163e1068c4', '儿童部', '1', '小明', '110', '0', '霞飞路228号', '儿童节快乐', null);
INSERT INTO `db_system_department` VALUES ('a85a1ad5657e11e88b6b00163e1068c4', '后清部', '1', '路飞', '13520381041', '0', '霞飞路', '啊啊', null);
INSERT INTO `db_system_department` VALUES ('acab4df4656311e88b6b00163e1068c4', '测试060101', '3ecf48b7656011e88b6b00163e1068c4', '张三丰', '0571-5632653', '1', '杭州江干下沙', '32253526', null);

-- ----------------------------
-- Table structure for `db_system_logger`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_logger`;
CREATE TABLE `db_system_logger` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_content` varchar(255) DEFAULT NULL COMMENT '操作内容',
  `operate_type` varchar(255) DEFAULT NULL COMMENT '操作类型',
  `operator` varchar(255) DEFAULT NULL COMMENT '操作人',
  `operate_account` varchar(255) DEFAULT NULL COMMENT '操作人账号',
  `operation` varchar(255) DEFAULT NULL COMMENT '操作',
  `operation_before` text COMMENT '操作前',
  `operation_after` text COMMENT '操作后',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_system_logger
-- ----------------------------
INSERT INTO `db_system_logger` VALUES ('007c7a49657911e88b6b00163e1068c4', '2018-06-01 16:51:41', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"管理员\",\"privilegeArray\":[\"PERM_temperature\",\"PERM_sleep\",\"PERM_electrocardio\",\"PERM_organization\",\"PERM_permission\",\"PERM_personage\",\"PERM_setting\",\"PERM_logger\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('2f193e59656211e88b6b00163e1068c4', '2018-06-01 14:08:21', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', 'null', '{\"departmentName\":\"儿童部\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"1\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('2f196ce7656211e88b6b00163e1068c4', '2018-06-01 14:08:21', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('3ed23d61656011e88b6b00163e1068c4', '2018-06-01 13:54:28', '系统管理-机构部门', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('4372fdbb656f11e88b6b00163e1068c4', '2018-06-01 15:41:58', '系统管理-人员管理', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"4370f0e2656f11e88b6b00163e1068c4\",\"createUser\":\"2c906f0163a4ac6c0163a4ce9bd50007\",\"createTime\":\"Jun 1, 2018 3:41:58 PM\",\"deleteFlag\":0,\"realName\":\"杨党委第三\",\"mobile\":\"15268558257\",\"gender\":1,\"birthday\":\"Jun 19, 2018 12:00:00 AM\",\"position\":\"\",\"status\":true,\"certificateType\":0,\"certificateNumber\":\"411526199209095178\",\"userId\":\"2c906f0163b02c270163ba088f9700c3\",\"isTemporary\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('4b461203656211e88b6b00163e1068c4', '2018-06-01 14:09:08', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', 'null', '{\"departmentName\":\"儿童部\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('4b466117656211e88b6b00163e1068c4', '2018-06-01 14:09:08', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('53439b60656211e88b6b00163e1068c4', '2018-06-01 14:09:21', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('5343a312656211e88b6b00163e1068c4', '2018-06-01 14:09:21', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', 'null', '{\"departmentName\":\"儿童部\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('5a2e403a656711e88b6b00163e1068c4', '2018-06-01 14:45:20', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_permission\"]}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('5cb61250656211e88b6b00163e1068c4', '2018-06-01 14:09:37', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', 'null', '{\"departmentName\":\"儿童部\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('5cb62b1b656211e88b6b00163e1068c4', '2018-06-01 14:09:37', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('71288776656211e88b6b00163e1068c4', '2018-06-01 14:10:11', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', 'null', '{\"departmentName\":\"儿童部\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('7128a0f9656211e88b6b00163e1068c4', '2018-06-01 14:10:11', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('7c7c65bb656211e88b6b00163e1068c4', '2018-06-01 14:10:30', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', 'null', '{\"departmentName\":\"儿童部\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('7c7ca9eb656211e88b6b00163e1068c4', '2018-06-01 14:10:30', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('7e78e046656411e88b6b00163e1068c4', '2018-06-01 14:24:53', '系统管理-人员管理', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"7e74b39c656411e88b6b00163e1068c4\",\"createUser\":\"2c906f0163a4ac6c0163a4ce9bd50007\",\"createTime\":\"Jun 1, 2018 2:24:52 PM\",\"deleteFlag\":0,\"realName\":\"杨党伟\",\"mobile\":\"15268558257\",\"gender\":1,\"birthday\":\"Jun 1, 2018 12:00:00 AM\",\"position\":\"\",\"status\":true,\"certificateType\":0,\"certificateNumber\":\"412326198703203934\",\"userId\":\"2c906f0163b02c270163ba05319100c0\",\"isTemporary\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('8c9ba3f2656211e88b6b00163e1068c4', '2018-06-01 14:10:57', '系统管理-角色权限', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"role\":\"测试060101\",\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_personage\"],\"status\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('95de042c656311e88b6b00163e1068c4', '2018-06-01 14:18:23', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"d3717cff656211e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('95deb74c656311e88b6b00163e1068c4', '2018-06-01 14:18:23', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"d3717cff656211e88b6b00163e1068c4\",\"departmentName\":\"儿童部一\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"陈健\",\"departmentPhone\":\"13520381041\",\"status\":\"1\",\"address\":\"潢川县爱国村\",\"remarks\":\"你好\"}', '{\"id\":\"d3717cff656211e88b6b00163e1068c4\",\"departmentName\":\"儿童部一\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"陈健\",\"departmentPhone\":\"13520381041\",\"status\":\"0\",\"address\":\"潢川县爱国村\",\"remarks\":\"你好\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('96945fe0656211e88b6b00163e1068c4', '2018-06-01 14:11:14', '系统管理-角色权限', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"role\":\"测试060101\",\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_personage\"],\"status\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('99147427656411e88b6b00163e1068c4', '2018-06-01 14:25:37', '系统管理-机构部门删除', '查看', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"d3717cff656211e88b6b00163e1068c4\",\"departmentName\":\"儿童部一\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"陈健\",\"departmentPhone\":\"13520381041\",\"status\":\"0\",\"address\":\"潢川县爱国村\",\"remarks\":\"你好\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('9f00513c656a11e88b6b00163e1068c4', '2018-06-01 15:08:44', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_permission\",\"PERM_logger\"]}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('9fbd0df4656211e88b6b00163e1068c4', '2018-06-01 14:11:30', '系统管理-角色权限', '删除', '徐颂梁', '13335713700', '查看详情', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('a14374f6657a11e88b6b00163e1068c4', '2018-06-01 17:03:20', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"管理员\",\"privilegeArray\":[\"PERM_temperature\",\"PERM_sleep\",\"PERM_electrocardio\",\"PERM_organization\",\"PERM_permission\",\"PERM_personage\",\"PERM_setting\",\"PERM_logger\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('a571be47656211e88b6b00163e1068c4', '2018-06-01 14:11:39', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('a5725398656211e88b6b00163e1068c4', '2018-06-01 14:11:39', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"1\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('a6d93db2656211e88b6b00163e1068c4', '2018-06-01 14:11:42', '系统管理-角色权限', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"role\":\"测试060101\",\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_personage\",\"PERM_logger\"],\"status\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('a7a7075f657a11e88b6b00163e1068c4', '2018-06-01 17:03:31', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"测试060101\",\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_sleep\",\"PERM_permission\",\"PERM_logger\",\"PERM_organization\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('a85b5223657e11e88b6b00163e1068c4', '2018-06-01 17:32:10', '系统管理-机构部门', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"a85a1ad5657e11e88b6b00163e1068c4\",\"departmentName\":\"后清部\",\"parentId\":\"1\",\"principle\":\"路飞\",\"departmentPhone\":\"13520381041\",\"status\":\"0\",\"address\":\"霞飞路\",\"remarks\":\"啊啊\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('acacae6c656311e88b6b00163e1068c4', '2018-06-01 14:19:01', '系统管理-机构部门', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"acab4df4656311e88b6b00163e1068c4\",\"departmentName\":\"测试060101\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"张三丰\",\"departmentPhone\":\"0571-5632653\",\"status\":\"0\",\"address\":\"杭州江干下沙\",\"remarks\":\"32253526\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('ace475da657a11e88b6b00163e1068c4', '2018-06-01 17:03:40', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"测试060101\",\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_sleep\",\"PERM_temperature\",\"PERM_permission\",\"PERM_logger\",\"PERM_organization\",\"PERM_personage\",\"PERM_setting\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('b2333de4656311e88b6b00163e1068c4', '2018-06-01 14:19:10', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"1\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('b234c3f5656311e88b6b00163e1068c4', '2018-06-01 14:19:10', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4,acab4df4656311e88b6b00163e1068c4,d3717cff656211e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('b592d221657a11e88b6b00163e1068c4', '2018-06-01 17:03:54', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"测试060101\",\"privilegeArray\":[\"PERM_sleep\",\"PERM_temperature\",\"PERM_electrocardio\",\"PERM_permission\",\"PERM_organization\",\"PERM_personage\",\"PERM_setting\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('b61046c9656211e88b6b00163e1068c4', '2018-06-01 14:12:07', '系统管理-角色权限', '删除', '徐颂梁', '13335713700', '查看详情', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('b8410363656311e88b6b00163e1068c4', '2018-06-01 14:19:20', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"d3717cff656211e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('b8415527656311e88b6b00163e1068c4', '2018-06-01 14:19:20', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"d3717cff656211e88b6b00163e1068c4\",\"departmentName\":\"儿童部一\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"陈健\",\"departmentPhone\":\"13520381041\",\"status\":\"1\",\"address\":\"潢川县爱国村\",\"remarks\":\"你好\"}', '{\"id\":\"d3717cff656211e88b6b00163e1068c4\",\"departmentName\":\"儿童部一\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"陈健\",\"departmentPhone\":\"13520381041\",\"status\":\"0\",\"address\":\"潢川县爱国村\",\"remarks\":\"你好\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('bd93a9ba657811e88b6b00163e1068c4', '2018-06-01 16:49:49', '系统管理-人员管理', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"bd8f8748657811e88b6b00163e1068c4\",\"createUser\":\"2c906f0163a4ac6c0163a4ce9bd50007\",\"createTime\":\"Jun 1, 2018 4:49:48 PM\",\"deleteFlag\":0,\"realName\":\"路飞\",\"mobile\":\"17633658454\",\"gender\":1,\"birthday\":\"Jun 13, 2018 12:00:00 AM\",\"position\":\"\",\"status\":true,\"certificateType\":0,\"certificateNumber\":\"411526199209095178\",\"userId\":\"2c906f0163b02c270163ba89def300d3\",\"isTemporary\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('ca1a3e85657a11e88b6b00163e1068c4', '2018-06-01 17:04:29', '系统管理-角色权限', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"role\":\"眼科\",\"privilegeArray\":[\"PERM_temperature\",\"PERM_sleep\",\"PERM_electrocardio\",\"PERM_personage\",\"PERM_permission\",\"PERM_organization\",\"PERM_logger\",\"PERM_setting\"],\"status\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('cb02f45d656e11e88b6b00163e1068c4', '2018-06-01 15:38:36', '系统管理-人员管理', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"cb004cf2656e11e88b6b00163e1068c4\",\"createUser\":\"2c906f0163a4ac6c0163a4ce9bd50007\",\"createTime\":\"Jun 1, 2018 3:38:36 PM\",\"deleteFlag\":0,\"realName\":\"杨党委第二\",\"mobile\":\"15268558257\",\"gender\":1,\"birthday\":\"Jun 19, 2018 12:00:00 AM\",\"position\":\"\",\"status\":true,\"certificateType\":0,\"certificateNumber\":\"411526199209095178\",\"userId\":\"2c906f0163b02c270163ba088f9700c3\",\"isTemporary\":0}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('cd7e9b0f656411e88b6b00163e1068c4', '2018-06-01 14:27:05', '系统管理-人员管理', '删除', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"7e74b39c656411e88b6b00163e1068c4\",\"createUser\":\"2c906f0163a4ac6c0163a4ce9bd50007\",\"createTime\":\"Jun 1, 2018 2:24:52 PM\",\"deleteFlag\":0,\"realName\":\"杨党伟\",\"mobile\":\"15268558257\",\"gender\":1,\"birthday\":\"Jun 1, 2018 12:00:00 AM\",\"position\":\"\",\"status\":true,\"certificateType\":0,\"certificateNumber\":\"412326198703203934\",\"userId\":\"2c906f0163b02c270163ba05319100c0\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('cfafcdc5657a11e88b6b00163e1068c4', '2018-06-01 17:04:38', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"眼科\",\"privilegeArray\":[\"PERM_temperature\",\"PERM_sleep\",\"PERM_electrocardio\",\"PERM_personage\",\"PERM_permission\",\"PERM_organization\",\"PERM_setting\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('d008b516657d11e88b6b00163e1068c4', '2018-06-01 17:26:07', '系统管理-机构部门', '更新', '徐颂梁', '13335713700', '查看详情', '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"1\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', '{\"id\":\"3ecf48b7656011e88b6b00163e1068c4\",\"departmentName\":\"儿童部\",\"parentId\":\"1\",\"principle\":\"小明\",\"departmentPhone\":\"110\",\"status\":\"0\",\"address\":\"霞飞路228号\",\"remarks\":\"儿童节快乐\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('d00956b2657d11e88b6b00163e1068c4', '2018-06-01 17:26:07', '系统管理-禁用部门及子部门', '禁用部门及子部门', '徐颂梁', '13335713700', '查看详情', '\"3ecf48b7656011e88b6b00163e1068c4,acab4df4656311e88b6b00163e1068c4\"', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('d372bab5656211e88b6b00163e1068c4', '2018-06-01 14:12:56', '系统管理-机构部门', '添加', '徐颂梁', '13335713700', '查看详情', null, '{\"id\":\"d3717cff656211e88b6b00163e1068c4\",\"departmentName\":\"儿童部一\",\"parentId\":\"3ecf48b7656011e88b6b00163e1068c4\",\"principle\":\"陈健\",\"departmentPhone\":\"13520381041\",\"status\":\"1\",\"address\":\"潢川县爱国村\",\"remarks\":\"你好\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('d84d4d8b657811e88b6b00163e1068c4', '2018-06-01 16:50:33', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"role\":\"管理员\",\"privilegeArray\":[\"PERM_temperature\",\"PERM_sleep\",\"PERM_electrocardio\",\"PERM_organization\",\"PERM_permission\",\"PERM_personage\",\"PERM_setting\",\"PERM_logger\"],\"status\":\"0\"}', null, null, null, null, null, null, null, null, null);
INSERT INTO `db_system_logger` VALUES ('f226d12e656611e88b6b00163e1068c4', '2018-06-01 14:42:26', '系统管理-角色权限', '更新', '徐颂梁', '13335713700', '查看详情', '{}', '{\"privilegeArray\":[\"PERM_electrocardio\",\"PERM_logger\"]}', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `db_system_message`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_message`;
CREATE TABLE `db_system_message` (
  `id` int(64) NOT NULL COMMENT '主键id',
  `from_user_id` varchar(64) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '消息标题',
  `origin` varchar(255) DEFAULT NULL COMMENT '消息来源',
  `content` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `type` tinyint(1) DEFAULT NULL COMMENT '消息类型（0通知，1公告）',
  `people_number` int(10) DEFAULT NULL COMMENT '人数',
  `orgnazation_id` varchar(64) DEFAULT NULL COMMENT '组织机构id',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标记（0未删，1已删）',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_send` tinyint(1) DEFAULT NULL COMMENT '是否发布（0，未发布， 1 已发布）',
  `send_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_system_message
-- ----------------------------
INSERT INTO `db_system_message` VALUES ('1', null, '升级啦', '新浪', '在排查问题时，一般是先通过JVM性能调优实践——性能指标分析中的几个命令来分析基础的服务器状态和信息。在微服务架构中，每台服务器部署着若干运行着服务的容器。在不能通过应用日志或者问题现象定位问题服务时，需要找到问题容器。', '0', '20', 'GUO_REN', '张三丰', '2018-05-23 14:17:51', null, '2018-05-23 14:17:51', null, null, null, '0', '通知已读', '1', null);
INSERT INTO `db_system_message` VALUES ('2', null, '降级啦', '腾讯', '在排查问题时，一般是先通过JVM性能调优实践——性能指标分析中的几个命令来分析基础的服务器状态和信息。在微服务架构中，每台服务器部署着若干运行着服务的容器。在不能通过应用日志或者问题现象定位问题服务时，需要找到问题容器。', '0', '20', 'GUO_REN', null, '2018-05-29 14:17:45', null, '2018-05-29 14:17:45', null, null, null, '0', '通知已读', '1', null);
INSERT INTO `db_system_message` VALUES ('3', null, '平级啦', '阿里', '在排查问题时，一般是先通过JVM性能调优实践——性能指标分析中的几个命令来分析基础的服务器状态和信息。在微服务架构中，每台服务器部署着若干运行着服务的容器。在不能通过应用日志或者问题现象定位问题服务时，需要找到问题容器。', '1', '20', 'GUO_REN', null, '2018-05-15 14:17:57', null, '2018-05-15 14:17:57', null, null, null, '0', '公告已读', '1', null);
INSERT INTO `db_system_message` VALUES ('4', null, '垃圾啦', '网易', '在排查问题时，一般是先通过JVM性能调优实践——性能指标分析中的几个命令来分析基础的服务器状态和信息。在微服务架构中，每台服务器部署着若干运行着服务的容器。在不能通过应用日志或者问题现象定位问题服务时，需要找到问题容器。', '1', '20', 'GUO_REN', null, '2018-05-17 14:18:01', null, '2018-05-17 14:18:01', null, null, null, '0', '公告已读', '1', null);
INSERT INTO `db_system_message` VALUES ('5', 'aaa', '测试发送', 'a', 'aaaaaaa', '1', null, 'GUO_REN', '张三丰', '2018-06-01 16:11:39', '张三丰', '2018-06-01 16:11:42', null, null, null, '0', '公告已读', null, '2018-06-01 16:14:36');

-- ----------------------------
-- Table structure for `db_system_message_setting`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_message_setting`;
CREATE TABLE `db_system_message_setting` (
  `user_id` varchar(64) NOT NULL,
  `notification` tinyint(1) DEFAULT NULL,
  `announcement` tinyint(1) DEFAULT NULL,
  `receive_method` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_system_message_setting
-- ----------------------------
INSERT INTO `db_system_message_setting` VALUES ('2c906f0163a4ac6c0163a4ce9bd50007', '1', '1', '2');

-- ----------------------------
-- Table structure for `db_system_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_privilege`;
CREATE TABLE `db_system_privilege` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `privilege` varchar(255) DEFAULT NULL COMMENT '权限',
  `privilege_name` varchar(255) DEFAULT NULL COMMENT '权限名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_system_privilege
-- ----------------------------
INSERT INTO `db_system_privilege` VALUES ('1', 'PERM_report', '报告');
INSERT INTO `db_system_privilege` VALUES ('10', 'PERM_setting', '系统设置');
INSERT INTO `db_system_privilege` VALUES ('2', 'PERM_temperature', '体温报告');
INSERT INTO `db_system_privilege` VALUES ('3', 'PERM_sleep', '睡眠报告');
INSERT INTO `db_system_privilege` VALUES ('4', 'PERM_electrocardio', '心电报告');
INSERT INTO `db_system_privilege` VALUES ('5', 'PERM_system', '系统');
INSERT INTO `db_system_privilege` VALUES ('6', 'PERM_organization', '组织结构');
INSERT INTO `db_system_privilege` VALUES ('7', 'PERM_permission', '权限管理');
INSERT INTO `db_system_privilege` VALUES ('8', 'PERM_personage', '人员管理');
INSERT INTO `db_system_privilege` VALUES ('9', 'PERM_logger', '操作日志');

-- ----------------------------
-- Table structure for `db_system_role`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_role`;
CREATE TABLE `db_system_role` (
  `role_id` varchar(64) NOT NULL COMMENT '主键id',
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（0禁用，1启用）',
  `privilege_management` varchar(255) DEFAULT NULL COMMENT '设置权限',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(3) DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标记（0使用，1删除，2彻底删除）',
  `remarks` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_system_role
-- ----------------------------
INSERT INTO `db_system_role` VALUES ('1', '管理员', '0', 'PERM_temperature,PERM_sleep,PERM_electrocardio,PERM_organization,PERM_permission,PERM_personage,PERM_setting,PERM_logger', '2c906f0162b5a64c0162b88c58830003', '2018-04-23 08:56:45.000', 'KaraJoker', '2018-05-23 08:56:55.000', '0', '管理员角色');
INSERT INTO `db_system_role` VALUES ('2', '全科医生a', '0', 'PERM_temperature,PERM_sleep,PERM_electrocardio,PERM_organization,PERM_permission,PERM_personage,PERM_setting', '12c906f0162b5a64c0162b88c58830003', '2018-05-23 16:43:22.000', 'KaraJoker', '2018-05-23 16:43:28.000', '0', '医生角色');
INSERT INTO `db_system_role` VALUES ('8c925c9d656211e88b6b00163e1068c4', '测试060101', '0', 'PERM_sleep,PERM_temperature,PERM_electrocardio,PERM_permission,PERM_organization,PERM_personage,PERM_setting', '2c906f0163a4ac6c0163a4ce9bd50007', '2018-06-01 14:10:57.438', null, null, null, null);
INSERT INTO `db_system_role` VALUES ('ca190847657a11e88b6b00163e1068c4', '眼科', '0', 'PERM_temperature,PERM_sleep,PERM_electrocardio,PERM_personage,PERM_permission,PERM_organization,PERM_setting', '2c906f0163a4ac6c0163a4ce9bd50007', '2018-06-01 17:04:28.584', null, null, null, null);

-- ----------------------------
-- Table structure for `db_system_setting`
-- ----------------------------
DROP TABLE IF EXISTS `db_system_setting`;
CREATE TABLE `db_system_setting` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `logo` varchar(255) DEFAULT NULL COMMENT '系统logo',
  `sysName` varchar(255) DEFAULT NULL COMMENT '系统名称',
  `copyright` varchar(255) DEFAULT NULL COMMENT '版本信息',
  `publish_picture` varchar(255) DEFAULT NULL COMMENT '宣传图片',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_system_setting
-- ----------------------------

-- ----------------------------
-- Table structure for `db_user_help`
-- ----------------------------
DROP TABLE IF EXISTS `db_user_help`;
CREATE TABLE `db_user_help` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `suggest` varchar(1000) DEFAULT NULL COMMENT '建议',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of db_user_help
-- ----------------------------

-- ----------------------------
-- Table structure for `db_user_message`
-- ----------------------------
DROP TABLE IF EXISTS `db_user_message`;
CREATE TABLE `db_user_message` (
  `from_user_id` varchar(64) DEFAULT NULL,
  `from_real_name` varchar(255) DEFAULT '',
  `to_user_id` varchar(64) NOT NULL,
  `msg_id` int(18) NOT NULL,
  `msg_type` int(3) NOT NULL,
  `orgnazation_id` varchar(64) DEFAULT '',
  `is_reading` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`to_user_id`,`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_user_message
-- ----------------------------
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163a4ac6c0163a4ce9bd50007', '1', '0', '', '0');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163a4ac6c0163a4ce9bd50007', '2', '0', '', '0');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163a4ac6c0163a4ce9bd50007', '3', '1', '', '0');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163a4ac6c0163a4ce9bd50007', '4', '1', '', '1');
INSERT INTO `db_user_message` VALUES (null, '', '2c906f0163a4ac6c0163a4ce9bd50007', '5', '1', 'GUO_REN', '0');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163b02c270163b42adeea0030', '1', '0', '', '1');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163b02c270163b42adeea0030', '2', '0', '', '0');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163b02c270163b42adeea0030', '3', '1', '', '0');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163b02c270163b42adeea0030', '4', '1', '', '1');
INSERT INTO `db_user_message` VALUES ('a', 'a', '2c906f0163b02c270163ba088f9700c3', '1', '0', '', '1');

-- ----------------------------
-- Table structure for `db_user_remain`
-- ----------------------------
DROP TABLE IF EXISTS `db_user_remain`;
CREATE TABLE `db_user_remain` (
  `id` varchar(64) NOT NULL,
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `main_user_id` varchar(64) DEFAULT NULL COMMENT '用户id（关联圈）',
  `remain_count` int(11) DEFAULT NULL COMMENT '剩余次数',
  `product_valid` int(11) DEFAULT NULL COMMENT '有效期天数',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户剩余问诊次数';

-- ----------------------------
-- Records of db_user_remain
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_vip_management`
-- ----------------------------
DROP TABLE IF EXISTS `sys_vip_management`;
CREATE TABLE `sys_vip_management` (
  `id` varchar(64) NOT NULL,
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend_1` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_2` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `extend_3` varchar(200) DEFAULT NULL COMMENT '预留扩展字段',
  `delete_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `vip_level` int(11) DEFAULT NULL COMMENT 'vip等级',
  `vip_empirical_value` int(11) DEFAULT NULL COMMENT 'vip每级所需经验值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='vip管理';

-- ----------------------------
-- Records of sys_vip_management
-- ----------------------------

-- ----------------------------
-- Procedure structure for `NewProc`
-- ----------------------------
DROP PROCEDURE IF EXISTS `NewProc`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `NewProc`(IN `finish_time` datetime,IN `report_time` datetime,OUT `timeLong` int)
BEGIN
		set timeLong = finish_time-report_time;

END
;;
DELIMITER ;
