-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ias_v2_0
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alarm_config`
--

DROP TABLE IF EXISTS `alarm_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarm_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dev_id` int(11) NOT NULL COMMENT '设备id',
  `alarm_type_id` int(11) NOT NULL COMMENT '传感器数据采集值的type',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `forewarn_uplimit` int(10) DEFAULT NULL COMMENT '预警上限值（暂时不用）',
  `forewarn_lowlimit` int(10) DEFAULT NULL COMMENT '预警下限值（暂时不用）',
  `alarmwarn_uplimit` int(10) DEFAULT NULL COMMENT '告警上限值',
  `alarmwarn_lowlimit` int(10) DEFAULT NULL COMMENT '告警下限值',
  PRIMARY KEY (`dev_id`,`alarm_type_id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `camera`
--

DROP TABLE IF EXISTS `camera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `camera` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `sn` varchar(128) NOT NULL,
  `code` varchar(128) NOT NULL,
  `url_hls` varchar(256) DEFAULT NULL,
  `url_hls_hd` varchar(256) DEFAULT NULL,
  `rtmp` varchar(256) DEFAULT NULL,
  `rtmp_hd` varchar(256) DEFAULT NULL,
  `ws_addr` varchar(256) DEFAULT NULL,
  `ias_id` int(11) NOT NULL,
  `farmland_id` int(11) DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `capability` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_UNIQUE` (`sn`),
  KEY `ias_id_idx` (`ias_id`),
  CONSTRAINT `ias_id` FOREIGN KEY (`ias_id`) REFERENCES `iasystem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `common_crop`
--

DROP TABLE IF EXISTS `common_crop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common_crop` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) unsigned NOT NULL COMMENT '分类ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `img` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sort` int(5) DEFAULT '9999',
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `cat_id` (`cat_id`) USING BTREE,
  CONSTRAINT `common_crop_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `common_crop_cat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='作物信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `common_crop_cat`
--

DROP TABLE IF EXISTS `common_crop_cat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common_crop_cat` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sort` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='作物分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `common_region`
--

DROP TABLE IF EXISTS `common_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common_region` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `pid` smallint(5) unsigned NOT NULL,
  `code` mediumint(8) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `controller_status_log`
--

DROP TABLE IF EXISTS `controller_status_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `controller_status_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `battery_vol` int(11) DEFAULT NULL,
  `battery_with_solar_vol` int(11) DEFAULT NULL,
  `solar_panel_vol` int(11) DEFAULT NULL,
  `signal_strength` int(11) DEFAULT NULL,
  `board_temp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_collections`
--

DROP TABLE IF EXISTS `data_collections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_collections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` bigint(20) DEFAULT NULL COMMENT '传感器id',
  `type` int(11) DEFAULT NULL COMMENT '传感器值类型',
  `value` int(11) DEFAULT NULL COMMENT '传感器值',
  `time` timestamp NULL DEFAULT NULL COMMENT '采集时间',
  `collection_type` int(11) DEFAULT NULL COMMENT '采集类型: 1整点自动采集 2数据变化采集',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `dc_point_id` (`sensor_id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2997 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` bigint(20) NOT NULL COMMENT '设备ID。设备ID由4字节主控器序列号和4字节设备地址（仅使用后3字节）组成。',
  `ias_id` int(11) NOT NULL COMMENT '设备属于哪一个智慧农业系统',
  `name` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '设备名称',
  `keep_alive` int(11) DEFAULT '70' COMMENT '仅对主控制器有效，服务器判断主控制器离线的阈值时间',
  `low_consume` tinyint(1) DEFAULT '0' COMMENT '仅对主控制器有效，主控制器是否采用休眠模式',
  `config` varbinary(20) DEFAULT NULL COMMENT '设备的配置参数，目前没有使用。后续用于服务器对设备配置的维护相关工作。',
  PRIMARY KEY (`id`),
  KEY `dev_owner_idx` (`ias_id`),
  CONSTRAINT `dev_owner` FOREIGN KEY (`ias_id`) REFERENCES `iasystem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_operate`
--

DROP TABLE IF EXISTS `device_operate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_operate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ias_dev_id` int(11) NOT NULL COMMENT '设备ID',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `op_type` int(11) NOT NULL COMMENT '操作类型',
  `auto_stop` tinyint(1) DEFAULT NULL COMMENT '断网是否自动停止设备',
  `duration` int(11) DEFAULT NULL COMMENT '运行时长',
  `param` int(11) DEFAULT NULL COMMENT '启动参数',
  `result` int(11) NOT NULL COMMENT '操作结果',
  `source` int(11) NOT NULL COMMENT '操作来源',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_status`
