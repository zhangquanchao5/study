alter table user_info add id_card varchar(64);

##第三次大变更
CREATE TABLE `bank_withdrawals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `bank_id` varchar(50) NOT NULL COMMENT '银行绑定编号id',
  `bank_type` int(11) NOT NULL COMMENT '1 银行卡 2支付宝 3微信',
  `amount` bigint(50) NOT NULL COMMENT '提现金额',
  `status` tinyint(2) DEFAULT '0' COMMENT '1 申请中 2已打款 3拒绝提现',
  `create_time` datetime DEFAULT NULL COMMENT '提现时间',
  `transfer_time` datetime DEFAULT NULL COMMENT '到帐时间',
  `left_amount` bigint(50) DEFAULT NULL COMMENT '当时剩下金额',
  `bill_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8;

CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `bank_no` varchar(50) NOT NULL COMMENT '银行帐号',
  `bank_type` int(11) NOT NULL COMMENT '1 银行卡 2支付宝 3微信',
  `name` varchar(64) DEFAULT NULL COMMENT '银行开户名',
  `bank_deposit` varchar(64) DEFAULT NULL COMMENT '开户银行',
  `address` varchar(255) DEFAULT NULL COMMENT '银行开户地址',
  `status` tinyint(2) DEFAULT '0' COMMENT '1 正常 0删除',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

alter table account_withdrawal_history add left_amount bigint(50) NOT NULL COMMENT '当时剩下金额';
alter table account_deposit_history add left_amount bigint(50) NOT NULL COMMENT '当时剩下金额';

alter table user_info add domain varchar(64) DEFAULT NULL COMMENT '二级域名';
alter table user_info add parent_id int(11) DEFAULT NULL COMMENT '主用户编号';



#用户收入卖课程区分有你学和轻校网收入
alter table account_deposit_history add `source_user_id` int(11) DEFAULT NULL COMMENT '课程收入来源用户';

alter table bank add `bank_person` tinyint(2) DEFAULT '0'  COMMENT '0 个人账户 1企业账户';
alter table bank add `phone` varchar(64) DEFAULT NULL   COMMENT '预留手机号';
alter table bank add `company_name` varchar(64) DEFAULT NULL  COMMENT '企业注册名称';
alter table bank add `company_code` varchar(64) DEFAULT NULL  COMMENT '纳税人编号';
alter table bank add `company_address` varchar(255) DEFAULT NULL  COMMENT '公司注册地址';

alter table user_info add remark varchar(1000);
alter table user_info add school_sign tinyint(2) DEFAULT '0' COMMENT '0 不跳后台 1跳后台';
