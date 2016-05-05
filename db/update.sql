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

