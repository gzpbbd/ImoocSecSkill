-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;
-- 创建秒杀库存表，原课程使用的 mysql 版本是 5.7 以下，MySQL建表的语句有问题，改为如下
-- mysql 5.7 如果不给 end_time 设置默认值，则创建表的时候会提示默认值无效
CREATE TABLE `seckill_product` (
   `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
   `name` varchar(255) COLLATE utf8_czech_ci NOT NULL COMMENT '商品名称',
   `number` int(11) NOT NULL COMMENT '库存数量',
   `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
   `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   PRIMARY KEY (`seckill_id`),
   KEY `idx_start_time` (`start_time`),
   KEY `idx_end_time` (`end_time`),
   KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci COMMENT='秒杀库存表';

-- 插入库存数据
INSERT INTO seckill.seckill_product (name, number, start_time, end_time)
values
    ('3000元秒杀ipone12',100, '2022-4-1 0:0:0', '2022-5-1 0:0:0'),
    ('1500元秒杀matepad11',200, '2022-2-15 0:0:0', '2022-3-30 0:0:0'),
    ('2000元秒杀ipad2022',300, '2022-1-26 0:0:0', '2022-1-30 0:0:0'),
    ('800元秒杀redme11',400, '2022-2-23 0:0:0', '2022-3-30 0:0:0');

-- 秒杀成功明细表
-- 用户登陆认证相关的信息
CREATE TABLE `success_killed_record` (
    `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_phone` varchar(255) NOT NULL COMMENT '用户手机号',
    `state` tinyint(255) NOT NULL DEFAULT '-1' COMMENT '状态标识： -1 无效，0 成功，1 已付款，2 发货',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`,`user_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';


-- ALTER TABLE seckill
-- DROP INDEX idx_create_time,
-- ADD INDEX idx_c_s(start_time, create_time);