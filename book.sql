/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : book

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 30/05/2022 00:37:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '还没有描述~',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `publisher_id` int(10) UNSIGNED NOT NULL,
  `price` double NULL DEFAULT NULL,
  `watch_num` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `sold` smallint(6) NOT NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Python编程', '本书是针对所有层次Python读者而作的Python入门书。', 'http://127.0.0.1:8000/image/Python编程.jpg', 2, 25, 234, 1, '2022-05-01 07:54:00');
INSERT INTO `book` VALUES (2, '克拉拉与太阳', '在《克拉拉与太阳》这部作品中，石黑一雄通过一位令人难忘的叙述者的视角，观察千变万化的现代社会，探索了一个根本性的问题：究竟什么是爱？', 'http://127.0.0.1:8000/image/克拉拉与太阳.jpg', 3, 18, 42, 1, '2022-05-07 23:01:00');
INSERT INTO `book` VALUES (3, '也许你该找个人聊聊', '这是一位心理治疗师的回忆录，讲述了发生在诊室中的故事。在这个小小的密闭空间里，人们会展现出最真实、最脆弱的一面；也是在这里，人们获得了陪伴和倾听，也获得了宝贵的觉察、成长与改变。', 'http://127.0.0.1:8000/image/也许你该找个人聊聊.jpg', 5, 20, 33, 1, '2022-05-04 18:10:00');
INSERT INTO `book` VALUES (4, '从零开始的女性主义', '日本女性主义理论第一人、《厌女》作者上野千鹤子 面向普通读者的女性主义普及课', 'http://127.0.0.1:8000/image/从零开始的女性主义.jpg', 5, 15, 24, 1, '2022-05-05 20:20:00');
INSERT INTO `book` VALUES (5, '刘擎西方现代思想讲义', '在这部讲义里，刘擎介绍了现代视域下的19位思想大家，广泛而系统地讨论工具理性的利 弊，如何面对虚无主义，消费主义对人的异化，财富分配的公平正义和全球化等议题。', 'http://127.0.0.1:8000/image/刘擎西方现代思想讲义.jpg', 3, 23, 40, 0, '2022-05-04 19:40:00');
INSERT INTO `book` VALUES (6, '置身事内', '“在成功的经济体中，经济政策一定是务实的，不是意识形态化的。是具体的，不是抽象的。”', 'http://127.0.0.1:8000/image/置身事内.jpg', 3, 19, 38, 1, '2022-05-08 17:55:00');
INSERT INTO `book` VALUES (7, '文城', '人生就是自己的往事和他人的序章', 'http://127.0.0.1:8000/image/文城.jpg', 2, 16, 22, 1, '2022-04-29 12:01:00');
INSERT INTO `book` VALUES (8, '海边的房间', '十二个坏掉的人，十二个令人倒吸一口凉气的好故事。小说家熬制典雅细密的汉语，精巧布局，将人间悲欢斩落整齐，写出一个城市畸爱者的幽冷世界', 'http://127.0.0.1:8000/image/海边的房间.jpg', 2, 17, 24, 0, '2022-05-01 13:42:00');
INSERT INTO `book` VALUES (9, '一日三秋', '《一日三秋》是当代著名作家刘震云的现实魔幻主义新作，《一句顶一万句》的升级版，从百年延宕到千年求索。', 'http://127.0.0.1:8000/image/一日三秋.jpg', 3, 20, 54, 0, '2022-05-02 14:22:00');
INSERT INTO `book` VALUES (10, '一把刀，千个字', '王安忆的长篇《一把刀，千个字》从清袁枚的 “月映竹成千个字，霜高梅孕一身花”进入，以一位淮扬名厨非同寻常的成长经历为叙述线索，他生于东北的冰雪之地，记忆却从因避难而被携来上海寄居的亭子间开始。', 'http://127.0.0.1:8000/image/一把刀，千个字.jpg', 1, 21, 19, 0, '2022-05-03 11:44:00');
INSERT INTO `book` VALUES (11, '拳', '武林是一个传说，八十年代也是一个传说，在传说中寻找传说，是为青春。', 'http://127.0.0.1:8000/image/拳.jpg', 4, 11, 45, 0, '2022-05-12 00:00:00');
INSERT INTO `book` VALUES (12, '破晓时分', '台湾文学家朱西甯短篇小说经典大陆首次出版，彰显人之存在，人之欲念，人之性灵。——从《铁浆》中的北地乡野传奇延展至台湾市镇风情，古希腊式悲剧演变为普通人琐细日常与内心战场，呈现更为现代的深邃风景。', 'http://127.0.0.1:8000/image/破晓时分.jpg', 1, 20, 47, 0, '2022-05-11 00:00:00');
INSERT INTO `book` VALUES (13, '活着就是冲天一喊', '本书是矿工诗人陈年喜首部非虚构故事集，由真故图书出品。', 'http://127.0.0.1:8000/image/活着就是冲天一喊.jpg', 3, 14, 55, 0, '2022-05-09 00:00:00');
INSERT INTO `book` VALUES (14, '俗女养成记', '豆瓣高分超人气剧集《俗女养成记》同名原著，“陈嘉玲”自传！', 'http://127.0.0.1:8000/image/俗女养成记.jpg', 1, 20, 4, 0, '2022-05-07 00:00:00');
INSERT INTO `book` VALUES (15, '一切境', '“那光亮与能量还是如此真挚而强烈”，经历二十余年写作，从安妮宝贝到庆山，文字的力量一直都在，影响无数读者并抵达他们的精神生活内核。前方路途迢遥，相信所有相通的心灵终将相遇。', 'http://127.0.0.1:8000/image/一切境.jpg', 5, 22, 70, 1, '2022-05-06 00:00:00');
INSERT INTO `book` VALUES (16, '成年人的谎言生活', '《成年人的谎言生活》（2019）是意大利作家埃莱娜•费兰特继“那不勒斯四部曲”之后的最新一部作品，聚焦于出身那不勒斯中产家庭的女孩乔瓦娜的青春和成长。', 'http://127.0.0.1:8000/image/成年人的谎言生活.jpg', 3, 23, 20, 1, '2022-05-04 00:00:00');
INSERT INTO `book` VALUES (19, '三国演义', '三国演义这么经典不来看啊', 'http://127.0.0.1:8000/image/三国演义.jpg', 4, 10, 244, 0, '2022-05-07 00:00:00');
INSERT INTO `book` VALUES (20, '挪威的森林', '这是一部动人心弦的、平缓舒雅的、略带感伤的恋爱小说。', 'http://127.0.0.1:8000/image/挪威的森林.jpg', 5, 11, 27, 1, '2022-05-04 00:00:00');
INSERT INTO `book` VALUES (23, 'test', 'description', 'http://127.0.0.1:8000/image/302973.jpg', 1, 20.5, 0, 0, '2022-05-28 00:10:21');
INSERT INTO `book` VALUES (24, '软件项目管理', '以精辟的语言更新了项目管理5大过程组的定义并介绍了项目管理10大知识领域与47个过程，是项目管理从业人员的极为重要的工具书。', 'http://127.0.0.1:8000/image/302973.jpg', 1, 25, 66, 1, '2022-05-28 13:56:04');

