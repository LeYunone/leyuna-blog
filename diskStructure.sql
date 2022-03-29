/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : disk

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 29/03/2022 15:11:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_dt` datetime NULL DEFAULT NULL,
  `update_dt` datetime NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT NULL,
  `file_size` double NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `file_type` tinyint NULL DEFAULT NULL COMMENT '文件类型：1图片、2音视、3文档、4其他文件',
  `file_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件类型中文',
  `save_dt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '保存时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_up_log
-- ----------------------------
DROP TABLE IF EXISTS `file_up_log`;
CREATE TABLE `file_up_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户',
  `update_dt` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_dt` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `up_sign` tinyint NULL DEFAULT NULL COMMENT '最后一次上传的合法标志',
  `up_file_total_size` double NULL DEFAULT NULL COMMENT '当前操作后的文件总内存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
