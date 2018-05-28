package com.aiw.bdzb.entity;

import com.aiw.entity.BaseEntity;

public class RechargeCard extends BaseEntity {

		private java.sql.Date  expires;
		private Integer  type;
		private Integer  days;
		private String  no;
		private Integer status;//0未使用，1使用了
		private Integer tenantid;
		
		//非DB字段
		private Integer  count;//产生充值卡的数量
		private String phone;//充值给哪一个用户了
	
		public java.sql.Date getExpires() {
			return expires;
		}
		public void setExpires(java.sql.Date expires) {
			this.expires = expires;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Integer getDays() {
			return days;
		}
		public void setDays(Integer days) {
			this.days = days;
		}
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getTenantid() {
			return tenantid;
		}
		public void setTenantid(Integer tenantid) {
			this.tenantid = tenantid;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	
}
