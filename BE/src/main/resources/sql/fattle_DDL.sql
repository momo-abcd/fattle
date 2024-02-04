DROP DATABASE IF EXISTS `fattle`;

CREATE DATABASE `fattle`;

USE `fattle`;

CREATE TABLE `avatar_tb` (
  `avatar_cd` char(3) NOT NULL,
  `level` int NOT NULL,
  `info` varchar(50) NOT NULL,
  `img_path` varchar(200) NOT NULL,
  PRIMARY KEY (`avatar_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_tb` (
  `user_cd` bigint NOT NULL,
  `avatar_cd` char(3) DEFAULT NULL,
  `nickname` varchar(16) DEFAULT NULL,
  `sex` char(1) DEFAULT 'M',
  `join_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `goal_weight` float DEFAULT '0',
  `goal_calory` int DEFAULT '0',
  `goal_carbo` int DEFAULT '0',
  `goal_protein` int DEFAULT '0',
  `goal_fat` int DEFAULT '0',
  `growth_exp` int DEFAULT '0',
  `stack_exp` int DEFAULT '0',
  `introduction` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `battle_tb` (
  `battle_cd` char(6) NOT NULL,
  `creator_cd` bigint NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `start_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `end_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint DEFAULT '0',
  PRIMARY KEY (`battle_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `battle_betting_tb` (
  `betting_cd` int NOT NULL AUTO_INCREMENT,
  `battle_cd` char(6) NOT NULL,
  `content` varchar(50) NOT NULL,
  PRIMARY KEY (`betting_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `battle_player_tb` (
  `battle_cd` char(6) NOT NULL,
  `user_cd` bigint NOT NULL,
  `before_weight` float DEFAULT '0',
  `after_weight` float DEFAULT '0',
  `goal_weight` float DEFAULT '0',
  `live_pt` int DEFAULT '0',
  `food_pt` int DEFAULT '0',
  `live_user_pt` int DEFAULT '0',
  `food_user_pt` int DEFAULT '0',
  `quest_pt` int DEFAULT '0',
  `goal_pt` int DEFAULT '0',
  PRIMARY KEY (`battle_cd`,`user_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `battle_trigger_tb` (
  `battle_cd` char(6) NOT NULL,
  `user_cd` bigint NOT NULL,
  `live_pt` int DEFAULT '0',
  PRIMARY KEY (`battle_cd`,`user_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `battle_point_tb` (
  `point_cd` int NOT NULL AUTO_INCREMENT,
  `battle_cd` char(6) NOT NULL,
  `player_cd` bigint NOT NULL,
  `trigger_cd` bigint NOT NULL DEFAULT 0,
  `type` tinyint DEFAULT '0',
  `point` int DEFAULT '0',
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`point_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exercise_type_tb` (
  `type_cd` char(3) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`type_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exercise_tb` (
  `exercise_cd` int NOT NULL AUTO_INCREMENT,
  `user_cd` bigint NOT NULL,
  `type_cd` char(3) NOT NULL,
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`exercise_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exp_history_tb` (
  `exp_cd` int NOT NULL AUTO_INCREMENT,
  `user_cd` bigint NOT NULL,
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(50) NOT NULL,
  `content` varchar(50) NOT NULL,
  `point` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`exp_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `follow_tb` (
  `follow_cd` int NOT NULL,
  `to_user_cd` bigint NOT NULL,
  `from_user_cd` bigint NOT NULL,
  PRIMARY KEY (`follow_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `food_board_tb` (
  `food_board_cd` int NOT NULL,
  `battle_cd` char(6) NOT NULL,
  `player_cd` bigint NOT NULL,
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `img_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`food_board_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `food_comment_tb` (
  `food_comment_cd` int NOT NULL,
  `food_board_cd` int NOT NULL,
  `trigger_cd` bigint NOT NULL,
  `content` varchar(100) DEFAULT NULL,
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`food_comment_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `food_tb` (
  `food_cd` int NOT NULL AUTO_INCREMENT,
  `user_cd` bigint NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '아침: 0\r\n점심: 1\r\n저녁: 2',
  `calory` int NOT NULL DEFAULT '0',
  `protein` int NOT NULL DEFAULT '0',
  `carbo` int NOT NULL DEFAULT '0',
  `fat` int NOT NULL DEFAULT '0',
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `img_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`food_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `health_tb` (
  `health_cd` int NOT NULL AUTO_INCREMENT,
  `user_cd` bigint NOT NULL,
  `height` float DEFAULT '0',
  `weight` float DEFAULT '0',
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`health_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `quest_tb` (
  `rec_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_cd` bigint NOT NULL,
  `day_chk` tinyint NOT NULL DEFAULT '1',
  `exercise_cnt` tinyint NOT NULL DEFAULT '0',
  `food_cnt` tinyint NOT NULL DEFAULT '0',
  `is_finish` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`rec_dt`,`user_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create or replace view `fattle`.`ranking_vw` as
select
    rank() over (
    order by (`fattle`.`user_tb`.`growth_exp` + `fattle`.`user_tb`.`stack_exp`) ) as `rank`,
    `fattle`.`user_tb`.`user_cd` as `user_cd`,
    `fattle`.`user_tb`.`nickname` as `nickname`,
    `fattle`.`user_tb`.`growth_exp` as `growth_exp`,
    `fattle`.`user_tb`.`stack_exp` as `stack_exp`,
    `fattle`.`avatar_tb`.`img_path` as `img_path`
from
    `fattle`.`user_tb`
join `fattle`.`avatar_tb`
on `fattle`.`user_tb`.`avatar_cd` = `fattle`.`avatar_tb`.`avatar_cd`;