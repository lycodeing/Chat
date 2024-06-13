/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 13/06/2024 22:20:48
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group`
(
    `id`          char(36) NOT NULL COMMENT '群组主键',
    `group_name`  varchar(128) DEFAULT NULL COMMENT '群组名称',
    `num`         int          DEFAULT NULL COMMENT '人数上限',
    `avatar_url`  varchar(256) DEFAULT NULL COMMENT '群组头像',
    `description` varchar(512) DEFAULT NULL COMMENT '群组简介',
    `status`      tinyint(1) DEFAULT NULL COMMENT '状态 1正常 2封群 3删群',
    `join_method` tinyint(1) DEFAULT NULL COMMENT '进群方式  1任意 2需审核',
    `is_muted`    tinyint(1) DEFAULT NULL COMMENT '禁言状态  0没禁言 1禁言',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of chat_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for chat_group_user
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_user`;
CREATE TABLE `chat_group_user`
(
    `id`          char(36) NOT NULL COMMENT '主键',
    `group_id`    char(36)                                                      DEFAULT NULL COMMENT '群组id',
    `user_id`     char(36)                                                      DEFAULT NULL COMMENT '用户id',
    `source_name` varchar(128)                                                  DEFAULT NULL COMMENT '原始昵称',
    `nick_name`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '群昵称',
    `role`        tinyint(1) DEFAULT NULL COMMENT '角色 0群主 1管理员 2普通成员',
    `join_time`   timestamp NULL DEFAULT NULL COMMENT '加入群组时间',
    `status`      tinyint  NOT NULL                                             DEFAULT '1' COMMENT '状态：1：正常；2：退群；3：被踢出群',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of chat_group_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for chat_user
-- ----------------------------
DROP TABLE IF EXISTS `chat_user`;
CREATE TABLE `chat_user`
(
    `id`         char(36) NOT NULL COMMENT '主键',
    `user_name`  varchar(64)  DEFAULT NULL COMMENT '用户名',
    `nick_name`  varchar(128) DEFAULT NULL COMMENT '昵称',
    `password`   varchar(128) DEFAULT NULL COMMENT '密码',
    `avatar_url` varchar(256) DEFAULT NULL COMMENT '头像',
    `gender`     tinyint(1) DEFAULT NULL COMMENT '性别 1男 0女',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of chat_user
-- ----------------------------
BEGIN;
INSERT INTO `chat_user` (`id`, `user_name`, `nick_name`, `password`, `avatar_url`, `gender`)
VALUES ('1', 'test', 'test', 'e10adc3949ba59abbe56e057f20f883e', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for chat_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `chat_user_relation`;
CREATE TABLE `chat_user_relation`
(
    `id`          char(36) NOT NULL COMMENT '主键',
    `user_id`     char(36)     DEFAULT NULL COMMENT '用户id',
    `to_user_id`  char(36)     DEFAULT NULL COMMENT '好友id',
    `nick_name`   varchar(128) DEFAULT NULL COMMENT '备注昵称',
    `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of chat_user_relation
-- ----------------------------
BEGIN;
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
