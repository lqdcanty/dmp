/*
Navicat MySQL Data Transfer

Source Server         : Evance-Aliyun
Source Server Version : 50716
Source Host           : 101.132.177.166:3306
Source Database       : 5jtzs-dev

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-10-12 16:40:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `app_code` varchar(20) DEFAULT '' COMMENT '应用编号',
  `app_name` varchar(64) DEFAULT '' COMMENT '系统名称',
  `app_version` varchar(20) DEFAULT '' COMMENT '系统版本',
  `app_intro` varchar(255) DEFAULT NULL COMMENT '系统简介',
  `manager_name` varchar(20) DEFAULT '' COMMENT '系统负责人账号',
  `manager_realname` varchar(24) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '系统负责人名称',
  `manager_phone` varchar(16) DEFAULT '' COMMENT '管理员电话',
  `manager_email` varchar(24) DEFAULT '' COMMENT '管理员邮箱',
  `duty_user_name` varchar(20) DEFAULT NULL COMMENT '值班人员账号',
  `code_type` varchar(8) DEFAULT 'svn' COMMENT 'svn: git:',
  `code_url` varchar(255) DEFAULT '' COMMENT '代码地址',
  `create_user` varchar(24) DEFAULT '' COMMENT '创建者账号',
  `last_modify_user` varchar(24) DEFAULT '' COMMENT '最后一次修改人',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最近一次修改时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_code` (`app_code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of app_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_base`;
CREATE TABLE `sys_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_name` varchar(200) DEFAULT NULL COMMENT '系统全称',
  `sys_sort_name` varchar(6) DEFAULT NULL COMMENT '系统简称',
  `version` varchar(8) DEFAULT NULL COMMENT '系统版本',
  `sys_log` varchar(255) DEFAULT NULL COMMENT '系统logo',
  `intro` text COMMENT '简介(此处数据为富文本)',
  `sys_manager_name` varchar(32) DEFAULT NULL COMMENT '系统管理员',
  `sys_manager_phone` varchar(20) DEFAULT NULL COMMENT '系统管理员电话',
  `sys_manager_email` varchar(32) DEFAULT NULL COMMENT '系统管理员邮箱',
  `online_express` varchar(255) DEFAULT '2' COMMENT '用户在线时间',
  `single_login` tinyint(1) DEFAULT '1' COMMENT '点登录默认单点登录',
  `pop3_title` varchar(128) DEFAULT NULL COMMENT '系统邮箱标题',
  `smtp_server` varchar(255) DEFAULT NULL COMMENT '发送系统邮件的SMTP服务器',
  `pop3` varchar(20) DEFAULT NULL COMMENT '系统邮箱POP3帐号',
  `pop3m` varchar(20) DEFAULT NULL COMMENT '系统邮箱POP3密码',
  `last_up_uid` varchar(20) DEFAULT NULL,
  `last_uname` varchar(32) DEFAULT NULL COMMENT '最后更新人名称',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_base
-- ----------------------------

-- ----------------------------
-- Table structure for sys_document
-- ----------------------------
DROP TABLE IF EXISTS `sys_document`;
CREATE TABLE `sys_document` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(2) NOT NULL COMMENT '(1.内部文档2.外部文档)',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `version` varchar(32) DEFAULT NULL COMMENT '版本',
  `intro` text COMMENT '简介',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `attachment` text COMMENT '附件JSON数据[{"sdk包下载":"http"},{"文档":""}]',
  `is_enable` tinyint(1) DEFAULT NULL COMMENT '是否可使用(1可使用 0不可使用)',
  `last_uid` int(11) DEFAULT NULL COMMENT '最后维护人uid',
  `last_uname` varchar(32) DEFAULT NULL COMMENT '最后维护人名称',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_document
-- ----------------------------

-- ----------------------------
-- Table structure for sys_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_info`;
CREATE TABLE `sys_info` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `app_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属app',
  `name` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '系统名称',
  `create_user_name` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统创建者',
  `icon` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统图标',
  `dispaly` tinyint(1) DEFAULT '1' COMMENT '1:展示 0:不展示',
  `host` varchar(32) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'varchar系统域名',
  `gmt_create` datetime DEFAULT NULL COMMENT 'datetime',
  `gmt_modifed` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `sys_name_index` (`name`) USING BTREE COMMENT '系统名称索引'
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_info
-- ----------------------------
INSERT INTO `sys_info` VALUES ('1', 'sys-admin', '系统管理', '超级管理员', 'fa-desktop', '1', null, '2018-02-26 18:22:13', '2018-05-28 23:58:54');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_id` bigint(20) DEFAULT NULL COMMENT '系统父级目录id',
  `menu_name` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统目录名称',
  `menu_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统目录url',
  `type` varchar(12) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型menu,url;button',
  `permission_code` varchar(24) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限码',
  `is_self_sys` tinyint(1) DEFAULT NULL COMMENT '是否内部系统：1.是，0.否',
  `new_window` tinyint(1) DEFAULT NULL COMMENT '是否是新窗口打开1:新窗口打开0当前框架打开',
  `is_open` tinyint(1) DEFAULT NULL COMMENT '是否展开1:展开 0:不展开',
  `status` tinyint(1) DEFAULT NULL COMMENT '使用状态 1启用0禁用',
  `parent_id` bigint(20) DEFAULT NULL,
  `support_mobile` tinyint(1) DEFAULT '0' COMMENT '是否支持手机端 1支持0不支持',
  `gmt_create` datetime DEFAULT NULL COMMENT '数据创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('36', '1', '系统配置', '', '', '75', '1', '0', '0', '1', null, '0', null);
INSERT INTO `sys_menu` VALUES ('37', '1', '用户管理', '/system/user/manager', 'url', '13', '1', '0', '0', '1', '36', '0', null);
INSERT INTO `sys_menu` VALUES ('38', '1', '权限管理', '/system/sys/roleList', 'url', '15', '1', '0', '0', '1', '36', '0', null);
INSERT INTO `sys_menu` VALUES ('42', '1', '菜单配置', '/system/sys/menuList', 'url', '89', '1', '0', '0', '1', '36', '0', null);
INSERT INTO `sys_menu` VALUES ('43', '1', '系统配置', '/system/sys/sysList', 'url', '90', '1', '0', '0', '1', '36', '0', null);
INSERT INTO `sys_menu` VALUES ('45', '1', '可视文档', '/doc.html', 'url', '29', '1', '0', '0', '1', '311', '0', null);
INSERT INTO `sys_menu` VALUES ('76', '1', '系统日志', '', 'catalog', 'MjAxODA4MTUxMTAwNTQ=', '1', '0', '0', '1', null, '0', null);
INSERT INTO `sys_menu` VALUES ('77', '1', '日志信息', '/system/log/loglist', 'url', 'MjAxODA4MTQxNDU1MDM=', '1', '0', '0', '1', '76', '0', null);
INSERT INTO `sys_menu` VALUES ('311', '1', '系统文档', '', 'catalog', 'admin-doc', '1', '0', '0', '1', null, '0', '2018-10-12 16:35:29');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `uuid` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者id',
  `create_user_name` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者名称',
  `status` varchar(12) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态 正常：normal锁定：lock',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE,
  KEY `id` (`id`,`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admindefult', 'admin', 'normal', '2018-10-12 16:26:23', '2018-10-12 16:26:26');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `role_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '系统目录id',
  `menu_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统目录名称',
  `uuid` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_user_name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者名称',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_permiss_index` (`role_id`,`role_name`,`menu_id`,`menu_name`) USING BTREE COMMENT '角色权限索引'
) ENGINE=InnoDB AUTO_INCREMENT=446 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('36', '1', '超级管理员', '36', '', 'admindefult', 'admin', '2018-03-19 17:29:07', '2018-03-19 17:29:07');
INSERT INTO `sys_role_permission` VALUES ('37', '1', '超级管理员', '37', '', 'admindefult', 'admin', '2018-03-19 17:29:07', '2018-03-19 17:29:07');
INSERT INTO `sys_role_permission` VALUES ('38', '1', '超级管理员', '38', '', 'admindefult', 'admin', '2018-03-19 17:29:07', '2018-03-19 17:29:07');
INSERT INTO `sys_role_permission` VALUES ('43', '1', '超级管理员', '43', '', 'admindefult', 'admin', '2018-10-12 16:32:31', '2018-10-12 16:32:31');
INSERT INTO `sys_role_permission` VALUES ('45', '1', '超级管理员', '45', '', 'admindefult', 'admin', '2018-10-12 16:32:31', '2018-10-12 16:32:31');
INSERT INTO `sys_role_permission` VALUES ('89', '1', '超级管理员', '42', '', 'admindefult', 'admin', '2018-05-22 23:39:31', '2018-10-12 16:32:31');
INSERT INTO `sys_role_permission` VALUES ('443', '1', '超级管理员', '76', null, 'admindefult', 'admin', '2018-10-12 16:37:24', null);
INSERT INTO `sys_role_permission` VALUES ('444', '1', '超级管理员', '77', null, 'admindefult', 'admin', '2018-10-12 16:37:26', null);
INSERT INTO `sys_role_permission` VALUES ('445', '1', '超级管理员', '311', null, 'admindefult', 'admin', '2018-10-12 16:37:27', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '用户唯一ID(内部)',
  `user_name` varchar(24) COLLATE utf8mb4_bin NOT NULL COMMENT '登陆名(登陆)',
  `password` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `real_name` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '实名',
  `nick_name` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `phone` varchar(24) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `gender` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '性别(男：man；女:woman；未知:unknow)',
  `status` varchar(12) COLLATE utf8mb4_bin DEFAULT '1' COMMENT '用户是否锁定(1:可使用；0:禁用)',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `remark` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `identity` varchar(24) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份(由第三方系统子指定)',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid_index` (`uuid`) USING BTREE,
  UNIQUE KEY `user_name_index` (`user_name`) USING BTREE,
  KEY `user_index` (`uuid`,`user_name`,`real_name`,`email`,`phone`) USING BTREE COMMENT '用户信息索引'
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admindefult', 'admin', 'A66ABB5684C45962D887564F08346E8D', 'admin', '超级管理员', 'wang765aaa@163.com', 'http://i-7.vcimg.com/trim/afbc4ce7d42d28e805f0d43c7c599120554463/trim.jpg', '18482190199', '男', '1', '2018-10-12 16:37:35', '系统默认超级管理员', '超级管理员', '2018-02-26 00:10:29', '2018-08-10 10:52:13');

-- ----------------------------
-- Table structure for sys_user_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_log`;
CREATE TABLE `sys_user_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户唯一ID',
  `user_name` varchar(24) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名称',
  `action_type` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '行为类型(login:登陆；loginout:退出登陆；sysadd:新增系统给；sysmodify:系统修改；menuadd:新增菜单；menumodify:菜单修改；other:其他)',
  `action` text COLLATE utf8mb4_bin COMMENT '行为记录',
  `ip` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作IP',
  `browser` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '浏览器信息',
  `view_url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '访问URL',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_log_index` (`uuid`,`action_type`) USING BTREE COMMENT '用户日志索引'
) ENGINE=InnoDB AUTO_INCREMENT=18836 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_user_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户唯一ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `role_name` varchar(24) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `role_id_fk` (`role_id`),
  KEY `role_name_fk` (`role_name`),
  CONSTRAINT `role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_name_fk` FOREIGN KEY (`role_name`) REFERENCES `sys_role` (`role_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=751 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', 'admindefult', '1', '超级管理员', '2018-03-01 18:46:49', '2018-10-12 16:30:52');