--

DROP TABLE IF EXISTS `device_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dev_id` bigint(20) NOT NULL COMMENT '设备ID',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `old_state` int(11) NOT NULL COMMENT '老的状态',
  `new_state` int(11) NOT NULL COMMENT '新的状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `farmland`
--

DROP TABLE IF EXISTS `farmland`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `farmland` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '农田ID',
  `iasystem_id` int(11) NOT NULL COMMENT '农田属于哪个智慧农业系统\n',
  `name` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '农田的名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '农田创建时间',
  `is_delete` smallint(1) NOT NULL DEFAULT '0' COMMENT '农田是否已经删除',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '农田删除时间',
  `sort_order` int(11) NOT NULL DEFAULT '1' COMMENT '农田在界面的显示顺序',
  PRIMARY KEY (`id`),
  KEY `farmland_owner_idx` (`iasystem_id`),
  CONSTRAINT `farmland_owner` FOREIGN KEY (`iasystem_id`) REFERENCES `iasystem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ias_device`
--

DROP TABLE IF EXISTS `ias_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ias_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录自身的ID',
  `dev_id` bigint(20) NOT NULL COMMENT '设备ID',
  `ias_id` int(11) NOT NULL COMMENT '智慧农业系统ID',
  `farmland_id` int(11) DEFAULT NULL COMMENT '农田ID',
  `irri_area_id` int(11) DEFAULT NULL COMMENT '灌溉区域ID',
  `ias_dev_id` bigint(11) DEFAULT NULL COMMENT '智慧农业系统设备ID',
  `irri_fer_id` int(11) DEFAULT NULL COMMENT '水肥一体化系统ID',
  `name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '设备在智慧农业系统中的名称',
  `usage_type` int(11) NOT NULL COMMENT '设备的用途。目前，设备的用途有:',
  `user_id` int(11) DEFAULT NULL COMMENT '使用该设备的对象的ID。\n这里需要注意当使用该设备的对象也为一个设备时，使用者为这里另一条设备记录的ID',
  `sort_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dev_id_UNIQUE` (`dev_id`),
  KEY `ias_device_idx` (`dev_id`),
  KEY `ias_idx` (`ias_id`),
  KEY `fm_device_idx` (`farmland_id`),
  KEY `area_device_idx` (`irri_area_id`),
  KEY `device_device_idx` (`ias_dev_id`),
  KEY `irri_fer_device_idx` (`irri_fer_id`),
  CONSTRAINT `area_device` FOREIGN KEY (`irri_area_id`) REFERENCES `irrigation_area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `devide_devide` FOREIGN KEY (`ias_dev_id`) REFERENCES `ias_device` (`dev_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fm_device` FOREIGN KEY (`farmland_id`) REFERENCES `farmland` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ias` FOREIGN KEY (`ias_id`) REFERENCES `iasystem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ias_device` FOREIGN KEY (`dev_id`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `irri_fer_device` FOREIGN KEY (`irri_fer_id`) REFERENCES `irri_fer_system` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='智慧农业系统设备使用信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iasystem`
--

DROP TABLE IF EXISTS `iasystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iasystem` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '智慧农业系统的ID',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '智慧农业系统类型。1-普通智慧农业系统、2-气象站',
  `name` varchar(45) NOT NULL COMMENT '智慧农业系统的名称',
  `alias` varchar(40) DEFAULT NULL COMMENT '别名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '智慧农业系统的创建时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智慧农业系统是否已经删除',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '智慧农业系统的删除时间',
  `max_irr_num` int(11) NOT NULL DEFAULT '1' COMMENT '可以同时运行的灌溉任务的数量',
  `mode` int(11) NOT NULL DEFAULT '2' COMMENT '系统的工作模式。1为手动模式，2为自动模式',
  `sort_order` int(11) NOT NULL DEFAULT '1' COMMENT '智慧农业系统在界面的显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_article`
--

DROP TABLE IF EXISTS `info_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(56) CHARACTER SET utf8 NOT NULL COMMENT '标题',
  `img` varchar(256) CHARACTER SET utf8 NOT NULL COMMENT '缩略图',
  `originImg` varchar(256) CHARACTER SET utf8 NOT NULL COMMENT '原图',
  `watchCount` smallint(8) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
  `commentCount` smallint(8) unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
  `publishTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `tag` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '文章标签,分隔',
  `htmlContent` text CHARACTER SET utf8 NOT NULL COMMENT '文章内容',
  `origin` varchar(24) CHARACTER SET utf8 NOT NULL COMMENT '内容来源: eg 第一农经',
  `regionId` smallint(5) unsigned DEFAULT NULL COMMENT '区域id ,如果这个文章是区分区域',
  `cropId` int(11) unsigned DEFAULT NULL COMMENT '作物id,如果这个文章是针对作物的话',
  `catPid` varchar(20) NOT NULL COMMENT '分类的path id , 用来索引 分类下的的文章',
  `catId` smallint(6) unsigned NOT NULL COMMENT '分类id , 主要用来关联删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `classify` (`catId`) USING BTREE,
  KEY `articleRegion` (`regionId`) USING BTREE,
  KEY `articleCrop` (`cropId`) USING BTREE,
  FULLTEXT KEY `classifyPid` (`catPid`),
  CONSTRAINT `articleClassify` FOREIGN KEY (`catId`) REFERENCES `info_cat` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `articleCrop` FOREIGN KEY (`cropId`) REFERENCES `common_crop` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `articleRegion` FOREIGN KEY (`regionId`) REFERENCES `common_region` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_article_report`
--

DROP TABLE IF EXISTS `info_article_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_article_report` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned DEFAULT NULL,
  `articleId` int(10) unsigned DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `reportUser` (`userId`) USING BTREE,
  KEY `reportArticle` (`articleId`) USING BTREE,
  CONSTRAINT `reportArticle` FOREIGN KEY (`articleId`) REFERENCES `info_article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reportUser` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_cat`
--

DROP TABLE IF EXISTS `info_cat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_cat` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `pid` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '父类ID  用 , 分隔  eg : 25,56',
  `name` varchar(24) CHARACTER SET utf8 NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE,
  FULLTEXT KEY `pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_comment`
--

DROP TABLE IF EXISTS `info_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `articleId` int(11) unsigned DEFAULT NULL COMMENT '如果是评论的文章,则是文章id',
  `userId` int(11) unsigned NOT NULL COMMENT '评论的用户',
  `subjectId` smallint(6) unsigned DEFAULT NULL COMMENT '如果是评论的专题 , 则是专题id',
  `comment` varchar(255) NOT NULL COMMENT '评论内容',
  `publishTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `likeCount` smallint(6) NOT NULL DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `commentArticle` (`articleId`) USING BTREE,
  KEY `commentSubject` (`subjectId`) USING BTREE,
  KEY `commentUser` (`userId`) USING BTREE,
  CONSTRAINT `commentArticle` FOREIGN KEY (`articleId`) REFERENCES `info_article` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `commentSubject` FOREIGN KEY (`subjectId`) REFERENCES `info_subject` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `commentUser` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_comment_like`
--

DROP TABLE IF EXISTS `info_comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_comment_like` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `commentId` int(11) unsigned NOT NULL COMMENT '评论的id',
  `userId` int(11) unsigned NOT NULL COMMENT '点赞的用户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `likeComment` (`commentId`) USING BTREE,
  KEY `likeUser` (`userId`) USING BTREE,
  CONSTRAINT `likeComment` FOREIGN KEY (`commentId`) REFERENCES `info_comment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `likeUser` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_recommend`
--

DROP TABLE IF EXISTS `info_recommend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_recommend` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) unsigned NOT NULL,
  `articleId` int(11) unsigned DEFAULT NULL,
  `subjectId` smallint(6) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `recommendUser` (`userId`) USING BTREE,
  KEY `recommendArticle` (`articleId`) USING BTREE,
  KEY `recommendSubject` (`subjectId`) USING BTREE,
  CONSTRAINT `recommendArticle` FOREIGN KEY (`articleId`) REFERENCES `info_article` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `recommendSubject` FOREIGN KEY (`subjectId`) REFERENCES `info_subject` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `recommendUser` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_subject`
--

DROP TABLE IF EXISTS `info_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_subject` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(56) NOT NULL,
  `img` varchar(256) NOT NULL,
  `originImg` varchar(256) NOT NULL,
  `watchCount` smallint(7) unsigned NOT NULL DEFAULT '0',
  `commentCount` smallint(7) unsigned NOT NULL,
  `publishTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `summary` varchar(255) NOT NULL,
  `origin` varchar(24) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `articlePublishTime` (`publishTime`) USING BTREE,
  KEY `articleId` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_subject_cat`
--

DROP TABLE IF EXISTS `info_subject_cat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_subject_cat` (
  `id` smallint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(56) NOT NULL,
  `subjectId` smallint(6) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `subjectId` (`subjectId`,`id`) USING BTREE,
  CONSTRAINT `subjectClassify` FOREIGN KEY (`subjectId`) REFERENCES `info_subject` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info_subject_cat_article`
--

DROP TABLE IF EXISTS `info_subject_cat_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_subject_cat_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `subjectId` smallint(6) unsigned NOT NULL,
  `subjectCatId` smallint(8) unsigned NOT NULL,
  `articleId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `articleSubjectClassify` (`articleId`) USING BTREE,
  KEY `subjectAndClassify` (`subjectId`,`subjectCatId`) USING BTREE,
  CONSTRAINT `articleSubjectClassify` FOREIGN KEY (`articleId`) REFERENCES `info_article` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `subjectAndClassify` FOREIGN KEY (`subjectId`, `subjectCatId`) REFERENCES `info_subject_cat` (`subjectId`, `id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `irri_fer_system`
--

DROP TABLE IF EXISTS `irri_fer_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irri_fer_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ias_id` int(11) NOT NULL COMMENT '所属的智慧农业系统id',
  `name` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '水肥一体化系统' COMMENT '水肥一体化名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `delete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `irri_fer_owner_idx` (`ias_id`),
  CONSTRAINT `irri_fer_owner` FOREIGN KEY (`ias_id`) REFERENCES `iasystem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='智慧农业系统水肥一体化子系统';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `irrigation_area`
--

DROP TABLE IF EXISTS `irrigation_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irrigation_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '灌溉区ID',
  `ias_id` int(11) NOT NULL COMMENT '灌溉区属于哪一个智慧农业系统',
  `fm_id` int(11) NOT NULL COMMENT '灌溉区属于哪一个农田',
  `name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '灌溉区名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '灌溉区域创建时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '灌溉区域是否删除',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '灌溉区域删除时间',
  `sort_order` int(11) NOT NULL DEFAULT '1' COMMENT '灌溉区域在界面的显示顺序',
  PRIMARY KEY (`id`),
  KEY `area_ias_idx` (`ias_id`),
  KEY `area_fm_idx` (`fm_id`),
  CONSTRAINT `area_fm` FOREIGN KEY (`fm_id`) REFERENCES `farmland` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `area_ias` FOREIGN KEY (`ias_id`) REFERENCES `iasystem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `irrigation_task`
--

DROP TABLE IF EXISTS `irrigation_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irrigation_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ias_id` int(11) DEFAULT NULL COMMENT '智慧农业系统id',
  `farmland_id` int(11) DEFAULT NULL COMMENT '农田id',
  `irri_area_id` int(11) DEFAULT NULL COMMENT '灌溉区id',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '任务创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `finish_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `create_user` int(11) DEFAULT NULL COMMENT '任务创建者',
  `delete_user` int(11) DEFAULT NULL COMMENT '任务删除者',
  `status` int(11) DEFAULT NULL COMMENT '这里的状态时代码中的状态，和给前端的不一样',
  `result` int(11) DEFAULT NULL COMMENT '结果',
  `exp_duration` int(11) DEFAULT NULL COMMENT '期望浇水时长',
  `irri_duration` int(11) DEFAULT NULL COMMENT '实际浇水时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `latest_irri_log`
--

DROP TABLE IF EXISTS `latest_irri_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `latest_irri_log` (
  `irr_area_id` int(11) NOT NULL,
  `iasystem_id` int(11) NOT NULL,
  `fm_id` int(11) NOT NULL,
  `duration` int(11) NOT NULL,
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `start_time` datetime NOT NULL,
  `finish_time` datetime NOT NULL,
  `result` int(11) NOT NULL,
  `create_user` int(11) DEFAULT NULL,
  `stop_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息的ID',
  `ias_id` int(11) DEFAULT NULL,
  `category` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '消息的类型',
  `title` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '消息的标题',
  `summary` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '消息的摘要',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息创建的时间',
  `extension` json NOT NULL COMMENT '消息的扩展信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message_push_log`
--

DROP TABLE IF EXISTS `message_push_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_push_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msg_id` bigint(20) DEFAULT NULL COMMENT '极光推送返回的id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '文本内容',
  `extra` text COMMENT '额外字段(json格式的消息体)',
  `userIds` text COMMENT '推送的用户字符串',
  `status` tinyint(4) DEFAULT NULL COMMENT '推送状态（0：失败  1：成功）',
  `time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '推送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_roles_permissions` (`role_name`,`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config` (
  `name` varchar(60) NOT NULL,
  `value` json NOT NULL,
  `description` varchar(1024) DEFAULT NULL COMMENT '配置项描述',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统全局配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_farmland`
--

DROP TABLE IF EXISTS `user_farmland`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_farmland` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `ias_id` int(11) DEFAULT NULL COMMENT '农业系统id',
  `fm_id` int(11) DEFAULT NULL COMMENT '农田id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_ias`
--

DROP TABLE IF EXISTS `user_ias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `ias_id` int(11) DEFAULT NULL COMMENT '系统id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_message`
--

DROP TABLE IF EXISTS `user_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_message` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `msg_id` bigint(20) NOT NULL COMMENT '消息ID',
  `verified` tinyint(1) DEFAULT '0' COMMENT '消息是否已读',
  `read_time` timestamp NULL DEFAULT NULL COMMENT '消息的已读时间',
  PRIMARY KEY (`user_id`,`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_notification` (
  `user_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `verify` tinyint(1) NOT NULL DEFAULT '0',
  `title` varchar(128) COLLATE utf8_bin NOT NULL,
  `summary` varchar(128) COLLATE utf8_bin NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiration_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`user_id`,`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) NOT NULL COMMENT '手机号码',
  `username` varchar(40) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `password_salt` varchar(40) DEFAULT NULL,
  `password_backup` varchar(45) NOT NULL COMMENT '保存用户密码，防止数据库升级后，用户无法登陆。后期待版本稳定后，移除该列',
  `image` varchar(255) DEFAULT NULL COMMENT '头像图片url',
  `area` double(11,2) DEFAULT NULL COMMENT '种植面积',
  `crop` varchar(255) DEFAULT NULL COMMENT '种植作物',
  `per_mu_yield` double(11,0) DEFAULT NULL COMMENT '亩产量',
  PRIMARY KEY (`id`),
  KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `verification_record`
--

DROP TABLE IF EXISTS `verification_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification_record` (
  `mobile` varchar(20) NOT NULL COMMENT '标识',
  `type` int(11) NOT NULL COMMENT '业务类型（1：注册 ， 2：忘记密码）',
  `code` varchar(20) DEFAULT NULL COMMENT '验证码',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`mobile`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warning`
--

DROP TABLE IF EXISTS `warning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warning` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '告警的ID',
  `type` int(11) NOT NULL COMMENT '告警的分类',
  `sub_type` int(11) NOT NULL COMMENT '告警子类型，对于没有子类型的告警，这里必须为0',
  `level` int(11) NOT NULL,
  `cleared` tinyint(1) NOT NULL COMMENT '告警是否已经消除',
  `produce_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '告警发生的时间',
  `clear_time` timestamp NULL DEFAULT NULL COMMENT '告警消除的时间',
  `clear_reason` int(11) DEFAULT NULL COMMENT '告警消除的原因',
  `addr_type` int(11) NOT NULL COMMENT '告警产生的位置的类型。该字段结合其余以addr开头的字段来说明告警的位置',
  `addr_ias` int(11) NOT NULL COMMENT '智慧农业系统的ID',
  `addr_irri_fer` int(11) DEFAULT NULL COMMENT '水肥一体化子系统ID',
  `addr_farmland` int(11) DEFAULT NULL COMMENT '农田ID',
  `addr_area` int(11) DEFAULT NULL COMMENT '灌溉区ID',
  `addr_parent_dev` int(11) DEFAULT NULL COMMENT '可操作设备的ID',
  `addr_dev` int(11) DEFAULT NULL COMMENT '传感器ID',
  `extension` json DEFAULT NULL COMMENT '告警扩展信息。不同告警有不同的扩展信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wstation_ias`
--

DROP TABLE IF EXISTS `wstation_ias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wstation_ias` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `weather_station_id` int(11) DEFAULT NULL COMMENT '气象站id',
  `ias_id` int(11) DEFAULT NULL COMMENT '农业系统id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'ias_v2_0'
--

--
-- Dumping routines for database 'ias_v2_0'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-29 14:43:51
