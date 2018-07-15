package com.aiw.bdzb.entity;

import com.aiw.base.entity.BaseEntity;

public class Tenant extends BaseEntity {

		private String  address;
		private String  accesskey;//开始存放验证码.后面存放本地的一个认证key.
		private String  phone;
		private String nickName;
		
		
		private java.sql.Date birthday;
		private String idCard;
		
		private Integer	vip; //0非会员         其他数字都代表会员卡的类型          99代表查询全部                    
		
		private java.sql.Date vipDeadLine;//vip到期时间
		private String vipCard;//会员卡号码
		private Integer consumeAmount;//办卡消费金额
		private Integer jifen;
		private String referer;//推荐人电话
		private Integer loginTimes;//登录次数
		
		private Integer faxing;
		private Integer fuse;
		private Integer lianxing;
		private Integer sex;//1 男  2 女  3未知
		private Integer balance;
		
		
		//非DB字段
		private Integer tenantDeadLine;//查询用的,今天到期,三天内到期,几个月内到期.
		private Integer tenantDeadLineDays;//仅仅为了展示,还剩多少天到期.
		private java.sql.Date startRegisterDate;
		private java.sql.Date endRegisterDate;
		private Integer adornTotalMoney;
		private Integer adornTimes;
		private String faxing_name;
        private String fuse_name;
        private String lianxing_name;
		private Integer totalDeposit;
		
		private String adornTimesQuery;
		private String adornTotalMoneyQuery;
		private boolean isMember;//包含是不是会员,并且判断是否过期
		
		
	
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getAccesskey() {
			return accesskey;
		}
		public void setAccesskey(String accesskey) {
			this.accesskey = accesskey;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		public java.sql.Date getVipDeadLine() {
			return vipDeadLine;
		}
		public void setVipDeadLine(java.sql.Date vipDeadLine) {
			this.vipDeadLine = vipDeadLine;
		}
		public java.sql.Date getBirthday() {
			return birthday;
		}
		public void setBirthday(java.sql.Date birthday) {
			this.birthday = birthday;
		}
		public String getVipCard() {
			return vipCard;
		}
		public void setVipCard(String vipCard) {
			this.vipCard = vipCard;
		}
		public Integer getVip() {
			return vip;
		}
		public void setVip(Integer vip) {
			this.vip = vip;
		}
		public Integer getTenantDeadLine() {
			return tenantDeadLine;
		}
		public void setTenantDeadLine(Integer tenantDeadLine) {
			this.tenantDeadLine = tenantDeadLine;
		}
		public Integer getConsumeAmount() {
			return consumeAmount;
		}
		public void setConsumeAmount(Integer consumeAmount) {
			this.consumeAmount = consumeAmount;
		}
		public Integer getJifen() {
			return jifen;
		}
		public void setJifen(Integer jifen) {
			this.jifen = jifen;
		}
		public String getReferer() {
			return referer;
		}
		public void setReferer(String referer) {
			this.referer = referer;
		}
		public java.sql.Date getStartRegisterDate() {
			return startRegisterDate;
		}
		public void setStartRegisterDate(java.sql.Date startRegisterDate) {
			this.startRegisterDate = startRegisterDate;
		}
		public java.sql.Date getEndRegisterDate() {
			return endRegisterDate;
		}
		public void setEndRegisterDate(java.sql.Date endRegisterDate) {
			this.endRegisterDate = endRegisterDate;
		}
		public Integer getAdornTotalMoney() {
			return adornTotalMoney;
		}
		public void setAdornTotalMoney(Integer adornTotalMoney) {
			this.adornTotalMoney = adornTotalMoney;
		}
		public Integer getAdornTimes() {
			return adornTimes;
		}
		public void setAdornTimes(Integer adornTimes) {
			this.adornTimes = adornTimes;
		}
        public Integer getFaxing() {
            return faxing;
        }
        public void setFaxing(Integer faxing) {
            this.faxing = faxing;
        }
        public Integer getFuse() {
            return fuse;
        }
        public void setFuse(Integer fuse) {
            this.fuse = fuse;
        }
        public Integer getLianxing() {
            return lianxing;
        }
        public void setLianxing(Integer lianxing) {
            this.lianxing = lianxing;
        }
        public Integer getSex() {
            return sex;
        }
        public void setSex(Integer sex) {
            this.sex = sex;
        }
        public Integer getTenantDeadLineDays() {
            return tenantDeadLineDays;
        }
        public void setTenantDeadLineDays(Integer tenantDeadLineDays) {
            this.tenantDeadLineDays = tenantDeadLineDays;
        }
        public String getFaxing_name() {
            return faxing_name;
        }
        public void setFaxing_name(String faxing_name) {
            this.faxing_name = faxing_name;
        }
        public String getFuse_name() {
            return fuse_name;
        }
        public void setFuse_name(String fuse_name) {
            this.fuse_name = fuse_name;
        }
        public String getLianxing_name() {
            return lianxing_name;
        }
        public void setLianxing_name(String lianxing_name) {
            this.lianxing_name = lianxing_name;
        }
        public Integer getTotalDeposit() {
            return totalDeposit;
        }
        public void setTotalDeposit(Integer totalDeposit) {
            this.totalDeposit = totalDeposit;
        }
        public String getAdornTimesQuery() {
            return adornTimesQuery;
        }
        public void setAdornTimesQuery(String adornTimesQuery) {
            this.adornTimesQuery = adornTimesQuery;
        }
        public String getAdornTotalMoneyQuery() {
            return adornTotalMoneyQuery;
        }
        public void setAdornTotalMoneyQuery(String adornTotalMoneyQuery) {
            this.adornTotalMoneyQuery = adornTotalMoneyQuery;
        }
        public Integer getLoginTimes() {
            return loginTimes;
        }
        public void setLoginTimes(Integer loginTimes) {
            this.loginTimes = loginTimes;
        }
        public Integer getBalance() {
            return balance;
        }
        public void setBalance(Integer balance) {
            this.balance = balance;
        }
        public boolean isMember() {
            return isMember;
        }
        public void setMember(boolean isMember) {
            this.isMember = isMember;
        }	
}