-- ----------------------------
-- Table structure for lack
-- ----------------------------
DROP TABLE IF EXISTS `lack`;
CREATE TABLE `lack`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `publisher_id` int(10) UNSIGNED NOT NULL,
  `is_solved` smallint(6) NOT NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lack
-- ----------------------------
INSERT INTO `lack` VALUES (1, '西游记', 3, 0, '2022-05-04 09:23:00');
INSERT INTO `lack` VALUES (2, '西方建筑史', 2, 0, '2022-05-07 10:53:00');
INSERT INTO `lack` VALUES (3, '三国演义', 1, 1, '2022-05-03 12:12:00');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL,
  `book_id` int(10) UNSIGNED NOT NULL,
  `cost` double NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `is_success` smallint(6) NOT NULL DEFAULT 1,
  `address` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 2, 6, 19, '2022-05-10 00:00:00', 1, '北京朝阳区');
INSERT INTO `order` VALUES (2, 3, 7, 16, '2022-05-11 00:00:00', 1, '上海浦东新区');
INSERT INTO `order` VALUES (12, 1, 2, 18, '2022-05-20 14:10:27', 1, '北京朝阳区');
INSERT INTO `order` VALUES (4, 3, 15, 22, '2022-05-15 00:00:00', 1, '湖北武汉');
INSERT INTO `order` VALUES (11, 1, 24, 25, '2022-05-28 13:57:50', 1, 'HBUT');
INSERT INTO `order` VALUES (10, 6, 1, 25, '2022-05-20 00:00:09', 1, '湖北工业大学');
INSERT INTO `order` VALUES (14, 1, 4, 15, '2022-05-29 22:04:25', 1, 'undefined');
INSERT INTO `order` VALUES (13, 1, 3, 20, '2022-05-20 14:10:27', 1, '湖北省武汉市洪山区湖北工业大学');
INSERT INTO `order` VALUES (15, 1, 16, 23, '2022-05-29 22:14:51', 1, '湖北武汉');
INSERT INTO `order` VALUES (17, 1, 20, 11, '2022-05-29 22:19:26', 1, '湖北武汉');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) CHARACTER SET ujis COLLATE ujis_japanese_ci NULL DEFAULT NULL,
  `sex` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `identity` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'member' COMMENT 'member或admin',
  `register_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'xiaoming', '123456', 'xiaoming@qq.com', '男', 'http://127.0.0.1:8000/image/xiaoming.jpg', 'member', '2022-04-04 08:42:06');
INSERT INTO `user` VALUES (3, 'gang', '123456', 'gang@gang.com', '男', 'http://127.0.0.1:8000/image/gang.jpg', 'admin', '2022-01-19 15:20:54');
INSERT INTO `user` VALUES (4, 'sunzhan', '123456', 'sunzhan@sz.com', '男', 'http://127.0.0.1:8000/image/sunzhan.jpg', 'member', '2022-02-02 03:45:54');
INSERT INTO `user` VALUES (5, 'slf', '666666', 'slf@qiang.com', '男', 'http://127.0.0.1:8000/image/slf.jpg', 'admin', '2022-03-07 07:41:12');
INSERT INTO `user` VALUES (6, 'test-user', '123123', '213123123@qq.com', NULL, 'http://127.0.0.1:8000/image/gang.jpg', 'member', '2022-05-27 23:39:49');
INSERT INTO `user` VALUES (1, 'xiaohong', '123456', '213123123@qq.com', '女', 'http://127.0.0.1:8000/image/xiaohong.jpg', 'admin', '2022-04-28 09:54:26');

SET FOREIGN_KEY_CHECKS = 1;
