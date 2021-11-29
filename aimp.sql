/*
Navicat MySQL Data Transfer

Source Server         : aimp
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : aimp

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2021-11-29 18:02:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for aimp_device
-- ----------------------------
DROP TABLE IF EXISTS `aimp_device`;
CREATE TABLE `aimp_device` (
  `device_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `port` int DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `device_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `first_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `second_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `current_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of aimp_device
-- ----------------------------
INSERT INTO `aimp_device` VALUES ('dh2626294621280', '192.168.1.108', '37777', 'admin', 'jyz123456', null, null, null, null, null, '2021-11-29 16:09:39', '在线');
INSERT INTO `aimp_device` VALUES ('dh2940565365856', '192.168.1.108', '37777', 'admin', 'jyz123456', null, null, null, null, null, '2021-11-29 16:01:44', '在线');
INSERT INTO `aimp_device` VALUES ('hk1', '192.168.1.61', '8000', 'admin', 'abc123..', null, null, null, null, null, '2021-11-29 14:18:32', '在线');

-- ----------------------------
-- Table structure for aimp_device_config
-- ----------------------------
DROP TABLE IF EXISTS `aimp_device_config`;
CREATE TABLE `aimp_device_config` (
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `wait_time` int DEFAULT NULL,
  `try_times` int DEFAULT NULL,
  `reconnect_time` int DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of aimp_device_config
-- ----------------------------
INSERT INTO `aimp_device_config` VALUES ('默认', '3000', '1', '10000', '默认');

-- ----------------------------
-- Table structure for aimp_forward_config
-- ----------------------------
DROP TABLE IF EXISTS `aimp_forward_config`;
CREATE TABLE `aimp_forward_config` (
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `height` int DEFAULT NULL,
  `width` int DEFAULT NULL,
  `video_codec` int DEFAULT NULL,
  `video_format` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of aimp_forward_config
-- ----------------------------
INSERT INTO `aimp_forward_config` VALUES ('默认', '0', '0', '0', 'mp4', '默认');

-- ----------------------------
-- Table structure for aimp_ptzcontrol_config
-- ----------------------------
DROP TABLE IF EXISTS `aimp_ptzcontrol_config`;
CREATE TABLE `aimp_ptzcontrol_config` (
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ptzstorage_max_size` int DEFAULT NULL,
  `sleep_time` int DEFAULT NULL,
  `small_object` double DEFAULT NULL,
  `cooling_time` int DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of aimp_ptzcontrol_config
-- ----------------------------
INSERT INTO `aimp_ptzcontrol_config` VALUES ('默认', '20', '120', '0.3', '6', '默认');

-- ----------------------------
-- Table structure for aimp_realplay_config
-- ----------------------------
DROP TABLE IF EXISTS `aimp_realplay_config`;
CREATE TABLE `aimp_realplay_config` (
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `channel_num` int DEFAULT NULL,
  `dw_stream_type` int DEFAULT NULL,
  `dw_link_mode` int DEFAULT NULL,
  `b_blocked` int DEFAULT NULL,
  `is_img_Stream` int DEFAULT NULL,
  `pending_list_max_size` int DEFAULT NULL,
  `processed_list_max_size` int DEFAULT NULL,
  `is_save_file` int DEFAULT NULL,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_forward` int DEFAULT NULL,
  `stream_media_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_ptz_control` int DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of aimp_realplay_config
-- ----------------------------
INSERT INTO `aimp_realplay_config` VALUES ('大华默认', '0', '0', '0', '1', null, '30', '30', '0', null, '0', 'rtsp://172.22.104.255/', '0', '大华默认');
INSERT INTO `aimp_realplay_config` VALUES ('海康默认', '1', '0', '0', '1', null, '30', '30', '1', 'dahua.mp4', '0', 'rtsp://172.22.104.255/', '0', '海康默认');
